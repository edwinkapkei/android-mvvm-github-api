package com.edwinkapkei.githubapi.api

import com.edwinkapkei.githubapi.data.api.GithubApiService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GithubApiServiceTest {
    private lateinit var apiService: GithubApiService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApiService::class.java)
    }

    @Test
    fun getGithubUserByUserName() {
        runBlocking {
            enqueueMockResponse("github_user_response.json")
            val responseBody = apiService.getUserByUsername("edwinkapkei").body()
            assertThat(responseBody).isNotNull()
            assertThat(responseBody!!.name).isNotEqualTo("Edwin")

            val request = mockWebServer.takeRequest()
            assertThat(request.path).isEqualTo("/users/edwinkapkei")
        }
    }

    @Test
    fun getGithubUserFollowers() {
        runBlocking {
            enqueueMockResponse("github_user_following_followers.json")
            val responseBody = apiService.getUserFollowers("edwinkapkei",10,1).body()
            assertThat(responseBody).isNotNull()

            val follower = responseBody!![0]
            assertThat(follower!!.login).isNotEqualTo("Edwin")

            val request = mockWebServer.takeRequest()
            assertThat(request.path).isEqualTo("/users/edwinkapkei/followers?per_page=10&page=1")
        }
    }

    @Test
    fun getGithubUserFollowing() {
        runBlocking {
            enqueueMockResponse("github_user_following_followers.json")
            val responseBody = apiService.getUserFollowing("edwinkapkei",10,1).body()
            assertThat(responseBody).isNotNull()

            val follower = responseBody!![0]
            assertThat(follower.login).isNotEqualTo("Edwin")
            assertThat(follower.login).isEqualTo("JakeWharton")

            val request = mockWebServer.takeRequest()
            assertThat(request.path).isEqualTo("/users/edwinkapkei/following?per_page=10&page=1")
        }
    }

    @After
    fun shutDown() {
        mockWebServer.shutdown()
    }

    private fun enqueueMockResponse(fileName: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        mockWebServer.enqueue(mockResponse)
    }
}