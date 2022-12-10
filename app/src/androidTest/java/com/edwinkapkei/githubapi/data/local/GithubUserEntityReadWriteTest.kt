package com.edwinkapkei.githubapi.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.edwinkapkei.githubapi.data.db.GithubUserDao
import com.edwinkapkei.githubapi.data.db.GithubUserDatabase
import com.edwinkapkei.githubapi.data.model.GithubUser
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class GithubUserEntityReadWriteTest {
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
    fun writeUserAndReadInList() {
        val githubUser: GithubUser = getMockUser()

        runBlocking {
            githubUserDao.insertUser(githubUser)
        }

        val name = githubUserDao.getSavedUser("hello")?.name
        assertThat(name, equalTo("daisy"))
    }

    private fun getMockUser(): GithubUser {
        return GithubUser(
            1,
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            1,
            "",
            1,
            "",
            "",
            "",
            true,
            "",
            1,
            "",
            "hello",
            "daisy",
            "",
            "",
            2,
            2,
            "",
            "",
            true,
            "",
            "",
            "",
            "",
            "",
            ""
        )
    }
}