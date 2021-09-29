package com.nnnnn.coroutine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    fun launch() {
        viewModelScope.launch(Dispatchers.Default) {

        }
    }
}