package com.example.e_kos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_kos.data.repository.AuthRepository

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> = _registerResult

    fun login(email: String, password: String) {
        repository.login(email, password) {
            _loginResult.value = it
        }
    }

    fun register(email: String, password: String) {
        repository.register(email, password) {
            _registerResult.value = it
        }
    }
}
