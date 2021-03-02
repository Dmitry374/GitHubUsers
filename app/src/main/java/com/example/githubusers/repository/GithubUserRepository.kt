package com.example.githubusers.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.example.githubusers.common.Constants
import com.example.githubusers.data.UserDetail
import com.example.githubusers.data.UsersResponseItem
import com.example.githubusers.network.ApiService
import io.reactivex.Flowable
import io.reactivex.Single

class GithubUserRepository(
    private val apiService: ApiService,
    private val dataSource: GithubUsersPaginationDataSource
) {

    fun loadGithubUserList(): Flowable<PagingData<UsersResponseItem>> {

        return Pager(
            config = PagingConfig(
                pageSize = Constants.USERS_PER_PAGE,
                enablePlaceholders = true,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                prefetchDistance = Constants.PREFETCH_DISTANCE,
                initialLoadSize = Constants.USERS_PER_PAGE
            ),
            pagingSourceFactory = { dataSource }
        )
            .flowable
    }

    fun getUserDetails(username: String): Single<UserDetail> {
        return apiService.getUserDetails(username)
    }
}