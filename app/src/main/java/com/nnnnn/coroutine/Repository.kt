package com.nnnnn.coroutine

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class Repository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    fun getList() = flow<List<Int>> {
        emit(emptyList())
        Log.d("EE", "1")
        val token = remoteDataSource.getToken()
        Log.d("EE", "2")
        val freshList = remoteDataSource.getFreshList(token)
        Log.d("EE", "3")
        localDataSource.saveList(freshList)
        Log.d("EE", "4")
        emit(freshList.map { it * 10 })
    }

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.d("EE", "exceptionHandler")
    }

    suspend fun getValue(): String = withContext(Dispatchers.IO) {
        //remoteDataSource.getFreshList("token")
        try {
            val token = remoteDataSource.getToken()
            val freshList = remoteDataSource.getFreshList(token)
            localDataSource.setToken(token)
            localDataSource.saveList(freshList)
        } catch (e: Exception) {
            return@withContext "ERROR"
        }

        return@withContext "SUCCESS"
    }

    suspend fun setLikeToStoryById(id: String) = withContext(Dispatchers.IO) {
        var token = localDataSource.getToken()
        try {
            remoteDataSource.setLikeToStoryById(id = id, token = token)
        } catch (e: Exception) {
            try {
                refreshToken()
                token = localDataSource.getToken()
                remoteDataSource.setLikeToStoryById(id = id, token = token)
            } catch (e: Exception) {
                return@withContext "ERROR"
            }
        }
        return@withContext "SUCCESS"
    }

    suspend fun setLikeToStoryById2(id: String): String {
        var token = localDataSource.getToken()
        try {
            remoteDataSource.setLikeToStoryById(id = id, token = token)
        } catch (e: Exception) {
            try {
                refreshToken()
                token = localDataSource.getToken()
                remoteDataSource.setLikeToStoryById(id = id, token = token)
            } catch (e: Exception) {
                return "ERROR"
            }
        }
        return "SUCCESS"
    }

    private suspend fun refreshToken() {
        val token = remoteDataSource.getToken()
        localDataSource.setToken(token)
    }

   suspend fun setStoryViewed(): String = coroutineScope {
       throw IllegalAccessException("sdsds")
       return@coroutineScope "fd"
    }

    //suspend fun getValue(): String = withContext(Dispatchers.IO) {
    //    remoteDataSource.getFreshList("token")
    //    return@withContext "MyValue"
    //}
}