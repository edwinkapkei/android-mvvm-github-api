package com.edwinkapkei.githubapi.views.di

import android.app.Application
import com.edwinkapkei.githubapi.domain.usecases.*
import com.edwinkapkei.githubapi.views.viewmodel.UserViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideUserViewModelFactory(
        application: Application,
        getGithubUserUseCase: GetGithubUserUseCase,
        getGithubUserFollowers: GetGithubUserFollowers,
        saveGithubUserUseCase: SaveGithubUserUseCase,
        saveGithubFollowerUseCase: SaveGithubFollowerUseCase,
        getSavedUserUseCase: GetSavedUserUseCase,
        getSavedFollowersUseCase: GetSavedFollowersUseCase
    ): UserViewModelFactory {
        return UserViewModelFactory(
            application,
            getGithubUserUseCase,
            getGithubUserFollowers,
            saveGithubUserUseCase,
            saveGithubFollowerUseCase,
            getSavedUserUseCase,
            getSavedFollowersUseCase
        )
    }
}