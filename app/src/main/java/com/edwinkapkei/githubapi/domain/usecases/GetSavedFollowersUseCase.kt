package com.edwinkapkei.githubapi.domain.usecases

import com.edwinkapkei.githubapi.data.model.GithubFollower
import com.edwinkapkei.githubapi.domain.repository.GithubUserRepository
import kotlinx.coroutines.flow.Flow

class GetSavedFollowersUseCase(private val userRepository: GithubUserRepository) {
    suspend fun execute(username: String): Flow<List<GithubFollower>> {
        return userRepository.getSavedFollowers(username)
    }
}