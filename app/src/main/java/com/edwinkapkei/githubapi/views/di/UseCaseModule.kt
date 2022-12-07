package com.edwinkapkei.githubapi.views.di

import com.edwinkapkei.githubapi.domain.repository.GithubUserRepository
import com.edwinkapkei.githubapi.domain.usecases.GetGithubUserFollowers
import com.edwinkapkei.githubapi.domain.usecases.GetGithubUserFollowing
import com.edwinkapkei.githubapi.domain.usecases.GetGithubUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun providesGithubUserUserCase(userRepository: GithubUserRepository): GetGithubUserUseCase {
        return GetGithubUserUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun providesGithubUserFollowers(userRepository: GithubUserRepository): GetGithubUserFollowers {
        return GetGithubUserFollowers(userRepository)
    }

    @Singleton
    @Provides
    fun providesGithubUserFollowing(userRepository: GithubUserRepository): GetGithubUserFollowing {
        return GetGithubUserFollowing(userRepository)
    }
}