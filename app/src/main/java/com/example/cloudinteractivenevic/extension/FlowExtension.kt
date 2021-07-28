package com.example.cloudinteractivenevic.extension

import androidx.lifecycle.MutableLiveData
import com.example.cloudinteractivenevic.api.converter.defaultGson
import com.example.cloudinteractivenevic.api.errorHandler.defaultCoroutineExceptionHandler
import com.example.cloudinteractivenevic.api.errorHandler.defaultMessageCoroutineExceptionHandler
import com.example.cloudinteractivenevic.api.response.OnErrorResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import retrofit2.Response
import timber.log.Timber


@OptIn(InternalCoroutinesApi::class)
suspend fun <T> Flow<T>.toLiveData(liveData: MutableLiveData<T>) = collect { liveData.value = it }

@OptIn(ExperimentalCoroutinesApi::class)
fun <T : Response<*>> Flow<T>.onApiFailed (
    doOnApiFailed: suspend (OnErrorResponse) -> Unit
): Flow<T> =
    transform { value ->
        if (!value.isSuccessful) {
            runCatching {
                defaultGson.fromJson(
                    kotlinx.coroutines.withContext(Dispatchers.IO) { value.errorBody()?.string() },
                    OnErrorResponse::class.java
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

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> Flow<T>.launchInWithDefaultErrorHandler(
    scope: CoroutineScope,
    onError: (exceptionMsg: String) -> Unit,
) =
    catch { exception ->
        Timber.d("caught $exception with suppressed ${(exception.suppressed ?: emptyArray()).contentToString()}")
        onError("${exception.message}")
    }.launchIn(scope + defaultMessageCoroutineExceptionHandler { _, exceptionMsg ->
        onError(exceptionMsg)
    })
