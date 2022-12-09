package com.edwinkapkei.githubapi.data.repository.dataSource

import com.edwinkapkei.githubapi.data.model.GithubFollower
import com.edwinkapkei.githubapi.data.model.GithubUser
import com.edwinkapkei.githubapi.data.utilities.ResourceStatus
import com.edwinkapkei.githubapi.domain.repository.GithubUserRepository
import retrofit2.Response

class UserRepositoryImpl(private val userRemoteDataSource: GithubUserRemoteDataSource) :
    GithubUserRepository {
    override suspend fun getGithubUser(username: String): ResourceStatus<GithubUser> {
        return responseToUserResource(userRemoteDataSource.getUser(username))
    }

    override suspend fun getGithubFollowers(
        username: String,
        followType: String,
        perPage: Int,
        page: Int
    ): ResourceStatus<List<GithubFollower>> {
        return responseToFollowerResource(
            userRemoteDataSource.getUserFollowers(
                username,
                followType,
                perPage,
                page
            )
        )
    }

    private fun responseToUserResource(response: Response<GithubUser>): ResourceStatus<GithubUser> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return ResourceStatus.Success(result)
            }
        }

        return ResourceStatus.Error(response.message())
    }

    private fun responseToFollowerResource(response: Response<List<GithubFollower>>): ResourceStatus<List<GithubFollower>> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return ResourceStatus.Success(result)
            }
        }

        return ResourceStatus.Error(response.message())
    }
}