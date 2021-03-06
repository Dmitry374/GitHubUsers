package com.example.githubusers.ui.users

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.githubusers.R
import com.example.githubusers.data.UsersResponseItem
import com.example.githubusers.ui.communication.FragmentCommunicationInterface
import com.example.githubusers.ui.detail.UserDetailFragment

class GitHubUsersActivity : AppCompatActivity(), FragmentCommunicationInterface {

    private val fragmentManager = supportFragmentManager

    private var githubUsersFragment =
        GithubUsersFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_git_hub_users)

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                .replace(R.id.nav_host_container, githubUsersFragment)
                .commit()
        }
    }

    override fun onOpenUserDetail(
        usersResponseItem: UsersResponseItem,
        avatarImageView: ImageView?,
        transitionName: String?
    ) {

        if (avatarImageView != null && transitionName != null) {
            fragmentManager.beginTransaction()
                .addSharedElement(avatarImageView, transitionName)
                .replace(
                    R.id.nav_host_container,
                    UserDetailFragment.newInstance(usersResponseItem, transitionName)
                )
                .addToBackStack(null)
                .commit()
        } else {
            fragmentManager.beginTransaction()
                .replace(R.id.nav_host_container, UserDetailFragment.newInstance(usersResponseItem))
                .addToBackStack(null)
                .commit()
        }

    }
}