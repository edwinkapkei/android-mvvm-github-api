package com.edwinkapkei.githubapi.domain.repository

import com.edwinkapkei.githubapi.data.model.GithubFollower
import com.edwinkapkei.githubapi.data.model.GithubUser
import com.edwinkapkei.githubapi.data.utilities.ResourceStatus
import kotlinx.coroutines.flow.Flow

interface GithubUserRepository {
    suspend fun getGithubUser(username: String): ResourceStatus<GithubUser>
    suspend fun getGithubFollowers(
        username: String,
        followType: String,
        perPage: Int,
        page: Int
    ): ResourceStatus<List<GithubFollower>>

    suspend fun saveUser(githubUser: GithubUser)
    suspend fun saveFollowers(githubFollowers: List<GithubFollower>)

    fun getSavedUser(username: String): Flow<GithubUser?>
    fun getSavedFollowers(username: String): Flow<List<GithubFollower>>
}