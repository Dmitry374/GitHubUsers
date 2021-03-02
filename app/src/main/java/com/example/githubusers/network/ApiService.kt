package com.example.githubusers.network

import com.example.githubusers.common.Constants
import com.example.githubusers.data.UsersResponseItem
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/users")
    fun getUserList(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int = Constants.USERS_PER_PAGE
    ): Single<List<UsersResponseItem>>
}