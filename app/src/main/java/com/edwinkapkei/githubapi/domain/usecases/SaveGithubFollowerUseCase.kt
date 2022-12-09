package com.edwinkapkei.githubapi.domain.usecases

import com.edwinkapkei.githubapi.data.model.GithubFollower
import com.edwinkapkei.githubapi.domain.repository.GithubUserRepository

class SaveGithubFollowerUseCase(private val userRepository: GithubUserRepository) {
    suspend fun execute(githubFollowers: List<GithubFollower>) = userRepository.saveFollowers(githubFollowers)
}