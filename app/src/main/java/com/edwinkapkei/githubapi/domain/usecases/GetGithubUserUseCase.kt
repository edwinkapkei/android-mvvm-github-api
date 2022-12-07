package com.edwinkapkei.githubapi.domain.usecases

import com.edwinkapkei.githubapi.data.model.GithubUser
import com.edwinkapkei.githubapi.data.utilities.ResourceStatus
import com.edwinkapkei.githubapi.domain.repository.GithubUserRepository

class GetGithubUserUseCase(private val userRepository: GithubUserRepository) {
    suspend fun execute(username: String): ResourceStatus<GithubUser> {
        return userRepository.getGithubUser(username)
    }
}