package com.magicbag.laboratorio_continuo.charactersprofile

import com.magicbag.laboratorio_continuo.Character

data class CharacterProfileState(
    val isLoading: Boolean = true,
    val data: Character? = null,
    val hasError: Boolean = false
)