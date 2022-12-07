package com.edwinkapkei.githubapi.data.api

import com.edwinkapkei.githubapi.data.model.GithubFollower
import com.edwinkapkei.githubapi.data.model.GithubUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {

    @GET("users/{username}")
    suspend fun getUserByUsername(@Path("username") username: String): Response<GithubUser>

    @GET("users/{username}/followers")
    suspend fun getUserFollowers(@Path("username") username: String): Response<GithubFollower>

    @GET("users/{username}/following")
    suspend fun getUserFollowing(@Path("username") username: String): Response<GithubFollower>
}