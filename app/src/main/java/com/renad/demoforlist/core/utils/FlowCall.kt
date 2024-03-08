package com.renad.demoforlist.core.utils

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

public fun <T> flowCall(block: suspend () -> T) = callbackFlow {
    try {
        this.send(Response.Success(block()))
    } catch (e: Throwable) {
        this.trySend(handleNetworkThrowable(e))
    }
    awaitClose { println("flowCall closed!") }
}