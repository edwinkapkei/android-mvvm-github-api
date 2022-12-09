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
        username: String,
        followType: String,
        perPage: Int,
        page: Int
    ): Response<List<GithubFollower>> {
        return githubApiService.getUserFollowers(username, followType, perPage, page)
    }
}