package com.example.lbg.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class UserViewModel : ViewModel() {
    private val _username = MutableStateFlow("Guest")
    val username: StateFlow<String> = _username.asStateFlow()

    fun updateName(newName: String) {
        _username.value = newName
    }
}