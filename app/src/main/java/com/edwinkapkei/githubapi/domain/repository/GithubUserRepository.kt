package com.edwinkapkei.githubapi.domain.repository

import com.edwinkapkei.githubapi.data.model.GithubFollower
import com.edwinkapkei.githubapi.data.model.GithubUser
import com.edwinkapkei.githubapi.data.utilities.ResourceStatus

interface GithubUserRepository {
    suspend fun getGithubUser(username: String): ResourceStatus<GithubUser>
    suspend fun getGithubFollowers(
        username: String,
        perPage: Int,
        page: Int
    ): ResourceStatus<List<GithubFollower>>

    suspend fun getGithubFollowing(
        username: String,
        perPage: Int,
        page: Int
    ): ResourceStatus<List<GithubFollower>>
}