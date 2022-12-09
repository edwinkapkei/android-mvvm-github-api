package com.edwinkapkei.githubapi.domain.usecases

import com.edwinkapkei.githubapi.data.model.GithubUser
import com.edwinkapkei.githubapi.domain.repository.GithubUserRepository

class SaveGithubUserUseCase(private val userRepository: GithubUserRepository) {
    suspend fun execute(githubUser: GithubUser) = userRepository.saveUser(githubUser)
}