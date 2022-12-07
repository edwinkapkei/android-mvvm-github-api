package com.edwinkapkei.githubapi.data.utilities

sealed class ResourceStatus<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : ResourceStatus<T>(data)
    class Error<T>(data: T? = null, message: String) : ResourceStatus<T>(data, message)
    class Loading<T>(data: T? = null) : ResourceStatus<T>(data)
}