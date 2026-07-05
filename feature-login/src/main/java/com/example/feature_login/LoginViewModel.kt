package com.example.feature_login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _isLoginEnabled = MutableLiveData(false)
    val isLoginEnabled: LiveData<Boolean> = _isLoginEnabled

    private val _navigateToMain = MutableLiveData<Boolean>()
    val navigateToMain: LiveData<Boolean> = _navigateToMain

    // Простая валидация email: текст@текст.текст
    fun validateEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
        checkLoginEnabled()
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
        checkLoginEnabled()
    }

    private fun checkLoginEnabled() {
        val isEmailValid = validateEmail(_email.value ?: "")
        val isPasswordNotEmpty = !(_password.value ?: "").isBlank()
        _isLoginEnabled.value = isEmailValid && isPasswordNotEmpty
    }

    fun onLoginClick() {
        if (_isLoginEnabled.value == true) {
            _navigateToMain.value = true
        }
    }

    fun onVKClick() {
        // Открываем https://vk.com/
    }

    fun onOKClick() {
        // Открываем https://ok.ru/
    }

}