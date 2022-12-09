package com.edwinkapkei.githubapi.views.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.edwinkapkei.githubapi.domain.usecases.*

class UserViewModelFactory(
    private val app: Application,
    private val getGithubUserUseCase: GetGithubUserUseCase,
    private val getGithubUserFollowers: GetGithubUserFollowers,
    private val saveGithubUserUseCase: SaveGithubUserUseCase,
    private val saveGithubFollowerUseCase: SaveGithubFollowerUseCase,
    private val getSavedUserUseCase: GetSavedUserUseCase,
    private val getSavedFollowersUseCase: GetSavedFollowersUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(
            app,
            getGithubUserUseCase,
            getGithubUserFollowers,
            saveGithubUserUseCase,
            saveGithubFollowerUseCase,
            getSavedUserUseCase,
            getSavedFollowersUseCase
        ) as T
    }
}