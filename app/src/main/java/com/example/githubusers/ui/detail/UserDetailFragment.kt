package com.example.githubusers.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubusers.App
import com.example.githubusers.R
import com.example.githubusers.data.UsersResponseItem
import kotlinx.android.synthetic.main.fragment_user_detail.*
import javax.inject.Inject

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

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val userDetailViewModel: UserDetailViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as App).appComponent.inject(this)
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

        if (savedInstanceState == null) {
            userDetailViewModel.getUserDetails(usersResponseItem.login)
        }

        userDetailViewModel.userDetail.observe(viewLifecycleOwner, Observer {
            it?.let { userDetail ->
                userName.text = userDetail.name
                textViewUserLogin.text = userDetail.login
                userLink.text = userDetail.htmlUrl
                userLocation.text = userDetail.location
            }
        })

        Glide
            .with(imageViewAvatarDetail)
            .load(usersResponseItem.avatarUrl)
            .circleCrop()
            .into(imageViewAvatarDetail)
    }
}