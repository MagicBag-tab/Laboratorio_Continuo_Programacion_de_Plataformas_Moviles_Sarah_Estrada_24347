package com.magicbag.laboratorio_continuo.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationResponse(
    @SerialName("results")
    val results: List<LocationDto>
)

@Serializable
data class LocationDto(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("type")
    val type: String,

    @SerialName("dimension")
    val dimension: String
)