package com.nnnnn.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

class MyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Log.d("EE", "1 currentThread = ${Thread.currentThread()}")
        lifecycleScope.launch(Dispatchers.Main) {
            //Log.d("EE", "2 currentThread = ${Thread.currentThread()}")
            myLaunch()
        }

        val myScope = CoroutineScope(Job() + Dispatchers.IO)
        val job = myScope.launch {

        }

        //Не привязан к моему скопу
        GlobalScope.launch {

        }
        // Log.d("EE", "DO")
        //Блокирует основной поток
        runBlocking {
            // Log.d("EE", "START")
            async(Dispatchers.IO) {
                val startTime = System.currentTimeMillis()
                while (System.currentTimeMillis() - startTime < 1_000) {

                }
            }
        }

        //Это не связано с корутинами, но с помощью этой фичи можно посмотреть,
        //за сколько времени выполнился код внутри
        measureTimeMillis {

        }

        //Обработка исключений
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->

        }
        lifecycleScope.launch(context = exceptionHandler) {

        }

        lifecycleScope.launch(Dispatchers.IO) {
           // Log.d("EE", "START")
            val result = fetchContent()
            //Log.d("EE", "RESULT = $result")
        }
        //Log.d("EE", "POSLE")

        val myFlow: Flow<Int> = flow {
            emit(4)
            emit(5)

        }.map {
            it
        }.filter {
            true
        }

        lifecycleScope.launch {
            myFlow
                .catch { }
                .collect { }
        }

        val remoteDataSource = RemoteDataSource()
        val localDataSource = LocalDataSource()
        val repository = Repository(remoteDataSource, localDataSource)

       //lifecycleScope.launch {
       //    repository.getList()
       //        .catch {  }
       //        .collect {
       //            Log.d("EE", "${it}")
       //        }
       //}

        lifecycleScope.launch {
            Log.d("EE", "START VALUE")
            val result = repository.getValue()
            Log.d("EE", "result")
        }

        test{

           "TEST"
        }

        lifecycleScope.launch {
            val result = repository.setStoryViewed()
        }
    }

    fun test(callback: (Int)-> String){
       val myString = callback(4)
        Log.d("EE", "myString = $myString")
    }

    private suspend fun fetchContent(): Int {
        return coroutineScope {
            val result1 = async {
                val startTime = System.currentTimeMillis()
                while (System.currentTimeMillis() - startTime < 5_000) {

                }
                return@async 5
            }
            val result2 = async {
                val startTime = System.currentTimeMillis()
                while (System.currentTimeMillis() - startTime < 7_000) {

                }
                return@async 3
            }
            return@coroutineScope result1.await() + result2.await()
        }
    }

    private suspend fun myLaunch() {
        // Log.d("EE", "3 currentThread = ${Thread.currentThread()}")
        delay(1_000)
        //Log.d("EE", "4 currentThread = ${Thread.currentThread()}")
    }
}