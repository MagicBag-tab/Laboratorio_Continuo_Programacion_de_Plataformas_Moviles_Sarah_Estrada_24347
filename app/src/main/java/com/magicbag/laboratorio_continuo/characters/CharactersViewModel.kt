package com.magicbag.laboratorio_continuo.characters

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.magicbag.laboratorio_continuo.CharacterDb
import com.magicbag.laboratorio_continuo.LaboratorioDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharactersViewModel(application: Application) : AndroidViewModel(application) {

    private val database = LaboratorioDatabase.getDatabase(application)
    private val characterDb = CharacterDb(database.characterDao())

    private val _state = MutableStateFlow(CharactersState())
    val state: StateFlow<CharactersState> = _state.asStateFlow()

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        _state.update {
            it.copy(
                isLoading = true,
                hasError = false,
                data = emptyList()
            )
        }

        viewModelScope.launch {
            try {
                delay(4000L)

                val randomNumber = (1..10).random()

                if (randomNumber % 2 == 0) {
                    val characters = characterDb.getAllCharacters()
                    _state.update {
                        it.copy(
                            isLoading = false,
                            data = characters,
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
            } catch (e: Exception) {
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
        loadCharacters()
    }
}