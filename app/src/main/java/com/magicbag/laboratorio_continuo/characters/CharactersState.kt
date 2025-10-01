
package com.magicbag.laboratorio_continuo.characters

import com.magicbag.laboratorio_continuo.Character

data class CharactersState(
    val isLoading: Boolean = true,
    val data: List<Character> = emptyList(),
    val hasError: Boolean = false
)