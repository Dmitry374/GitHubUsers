package com.example.githubusers.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.data.UserDetail
import com.example.githubusers.repository.GithubUserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserDetailViewModel @Inject constructor(
    private val githubUserRepository: GithubUserRepository
) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    private val _userDetail by lazy { MutableLiveData<UserDetail>() }
    val userDetail: LiveData<UserDetail>
        get() = _userDetail

    fun getUserDetails(username: String) {
        compositeDisposable.add(
            githubUserRepository.getUserDetails(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userDetail ->
                    _userDetail.value = userDetail
                }, {
                    _userDetail.value = null
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}