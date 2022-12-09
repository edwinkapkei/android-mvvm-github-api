package com.edwinkapkei.githubapi.views.di

import android.app.Application
import androidx.room.Room
import com.edwinkapkei.githubapi.data.db.GithubUserDao
import com.edwinkapkei.githubapi.data.db.GithubUserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideGithubUserDatabase(application: Application): GithubUserDatabase {
        return Room.databaseBuilder(application, GithubUserDatabase::class.java, "github_user_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideGithubUserDao(githubUserDatabase: GithubUserDatabase): GithubUserDao {
        return githubUserDatabase.getGithubUserDao()
    }
}