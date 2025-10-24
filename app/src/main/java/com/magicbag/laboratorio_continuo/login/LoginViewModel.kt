package com.magicbag.laboratorio_continuo.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.magicbag.laboratorio_continuo.UserPreferences
import com.magicbag.laboratorio_continuo.dataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginState(
    val isLoading: Boolean = false,
    val userName: String = ""
)

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val userPreferences = UserPreferences(application.dataStore)

    val isLoggedIn = userPreferences.isLoggedIn
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    val userName = userPreferences.userName
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun login(userName: String) {
        if (userName.isBlank()) return

        _state.update { it.copy(isLoading = true, userName = userName) }

        viewModelScope.launch {
            try {
                delay(2000L)

                userPreferences.setLoggedIn(userName)

                _state.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                e.printStackTrace()
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}