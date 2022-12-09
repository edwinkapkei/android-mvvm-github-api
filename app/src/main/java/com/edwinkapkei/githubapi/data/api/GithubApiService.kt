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

    @GET("users/{username}/{follow_type}")
    suspend fun getUserFollowers(
        @Path("username") username: String,
        @Path("follow_type") followType: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Response<List<GithubFollower>>
}