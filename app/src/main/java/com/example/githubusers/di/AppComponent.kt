package com.example.githubusers.di

import com.example.githubusers.ui.users.GithubUsersFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class, ViewModelBuilderModule::class])
interface AppComponent {

    fun inject(githubUsersFragment: GithubUsersFragment)
}