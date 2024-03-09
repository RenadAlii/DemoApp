package com.renad.demoforlist.core.utils

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun <T> flowCall(block: suspend () -> T) =
    callbackFlow {
        try {
            this.send((block()))
        } catch (e: Throwable) {
            this.trySend(e)
        }
        awaitClose { println("flowCall closed!") }
    }
