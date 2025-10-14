package com.magicbag.laboratorio_continuo.locationdetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.magicbag.laboratorio_continuo.LaboratorioDatabase
import com.magicbag.laboratorio_continuo.LocationDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationDetailsViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val database = LaboratorioDatabase.getDatabase(application)
    private val locationDb = LocationDb(database.locationDao())

    private val _state = MutableStateFlow(LocationDetailsState(isLoading = true))
    val state: StateFlow<LocationDetailsState> = _state.asStateFlow()

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
            try {
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
            } catch (e: Exception) {
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