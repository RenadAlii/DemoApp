package com.renad.demoforlist.core.utils

sealed class Response<out T> {
    class Success<T>(val data: T?) : Response<T>()
    object Loading : Response<Nothing>()
    class Failure(val message: String) : Response<Nothing>()
}
