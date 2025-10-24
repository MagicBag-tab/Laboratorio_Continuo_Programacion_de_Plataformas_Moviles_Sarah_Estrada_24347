package com.magicbag.laboratorio_continuo.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterResponse(
    @SerialName("results")
    val results: List<CharacterDto>
)

@Serializable
data class CharacterDto(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("status")
    val status: String,

    @SerialName("species")
    val species: String,

    @SerialName("gender")
    val gender: String,

    @SerialName("image")
    val image: String
)