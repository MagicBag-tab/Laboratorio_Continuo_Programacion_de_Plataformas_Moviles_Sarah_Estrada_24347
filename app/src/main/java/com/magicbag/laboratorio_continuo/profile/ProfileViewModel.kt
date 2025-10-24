package com.magicbag.laboratorio_continuo.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.magicbag.laboratorio_continuo.UserPreferences
import com.magicbag.laboratorio_continuo.dataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileState(
    val isLoggingOut: Boolean = false
)

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val userPreferences = UserPreferences(application.dataStore)

    val userName = userPreferences.userName
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    fun logout(onLogoutComplete: () -> Unit) {
        _state.update { it.copy(isLoggingOut = true) }

        viewModelScope.launch {
            try {
                userPreferences.clear()
                onLogoutComplete()
            } catch (e: Exception) {
                _state.update { it.copy(isLoggingOut = false) }
            }
        }
    }
}