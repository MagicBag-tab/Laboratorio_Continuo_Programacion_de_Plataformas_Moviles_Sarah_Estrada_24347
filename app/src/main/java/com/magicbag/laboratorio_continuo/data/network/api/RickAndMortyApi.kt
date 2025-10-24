package com.magicbag.laboratorio_continuo.data.network.api

import com.magicbag.laboratorio_continuo.data.network.dto.CharacterResponse
import com.magicbag.laboratorio_continuo.data.network.dto.LocationResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RickAndMortyApi(
    private val httpClient: HttpClient
) {
    suspend fun getCharacters(): Result<CharacterResponse> {
        return try {
            val response = httpClient.get("character")
            val characters: CharacterResponse = response.body()
            Result.success(characters)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getLocations(): Result<LocationResponse> {
        return try {
            val response = httpClient.get("location")
            val locations: LocationResponse = response.body()
            Result.success(locations)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}