package com.example.githubusers.ui.users

import androidx.lifecycle.ViewModel
import com.example.githubusers.repository.GithubRepository
import javax.inject.Inject

class GithubUsersViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {

}