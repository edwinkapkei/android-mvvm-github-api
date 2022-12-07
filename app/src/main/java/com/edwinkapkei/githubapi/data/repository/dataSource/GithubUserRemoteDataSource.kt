package com.edwinkapkei.githubapi.data.repository.dataSource

import com.edwinkapkei.githubapi.data.model.GithubFollower
import com.edwinkapkei.githubapi.data.model.GithubUser
import retrofit2.Response

interface GithubUserRemoteDataSource {
    suspend fun getUser(username: String): Response<GithubUser>
    suspend fun getUserFollowers(
        username: String,
        perPage: Int,
        page: Int
    ): Response<List<GithubFollower>>

    suspend fun getUserFollowing(
        username: String, perPage: Int,
        page: Int
    ): Response<List<GithubFollower>>
}