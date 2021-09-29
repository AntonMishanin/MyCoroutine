package com.nnnnn.coroutine

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

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

    suspend fun getList1() = withContext(Dispatchers.IO){

    }
}