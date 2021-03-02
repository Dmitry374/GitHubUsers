package com.example.githubusers.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.example.githubusers.common.Constants
import com.example.githubusers.data.UsersResponseItem
import io.reactivex.Flowable

class GithubUserRepository(
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
}