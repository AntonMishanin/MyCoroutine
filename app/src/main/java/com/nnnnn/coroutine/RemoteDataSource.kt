package com.nnnnn.coroutine

import kotlinx.coroutines.delay

class RemoteDataSource {

    suspend fun getToken(): String {
        delay(timeMillis = 2_000L)
        return "MuToken"
    }

    suspend fun getFreshList(token: String): List<Int> {
        delay(timeMillis = 3_000L)
        return listOf(1, 3, 5, 7)
    }
}