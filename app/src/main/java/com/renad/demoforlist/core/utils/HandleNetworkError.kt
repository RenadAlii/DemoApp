package com.renad.demoforlist.core.utils

import com.renad.demoforlist.core.utils.NetworkErrorException.Companion.parseException
import org.json.JSONObject
import retrofit2.HttpException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <T> handleNetworkThrowable(e: Throwable): Response<T> {
    var error = ""

    when (e) {
        is SocketTimeoutException -> {
            error = "Connection error!"
        }
        is ConnectException -> {
            error = "No internet access!"
        }
        is UnknownHostException -> {
            error = "No internet access!"
        }
        is HttpException -> {
            when (e.code()) {
                HttpURLConnection.HTTP_BAD_GATEWAY -> {
                    error = NetworkErrorException("Internal error!").errorMessage
                }

                // unauthorized with 401 error
                HttpURLConnection.HTTP_UNAUTHORIZED -> {
                    error = "Authentication error!"
                }
                // access denied
                HttpURLConnection.HTTP_FORBIDDEN -> {
                    error = "Access denied!"
                }
                else -> {
                    error = parseException(e).errorMessage
                }
            }
        }
        else -> {
            error = "An unknown error occurred."
        }
    }

    return Response.Failure(error)
}

open class NetworkErrorException(
//    val errorCode: Int = -1,
    val errorMessage: String,
) : Exception() {
    override val message: String
        get() = localizedMessage

    override fun getLocalizedMessage(): String {
        return errorMessage
    }

    companion object {
        fun parseException(e: HttpException): NetworkErrorException {
            val errorBody = e.response()?.errorBody()?.string()

            return try {
                // here you can parse the error body that comes from server
                NetworkErrorException(JSONObject(errorBody!!).getString("message"))
            } catch (_: Exception) {
                NetworkErrorException("unexpected error!!ً")
            }
        }
    }
}
