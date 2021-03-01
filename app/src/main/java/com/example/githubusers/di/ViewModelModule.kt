package com.example.githubusers.di

import androidx.lifecycle.ViewModel
import com.example.githubusers.ui.users.GithubUsersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(GithubUsersViewModel::class)
    abstract fun bindGithubUsersViewModel(githubUsersViewModel: GithubUsersViewModel): ViewModel
}