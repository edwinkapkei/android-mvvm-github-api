package com.edwinkapkei.githubapi.views.di

import android.app.Application
import com.edwinkapkei.githubapi.domain.usecases.GetGithubUserFollowers
import com.edwinkapkei.githubapi.domain.usecases.GetGithubUserFollowing
import com.edwinkapkei.githubapi.domain.usecases.GetGithubUserUseCase
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
        getGithubUserFollowing: GetGithubUserFollowing
    ): UserViewModelFactory {
        return UserViewModelFactory(
            application,
            getGithubUserUseCase,
            getGithubUserFollowers,
            getGithubUserFollowing
        )
    }
}