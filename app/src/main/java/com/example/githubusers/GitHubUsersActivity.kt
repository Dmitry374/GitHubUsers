package com.example.githubusers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class GitHubUsersActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager

    private var githubUsersFragment = GithubUsersFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_git_hub_users)

        fragmentManager.beginTransaction()
            .add(R.id.container, githubUsersFragment)
            .commit()
    }
}