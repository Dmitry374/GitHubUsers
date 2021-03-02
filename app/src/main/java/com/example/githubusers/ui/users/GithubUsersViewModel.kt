package com.example.githubusers.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.example.githubusers.data.UsersResponseItem
import com.example.githubusers.repository.GithubUserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GithubUsersViewModel @Inject constructor(
    private val githubUserRepository: GithubUserRepository
) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    private val _users by lazy { MutableLiveData<PagingData<UsersResponseItem>>() }
    val users: LiveData<PagingData<UsersResponseItem>>
        get() = _users

    init {
        loadGithubUserList()
    }

    private fun loadGithubUserList() {

        compositeDisposable.add(
            githubUserRepository.loadGithubUserList()
                .cachedIn(viewModelScope)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ pagingData ->
                    _users.value = pagingData
                }, {
                    _users.value = PagingData.empty()
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}