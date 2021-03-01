package com.example.githubusers.repository

import com.example.githubusers.network.ApiService

class GithubRepository(
    private val apiService: ApiService
) {

    fun getSomeText(): String {
        return "Hello"
    }
}