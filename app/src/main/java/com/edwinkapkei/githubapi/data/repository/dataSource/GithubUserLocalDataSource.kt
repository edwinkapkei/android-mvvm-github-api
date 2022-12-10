package com.edwinkapkei.githubapi.data.repository.dataSource

import com.edwinkapkei.githubapi.data.model.GithubFollower
import com.edwinkapkei.githubapi.data.model.GithubUser

interface GithubUserLocalDataSource {
    suspend fun saveUserToDB(githubUser: GithubUser)
    suspend fun saveFollowersToDB(githubFollowers: List<GithubFollower>)

    fun getSavedUserName(username: String): GithubUser?
    fun getSavedUserFollowers(username: String, followType: String): List<GithubFollower>
}