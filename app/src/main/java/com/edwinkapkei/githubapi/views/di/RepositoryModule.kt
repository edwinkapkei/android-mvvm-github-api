package com.edwinkapkei.githubapi.views.di

import com.edwinkapkei.githubapi.data.repository.dataSource.GithubUserRemoteDataSource
import com.edwinkapkei.githubapi.data.repository.dataSource.UserRepositoryImpl
import com.edwinkapkei.githubapi.domain.repository.GithubUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(remoteDataSource: GithubUserRemoteDataSource): GithubUserRepository {
        return UserRepositoryImpl(remoteDataSource)
    }
}