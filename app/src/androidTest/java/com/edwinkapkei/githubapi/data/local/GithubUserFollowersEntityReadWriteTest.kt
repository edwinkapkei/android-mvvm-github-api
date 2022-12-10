package com.edwinkapkei.githubapi.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.edwinkapkei.githubapi.data.db.GithubUserDao
import com.edwinkapkei.githubapi.data.db.GithubUserDatabase
import com.edwinkapkei.githubapi.data.model.GithubFollower
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class GithubUserFollowersEntityReadWriteTest {
    private lateinit var githubUserDao: GithubUserDao
    private lateinit var database: GithubUserDatabase

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        database = Room.inMemoryDatabaseBuilder(context, GithubUserDatabase::class.java).build()
        githubUserDao = database.getGithubUserDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeFollowerAndReadInList() {
        val githubFollower: GithubFollower = getMockFollower()
        githubFollower.ownerUsername = "hello"
        githubFollower.followType = "followers"

        runBlocking {
            githubUserDao.insertFollowers(listOf(githubFollower))
        }

        val list = githubUserDao.getSavedUserFollowers("hello","followers")
        assertThat(list.size, equalTo(1))
        assertThat(list[0].login, equalTo("flower"))
    }

    @Test
    @Throws(Exception::class)
    fun writeFollowingAndReadInList() {
        val githubFollower: GithubFollower = getMockFollower()
        githubFollower.ownerUsername = "hello"
        githubFollower.followType = "following"

        runBlocking {
            githubUserDao.insertFollowers(listOf(githubFollower))
        }

        val list = githubUserDao.getSavedUserFollowers("hello","following")
        assertThat(list.size, equalTo(1))
        assertThat(list[0].login, equalTo("tulip"))
    }

    private fun getMockFollower(): GithubFollower {
        return GithubFollower(
            1,
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            1,
            "tulip",
            "",
            "",
            "true",
            "",
            true,
            "",
            "",
            "",
            "",
        )
    }
}