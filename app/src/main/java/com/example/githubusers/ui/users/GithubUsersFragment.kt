package com.example.githubusers.ui.users

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.githubusers.App
import com.example.githubusers.R
import com.example.githubusers.ui.users.adapter.GithubUserAdapter
import com.example.githubusers.ui.users.adapter.LoadingAdapter
import kotlinx.android.synthetic.main.fragment_github_users_list.*
import javax.inject.Inject

class GithubUsersFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val githubUsersViewModel: GithubUsersViewModel by viewModels {
        viewModelFactory
    }

    private val githubUserAdapter: GithubUserAdapter =
        GithubUserAdapter { userResponseItem ->

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
        return inflater.inflate(R.layout.fragment_github_users_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRecycler()
    }

    private fun initRecycler() {
        githubUserRecycler.adapter = githubUserAdapter.withLoadStateFooter(LoadingAdapter())

        githubUsersViewModel.users.observe(viewLifecycleOwner, Observer {
            it?.let { pagingData ->
                githubUserAdapter.submitData(lifecycle, pagingData)
            }
        })
    }
}