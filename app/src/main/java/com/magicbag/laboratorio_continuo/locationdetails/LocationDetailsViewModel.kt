package com.magicbag.laboratorio_continuo.locationdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.magicbag.laboratorio_continuo.LocationDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationDetailsViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(LocationDetailsState())
    val state = _state.asStateFlow()

    private val locationDb = LocationDb()
    private val locationDetails = savedStateHandle.toRoute<LocationDetails>()
    private val locationId = locationDetails.id

    init {
        loadLocationDetails()
    }

    fun loadLocationDetails() {
        _state.update {
            it.copy(
                isLoading = true,
                hasError = false,
                data = null
            )
        }

        viewModelScope.launch {
            delay(2000L)

            val randomNumber = (1..10).random()

            if (randomNumber % 2 == 0) {
                val location = locationDb.getLocationById(locationId)
                _state.update {
                    it.copy(
                        isLoading = false,
                        data = location,
                        hasError = false
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        isLoading = false,
                        data = null,
                        hasError = true
                    )
                }
            }
        }
    }

    fun onRetry() {
        loadLocationDetails()
    }
}