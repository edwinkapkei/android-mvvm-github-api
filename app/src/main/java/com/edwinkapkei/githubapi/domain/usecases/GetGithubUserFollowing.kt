package com.edwinkapkei.githubapi.domain.usecases

import com.edwinkapkei.githubapi.data.model.GithubFollower
import com.edwinkapkei.githubapi.data.utilities.ResourceStatus
import com.edwinkapkei.githubapi.domain.repository.GithubUserRepository

class GetGithubUserFollowing(private val userRepository: GithubUserRepository) {
    suspend fun execute(
        username: String,
        perPage: Int,
        page: Int
    ): ResourceStatus<List<GithubFollower>> {
        return userRepository.getGithubFollowing(username, perPage, page)
    }
}