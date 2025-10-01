package com.magicbag.laboratorio_continuo.locations

import com.magicbag.laboratorio_continuo.Location

data class LocationsState(
    val isLoading: Boolean = true,
    val data: List<Location> = emptyList(),
    val hasError: Boolean = false
)