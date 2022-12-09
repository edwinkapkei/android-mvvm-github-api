package com.edwinkapkei.githubapi.domain.usecases

import com.edwinkapkei.githubapi.data.model.GithubUser
import com.edwinkapkei.githubapi.domain.repository.GithubUserRepository
import kotlinx.coroutines.flow.Flow

class GetSavedUserUseCase(private val userRepository: GithubUserRepository) {
     fun execute(username: String) : Flow<GithubUser?>{
       return userRepository.getSavedUser(username)
    }
}