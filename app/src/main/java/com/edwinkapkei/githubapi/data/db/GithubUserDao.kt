package com.edwinkapkei.githubapi.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.edwinkapkei.githubapi.data.model.GithubFollower
import com.edwinkapkei.githubapi.data.model.GithubUser
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(githubUser: GithubUser)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollowers(githubFollower: List<GithubFollower>)

    @Query("SELECT * FROM githubuser WHERE login=:username")
    fun getSavedUser(username: String): Flow<GithubUser>

    @Query("SELECT * FROM githubfollower WHERE ownerUsername=:username AND followType=:followType")
    fun getSavedUserFollowers(username: String, followType: String): Flow<List<GithubFollower>>
}