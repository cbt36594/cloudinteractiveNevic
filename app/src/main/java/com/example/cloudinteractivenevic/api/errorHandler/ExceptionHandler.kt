package com.example.cloudinteractivenevic.api.errorHandler

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler

val defaultCoroutineExceptionHandler =
    CoroutineExceptionHandler { _, exception ->
        Log.d(
            "",
            "CoroutineExceptionHandler got ${exception} with suppressed ${(exception.suppressed ?: emptyArray()).contentToString()}",
        )
    }
fun defaultMessageCoroutineExceptionHandler(
    onError: (code: Int, message: String) -> Unit,
): CoroutineExceptionHandler =
    CoroutineExceptionHandler { coroutineContext, exception ->
        defaultCoroutineExceptionHandler.handleException(coroutineContext, exception)
        onError(exception.hashCode(), "${exception.message}")
    }
fun defaultCoroutineExceptionHandler(
    onError: () -> Unit): CoroutineExceptionHandler =
    CoroutineExceptionHandler { coroutineContext, exception ->
        defaultCoroutineExceptionHandler.handleException(
            coroutineContext, exception)
        onError()
    }