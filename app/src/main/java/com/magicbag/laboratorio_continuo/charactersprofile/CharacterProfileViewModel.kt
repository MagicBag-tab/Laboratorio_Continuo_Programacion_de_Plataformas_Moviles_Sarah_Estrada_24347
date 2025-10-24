package com.magicbag.laboratorio_continuo.charactersprofile


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.magicbag.laboratorio_continuo.data.repository.CharacterRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterProfileViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val repository = CharacterRepository(application)

    private val _state = MutableStateFlow(CharacterProfileState(isLoading = true))
    val state: StateFlow<CharacterProfileState> = _state.asStateFlow()

    private val characterDetails = savedStateHandle.toRoute<CharacterProfile>()
    private val characterId = characterDetails.id

    init {
        loadCharacterDetails()
    }

    fun loadCharacterDetails() {
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
                    val character = repository.getCharacter(characterId)
                    _state.update {
                        it.copy(
                            isLoading = false,
                            data = character,
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
        loadCharacterDetails()
    }
}