package com.edwinkapkei.githubapi.data.repository.dataSource

import com.edwinkapkei.githubapi.data.model.GithubFollower
import com.edwinkapkei.githubapi.data.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface GithubUserLocalDataSource {
    suspend fun saveUserToDB(githubUser: GithubUser)
    suspend fun saveFollowersToDB(githubFollowers: List<GithubFollower>)

    fun getSavedUserName(username: String): Flow<GithubUser?>
    fun getSavedUserFollowers(username: String): Flow<List<GithubFollower>>
}