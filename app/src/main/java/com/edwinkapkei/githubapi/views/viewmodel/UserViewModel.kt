package com.edwinkapkei.githubapi.views.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.edwinkapkei.githubapi.data.model.GithubFollower
import com.edwinkapkei.githubapi.data.model.GithubUser
import com.edwinkapkei.githubapi.data.utilities.ResourceStatus
import com.edwinkapkei.githubapi.domain.usecases.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(
    private val app: Application,
    private val getGithubUserUseCase: GetGithubUserUseCase,
    private val getGithubUserFollowers: GetGithubUserFollowers,
    private val saveGithubUserUseCase: SaveGithubUserUseCase,
    private val saveGithubFollowerUseCase: SaveGithubFollowerUseCase,
    private val getSavedUserUseCase: GetSavedUserUseCase,
    private val getSavedFollowersUseCase: GetSavedFollowersUseCase
) : AndroidViewModel(app) {

    val githubUser: MutableLiveData<ResourceStatus<GithubUser>> = MutableLiveData()
    val userFollowers: MutableLiveData<ResourceStatus<List<GithubFollower>>> =
        MutableLiveData()
    val userFollowing: MutableLiveData<ResourceStatus<List<GithubFollower>>> =
        MutableLiveData()

    fun getUser(username: String) = viewModelScope.launch(Dispatchers.IO) {
        githubUser.postValue(ResourceStatus.Loading())

        try {
            if (isNetworkAvailable(app)) {
                val apiResult = getGithubUserUseCase.execute(username)
                if (apiResult.data != null) {
                    githubUser.postValue(ResourceStatus.Success(apiResult.data))
                } else {
                    githubUser.postValue(ResourceStatus.Error("An error occurred"))
                }
            } else {
                getSavedUserUseCase.execute(username).collect {
                    if (it != null) {
                        githubUser.postValue(ResourceStatus.Success(it))
                    } else {
                        githubUser.postValue(ResourceStatus.Error("Internet is not available"))
                    }
                }
            }
        } catch (e: Exception) {
            githubUser.postValue(ResourceStatus.Error(e.message.toString()))
        }
    }

    fun getUserFollowers(username: String, followType: String, perPage: Int, page: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            userFollowers.postValue(ResourceStatus.Loading())

            try {
                if (isNetworkAvailable(app)) {
                    val apiResult =
                        getGithubUserFollowers.execute(username, followType, perPage, page)
                    userFollowers.postValue(apiResult)
                } else {
                    getSavedFollowersUseCase.execute(username, followType).collect {
                        if (it.isNotEmpty()) {
                            userFollowers.postValue(ResourceStatus.Success(it))
                        } else {
                            userFollowers.postValue(ResourceStatus.Error("Internet is not available"))
                        }
                    }
                }
            } catch (e: Exception) {
                userFollowers.postValue(ResourceStatus.Error(e.message.toString()))
            }
        }

    fun saveUser(githubUser: GithubUser) =
        viewModelScope.launch { saveGithubUserUseCase.execute(githubUser) }

    fun saveUserFollowers(
        username: String,
        followType: String,
        githubFollowers: List<GithubFollower>
    ) =
        viewModelScope.launch {
            for (follower in githubFollowers) {
                follower.ownerUsername = username
                follower.followType = followType
            }
            saveGithubFollowerUseCase.execute(githubFollowers)
        }

    fun getSavedUser(username: String) = liveData {
        getSavedUserUseCase.execute(username).collect() {
            emit(it)
        }
    }

    fun getSavedUserFollowers(username: String, followType: String) = liveData {
        getSavedFollowersUseCase.execute(username, followType).collect() {
            emit(it)
        }
    }

    @Suppress("DEPRECATION")
    fun isNetworkAvailable(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    }
}