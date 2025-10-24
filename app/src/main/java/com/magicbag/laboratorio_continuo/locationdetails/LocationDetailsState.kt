package com.magicbag.laboratorio_continuo.locationdetails

import com.magicbag.laboratorio_continuo.Location

data class LocationDetailsState(
    val isLoading: Boolean = true,
    val data: Location? = null,
    val hasError: Boolean = false
)