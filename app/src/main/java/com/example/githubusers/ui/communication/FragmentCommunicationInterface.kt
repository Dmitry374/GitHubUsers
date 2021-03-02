package com.example.githubusers.ui.communication

import com.example.githubusers.data.UsersResponseItem

interface FragmentCommunicationInterface {
    fun onOpenUserDetail(usersResponseItem: UsersResponseItem)
}