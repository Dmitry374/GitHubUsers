package com.example.githubusers.ui.detail

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.example.githubusers.App
import com.example.githubusers.R
import com.example.githubusers.data.UsersResponseItem
import kotlinx.android.synthetic.main.fragment_user_detail.*
import javax.inject.Inject

class UserDetailFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val userDetailViewModel: UserDetailViewModel by viewModels {
        viewModelFactory
    }

    companion object {

        private const val ARG_GITHUB_USER = "github_user"
        private const val ARG_AVATAR_IMAGE_TRANSITION_NAME = "avatar_image_transition_name"

        fun newInstance(usersResponseItem: UsersResponseItem): UserDetailFragment =
            UserDetailFragment().apply {
                arguments = bundleOf(
                    ARG_GITHUB_USER to usersResponseItem
                )
            }

        fun newInstance(
            usersResponseItem: UsersResponseItem,
            transitionName: String
        ): UserDetailFragment =
            UserDetailFragment().apply {
                arguments =
                    bundleOf(
                        ARG_GITHUB_USER to usersResponseItem,
                        ARG_AVATAR_IMAGE_TRANSITION_NAME to transitionName
                    )
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition =
                TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
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

        val transitionName = requireArguments().getString(ARG_AVATAR_IMAGE_TRANSITION_NAME)

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

        fillAvatarImage(usersResponseItem, transitionName)

        fillLinkClickListener(usersResponseItem)
    }

    private fun fillAvatarImage(usersResponseItem: UsersResponseItem, transitionName: String?) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageViewAvatarDetail.transitionName = transitionName
        }

        Glide
            .with(imageViewAvatarDetail)
            .load(usersResponseItem.avatarUrl)
            .circleCrop()
            .into(imageViewAvatarDetail)
    }

    private fun fillLinkClickListener(usersResponseItem: UsersResponseItem) {

        val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(usersResponseItem.htmlUrl))

        userLink.setOnClickListener {
            try {
                startActivity(urlIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(requireActivity(), R.string.activity_not_found, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}