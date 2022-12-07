package com.edwinkapkei.githubapi.views.di

import com.edwinkapkei.githubapi.data.api.GithubApiService
import com.edwinkapkei.githubapi.data.repository.dataSource.GithubUserRemoteDataSource
import com.edwinkapkei.githubapi.data.repository.dataSource.GithubUserRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideUserRemoteDataSource(apiService: GithubApiService): GithubUserRemoteDataSource {
        return GithubUserRemoteDataSourceImpl(apiService)
    }
}