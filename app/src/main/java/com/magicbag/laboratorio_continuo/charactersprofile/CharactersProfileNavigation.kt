package com.magicbag.laboratorio_continuo.charactersprofile

import kotlinx.serialization.Serializable

@Serializable
data class CharacterProfile(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String
)