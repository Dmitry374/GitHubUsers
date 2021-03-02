package com.example.githubusers.repository

import androidx.paging.rxjava2.RxPagingSource
import com.example.githubusers.common.Constants
import com.example.githubusers.data.UsersResponseItem
import com.example.githubusers.network.ApiService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class GithubUsersPaginationDataSource(
    private val apiService: ApiService
) : RxPagingSource<Int, UsersResponseItem>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, UsersResponseItem>> {
        val position = params.key ?: 0

        return apiService.getUserList(position)
            .delay(5, TimeUnit.SECONDS)
            .map { response -> toLoadResult(response, position) }
            .onErrorReturn { error -> LoadResult.Error(error) }
            .subscribeOn(Schedulers.io())
    }

    private fun toLoadResult(
        response: List<UsersResponseItem>,
        position: Int
    ): LoadResult<Int, UsersResponseItem> {

        return LoadResult.Page(
            data = response,
            prevKey = if (position == 0) null else position - 1,
            nextKey = response.last().id + Constants.USERS_PER_PAGE
        )
    }
}