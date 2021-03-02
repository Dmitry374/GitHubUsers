package com.example.githubusers.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.githubusers.R
import com.example.githubusers.ui.users.GithubUsersFragment

class GitHubUsersActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager

    private var githubUsersFragment =
        GithubUsersFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_git_hub_users)

        fragmentManager.beginTransaction()
            .replace(R.id.nav_host_container, githubUsersFragment)
            .commit()
    }
}