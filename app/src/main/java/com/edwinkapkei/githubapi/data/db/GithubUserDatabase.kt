package com.edwinkapkei.githubapi.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.edwinkapkei.githubapi.data.model.GithubFollower
import com.edwinkapkei.githubapi.data.model.GithubUser

@Database(
    entities = [GithubUser::class, GithubFollower::class],
    version = 1,
    exportSchema = false
)
abstract class GithubUserDatabase : RoomDatabase() {
    abstract fun getGithubUserDao(): GithubUserDao
}