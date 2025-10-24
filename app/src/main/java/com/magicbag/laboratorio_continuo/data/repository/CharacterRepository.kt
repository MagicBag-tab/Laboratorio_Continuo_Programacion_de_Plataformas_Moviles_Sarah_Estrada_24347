package com.magicbag.laboratorio_continuo.data.repository

import android.app.Application
import com.magicbag.laboratorio_continuo.Character
import com.magicbag.laboratorio_continuo.LaboratorioDatabase
import com.magicbag.laboratorio_continuo.data.network.HttpClientFactory
import com.magicbag.laboratorio_continuo.data.network.api.RickAndMortyApi
import com.magicbag.laboratorio_continuo.data.network.dto.toEntity
import java.util.NoSuchElementException
import com.magicbag.laboratorio_continuo.data.entity.toCharacter


class CharacterRepository(
    application: Application
) {
    private val database = LaboratorioDatabase.getDatabase(application)
    private val characterDao = database.characterDao()
    private val httpClient = HttpClientFactory.create()
    private val api = RickAndMortyApi(httpClient)

    suspend fun getCharacters(): List<Character> {
        var localCharacters = characterDao.getAllCharacters()

        if (localCharacters.isEmpty()) {
            val result = api.getCharacters()

            if (result.isSuccess) {
                val response = result.getOrNull()
                val entities = response?.results?.map { it.toEntity() } ?: emptyList()

                if (entities.isNotEmpty()) {
                    characterDao.insertAll(entities)
                    localCharacters = characterDao.getAllCharacters()
                }
            } else {
                return emptyList()
            }
        }

        return localCharacters.map { entity -> entity.toCharacter() }
    }

    suspend fun getCharacter(id: Int): Character {
        val entity = characterDao.getCharacterById(id)
            ?: throw NoSuchElementException("Character with id $id not found")

        return entity.toCharacter()
    }
}
