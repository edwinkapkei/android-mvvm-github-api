package com.edwinkapkei.githubapi.data.repository.dataSource

import com.edwinkapkei.githubapi.data.api.GithubApiService
import com.edwinkapkei.githubapi.data.model.GithubFollower
import com.edwinkapkei.githubapi.data.model.GithubUser
import retrofit2.Response

class GithubUserRemoteDataSourceImpl(private val githubApiService: GithubApiService) :
    GithubUserRemoteDataSource {
    override suspend fun getUser(username: String): Response<GithubUser> {
        return githubApiService.getUserByUsername(username)
    }

    override suspend fun getUserFollowers(
        username: String, perPage: Int,
        page: Int
    ): Response<List<GithubFollower>> {
        return githubApiService.getUserFollowers(username, perPage, page)
    }

    override suspend fun getUserFollowing(
        username: String, perPage: Int,
        page: Int
    ): Response<List<GithubFollower>> {
        return githubApiService.getUserFollowing(username, perPage, page)
    }
}