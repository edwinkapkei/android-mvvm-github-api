package com.edwinkapkei.githubapi.views.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.edwinkapkei.githubapi.data.model.GithubFollower
import com.edwinkapkei.githubapi.data.model.GithubUser
import com.edwinkapkei.githubapi.data.utilities.ResourceStatus
import com.edwinkapkei.githubapi.domain.usecases.GetGithubUserFollowers
import com.edwinkapkei.githubapi.domain.usecases.GetGithubUserFollowing
import com.edwinkapkei.githubapi.domain.usecases.GetGithubUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(
    private val app: Application,
    private val getGithubUserUseCase: GetGithubUserUseCase,
    private val getGithubUserFollowers: GetGithubUserFollowers,
    private val getGithubUserFollowing: GetGithubUserFollowing
) : AndroidViewModel(app) {

    private val githubUser: MutableLiveData<ResourceStatus<GithubUser>> = MutableLiveData()
    private val userFollowers: MutableLiveData<ResourceStatus<List<GithubFollower>>> =
        MutableLiveData()
    private val userFollowing: MutableLiveData<ResourceStatus<List<GithubFollower>>> =
        MutableLiveData()

    fun getUser(username: String) = viewModelScope.launch(Dispatchers.IO) {
        githubUser.postValue(ResourceStatus.Loading())

        try {
            if (isNetworkAvailable(app)) {
                val apiResult = getGithubUserUseCase.execute(username)
                githubUser.postValue(apiResult)
            } else {
                githubUser.postValue(ResourceStatus.Error("Internet is not available"))
            }
        } catch (e: Exception) {
            githubUser.postValue(ResourceStatus.Error(e.message.toString()))
        }
    }

    fun getUserFollowers(username: String, perPage: Int, page: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            userFollowers.postValue(ResourceStatus.Loading())

            try {
                if (isNetworkAvailable(app)) {
                    val apiResult = getGithubUserFollowers.execute(username, perPage, page)
                    userFollowers.postValue(apiResult)
                } else {
                    userFollowers.postValue(ResourceStatus.Error("Internet is not available"))
                }
            } catch (e: Exception) {
                userFollowers.postValue(ResourceStatus.Error(e.message.toString()))
            }
        }

    fun getUserFollowing(username: String, perPage: Int, page: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            userFollowing.postValue(ResourceStatus.Loading())

            try {
                if (isNetworkAvailable(app)) {
                    val apiResult = getGithubUserFollowing.execute(username, perPage, page)
                    userFollowing.postValue(apiResult)
                } else {
                    userFollowing.postValue(ResourceStatus.Error("Internet is not available"))
                }
            } catch (e: Exception) {
                userFollowing.postValue(ResourceStatus.Error(e.message.toString()))
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