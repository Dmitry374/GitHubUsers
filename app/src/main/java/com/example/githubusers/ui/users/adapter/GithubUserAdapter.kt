package com.example.githubusers.ui.users.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubusers.R
import com.example.githubusers.common.Constants
import com.example.githubusers.data.UsersResponseItem
import kotlinx.android.synthetic.main.item_github_user.view.*

class GithubUserAdapter(private val onUserClickListener: (UsersResponseItem) -> Unit) :
    PagingDataAdapter<UsersResponseItem, GithubUserAdapter.GithubUserViewHolder>(
        DIFF_CALLBACK
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_github_user, parent, false)
        return GithubUserViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: GithubUserViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, onUserClickListener) }
    }

    class GithubUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            usersResponseItem: UsersResponseItem,
            onUserClickListener: (UsersResponseItem) -> Unit
        ) {
            with(itemView) {
                setOnClickListener { onUserClickListener(usersResponseItem) }

                Glide.with(itemView)
                    .load(usersResponseItem.avatarUrl)
                    .apply(
                        RequestOptions().override(
                            Constants.AVATAR_IMAGE_SIZE,
                            Constants.AVATAR_IMAGE_SIZE
                        )
                    )
                    .circleCrop()
                    .into(imageViewUserAvatar)

                textViewUserName.text = usersResponseItem.login
            }

        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UsersResponseItem>() {
            override fun areItemsTheSame(
                oldItem: UsersResponseItem,
                newItem: UsersResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UsersResponseItem,
                newItem: UsersResponseItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}