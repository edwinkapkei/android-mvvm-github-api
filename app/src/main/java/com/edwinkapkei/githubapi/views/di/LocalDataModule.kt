package com.edwinkapkei.githubapi.views.di

import com.edwinkapkei.githubapi.data.db.GithubUserDao
import com.edwinkapkei.githubapi.data.repository.dataSource.GithubUserLocalDataSource
import com.edwinkapkei.githubapi.data.repository.dataSource.GithubUserLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(githubUserDao: GithubUserDao): GithubUserLocalDataSource {
        return GithubUserLocalDataSourceImpl(githubUserDao)
    }
}