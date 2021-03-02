package com.example.githubusers.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.githubusers.R
import com.example.githubusers.data.UsersResponseItem
import kotlinx.android.synthetic.main.fragment_user_detail.*

class UserDetailFragment : Fragment() {

    companion object {

        private const val ARG_GITHUB_USER = "github_user"

        fun newInstance(usersResponseItem: UsersResponseItem): UserDetailFragment =
            UserDetailFragment().apply {
                arguments = bundleOf(
                    ARG_GITHUB_USER to usersResponseItem
                )
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_user_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usersResponseItem =
            requireArguments().getParcelable<UsersResponseItem>(ARG_GITHUB_USER) as UsersResponseItem

        textViewUserName.text = usersResponseItem.login
    }
}