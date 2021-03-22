package com.example.cloudinteractivenevic.extension

import androidx.lifecycle.MutableLiveData
import com.example.cloudinteractivenevic.api.converter.defaultGson
import com.example.cloudinteractivenevic.api.errorHandler.defaultCoroutineExceptionHandler
import com.example.cloudinteractivenevic.model.ErrorResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.transform
import retrofit2.Response

@OptIn(InternalCoroutinesApi::class)
suspend fun <T> Flow<T>.toLiveData(liveData: MutableLiveData<T>) = collect { liveData.value = it }

@OptIn(ExperimentalCoroutinesApi::class)
fun <T : Response<*>> Flow<T>.onApiFailed(
    doOnApiFailed: suspend (ErrorResponse) -> Unit
): Flow<T> =
    transform { value ->
        if (!value.isSuccessful) {
            runCatching {
                defaultGson.fromJson(
                    withContext(Dispatchers.IO) { value.errorBody()?.string() },
                    ErrorResponse::class.java
                )
            }
                .onSuccess { doOnApiFailed(it) }
                .onFailure { it.printStackTrace() }
            return@transform
        }
        return@transform emit(value)
    }

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> Flow<T>.onEveryEachToLiveData(liveData: MutableLiveData<T>, onSuccess: () -> Unit = {}) =
    transform {
        liveData.value = it
        onSuccess()
        return@transform emit(it)
    }

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> Flow<T>.launchInWithDefaultErrorHandler(scope: CoroutineScope) =
    launchIn(scope + defaultCoroutineExceptionHandler)
