package com.edwinkapkei.githubapi.views.di

import com.edwinkapkei.githubapi.domain.repository.GithubUserRepository
import com.edwinkapkei.githubapi.domain.usecases.*
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
    fun providesSaveUserUseCase(userRepository: GithubUserRepository): SaveGithubUserUseCase {
        return SaveGithubUserUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun providesSaveFollowersUseCase(userRepository: GithubUserRepository): SaveGithubFollowerUseCase {
        return SaveGithubFollowerUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun providesSavedGithubUser(userRepository: GithubUserRepository): GetSavedUserUseCase {
        return GetSavedUserUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun providesSavedGithubUserFollowers(userRepository: GithubUserRepository): GetSavedFollowersUseCase {
        return GetSavedFollowersUseCase(userRepository)
    }
}