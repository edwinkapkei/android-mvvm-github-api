package com.edwinkapkei.githubapi.views.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.edwinkapkei.githubapi.domain.usecases.GetGithubUserFollowers
import com.edwinkapkei.githubapi.domain.usecases.GetGithubUserFollowing
import com.edwinkapkei.githubapi.domain.usecases.GetGithubUserUseCase

class UserViewModelFactory(
    private val app: Application,
    private val getGithubUserUseCase: GetGithubUserUseCase,
    private val getGithubUserFollowers: GetGithubUserFollowers,
    private val getGithubUserFollowing: GetGithubUserFollowing
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(
            app,
            getGithubUserUseCase,
            getGithubUserFollowers,
            getGithubUserFollowing
        ) as T
    }
}