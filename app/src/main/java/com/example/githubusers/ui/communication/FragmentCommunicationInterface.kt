package com.example.githubusers.ui.communication

import android.widget.ImageView
import com.example.githubusers.data.UsersResponseItem

interface FragmentCommunicationInterface {
    fun onOpenUserDetail(
        usersResponseItem: UsersResponseItem,
        avatarImageView: ImageView? = null,
        transitionName: String? = null
    )
}