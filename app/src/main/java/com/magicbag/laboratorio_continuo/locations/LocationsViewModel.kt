package com.magicbag.laboratorio_continuo.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magicbag.laboratorio_continuo.LocationDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationsViewModel : ViewModel() {
    private val _state = MutableStateFlow(LocationsState())
    val state = _state.asStateFlow()

    private val locationDb = LocationDb()

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
            delay(4000L)

            val randomNumber = (1..10).random()

            if (randomNumber % 2 == 0) {
                val locations = locationDb.getAllLocations()
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
        }
    }

    fun onRetry() {
        loadLocations()
    }
}