package com.edwinkapkei.githubapi.data.repository.dataSource

import com.edwinkapkei.githubapi.data.db.GithubUserDao
import com.edwinkapkei.githubapi.data.model.GithubFollower
import com.edwinkapkei.githubapi.data.model.GithubUser

class GithubUserLocalDataSourceImpl(private val userDao: GithubUserDao) :
    GithubUserLocalDataSource {
    override suspend fun saveUserToDB(githubUser: GithubUser) {
        userDao.insertUser(githubUser)
    }

    override suspend fun saveFollowersToDB(githubFollowers: List<GithubFollower>) {
        userDao.insertFollowers(githubFollowers)
    }

    override fun getSavedUserName(username: String): GithubUser? {
        return userDao.getSavedUser(username)
    }

    override fun getSavedUserFollowers(
        username: String,
        followType: String
    ): List<GithubFollower> {
        return userDao.getSavedUserFollowers(username, followType)
    }
}