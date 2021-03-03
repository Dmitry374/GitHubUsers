package com.example.githubusers.ui.users.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubusers.R
import com.example.githubusers.common.Constants
import com.example.githubusers.data.UsersResponseItem
import kotlinx.android.synthetic.main.item_github_user.view.*

class GithubUserAdapter(private val onUserClickListener: (UsersResponseItem, ImageView?, String?) -> Unit) :
    PagingDataAdapter<UsersResponseItem, GithubUserAdapter.GithubUserViewHolder>(
        DIFF_CALLBACK
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_github_user, parent, false)
        return GithubUserViewHolder(
            view
        ) { position, imageView, transitionName ->
            getItem(position)?.let { onUserClickListener(it, imageView, transitionName) }
        }
    }

    override fun onBindViewHolder(holder: GithubUserViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class GithubUserViewHolder(
        itemView: View,
        private val onUserClick: (Int, ImageView?, String?) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            usersResponseItem: UsersResponseItem
        ) {

            with(itemView) {

                Glide.with(this)
                    .load(usersResponseItem.avatarUrl)
                    .apply(
                        RequestOptions().override(
                            Constants.AVATAR_IMAGE_SIZE,
                            Constants.AVATAR_IMAGE_SIZE
                        )
                    )
                    .circleCrop()
                    .into(imageViewUserAvatar)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    imageViewUserAvatar.transitionName = String.format(
                        itemView.context.getString(R.string.transition_name_template),
                        absoluteAdapterPosition
                    )
                }

                setOnClickListener {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        onUserClick(
                            absoluteAdapterPosition,
                            imageViewUserAvatar,
                            imageViewUserAvatar.transitionName
                        )
                    } else {
                        onUserClick(absoluteAdapterPosition, null, null)
                    }
                }

                textViewUserLogin.text = usersResponseItem.login
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