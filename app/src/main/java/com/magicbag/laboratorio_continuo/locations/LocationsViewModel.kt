package com.magicbag.laboratorio_continuo.locations

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.magicbag.laboratorio_continuo.data.repository.LocationRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = LocationRepository(application)

    private val _state = MutableStateFlow(LocationsState())
    val state: StateFlow<LocationsState> = _state.asStateFlow()

    init {
        loadLocations()
    }

    fun loadLocations() {
        _state.update {
            it.copy(
                isLoading = true,
                hasError = false,
                data = emptyList()
            )
        }

        viewModelScope.launch {
            try {
                delay(4000L)

                val randomNumber = (1..10).random()

                if (randomNumber % 2 == 0) {
                    val locations = repository.getLocations()
                    _state.update {
                        it.copy(
                            isLoading = false,
                            data = locations,
                            hasError = false
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            data = emptyList(),
                            hasError = true
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        data = emptyList(),
                        hasError = true
                    )
                }
            }
        }
    }

    fun onRetry() {
        loadLocations()
    }
}