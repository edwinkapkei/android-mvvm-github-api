package com.edwinkapkei.githubapi.data.repository

import com.edwinkapkei.githubapi.data.model.GithubFollower
import com.edwinkapkei.githubapi.data.model.GithubUser
import com.edwinkapkei.githubapi.data.repository.dataSource.GithubUserLocalDataSource
import com.edwinkapkei.githubapi.data.repository.dataSource.GithubUserRemoteDataSource
import com.edwinkapkei.githubapi.data.utilities.ResourceStatus
import com.edwinkapkei.githubapi.domain.repository.GithubUserRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class UserRepositoryImpl(
    private val userRemoteDataSource: GithubUserRemoteDataSource,
    private val userLocalDataSource: GithubUserLocalDataSource
) :
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

    override suspend fun saveUser(githubUser: GithubUser) {
        userLocalDataSource.saveUserToDB(githubUser)
    }

    override suspend fun saveFollowers(githubFollowers: List<GithubFollower>) {
       userLocalDataSource.saveFollowersToDB(githubFollowers)
    }

    override fun getSavedUser(username: String): Flow<GithubUser?> {
        return userLocalDataSource.getSavedUserName(username)
    }

    override fun getSavedFollowers(username: String): Flow<List<GithubFollower>> {
        return userLocalDataSource.getSavedUserFollowers(username)
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