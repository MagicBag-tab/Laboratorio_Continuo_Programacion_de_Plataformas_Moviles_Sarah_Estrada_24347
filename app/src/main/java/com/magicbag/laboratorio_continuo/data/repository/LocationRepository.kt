package com.magicbag.laboratorio_continuo.data.repository

import android.app.Application
import com.magicbag.laboratorio_continuo.LaboratorioDatabase
import com.magicbag.laboratorio_continuo.Location
import com.magicbag.laboratorio_continuo.data.network.HttpClientFactory
import com.magicbag.laboratorio_continuo.data.network.api.RickAndMortyApi
import com.magicbag.laboratorio_continuo.data.network.dto.toEntity
import com.magicbag.laboratorio_continuo.data.entity.toLocation
import java.util.NoSuchElementException

class LocationRepository(
    application: Application
) {
    private val database = LaboratorioDatabase.getDatabase(application)
    private val locationDao = database.locationDao()
    private val httpClient = HttpClientFactory.create()
    private val api = RickAndMortyApi(httpClient)

    suspend fun getLocations(): List<Location> {
        var localLocations = locationDao.getAllLocations()

        if (localLocations.isEmpty()) {
            val result = api.getLocations()

            if (result.isSuccess) {
                val response = result.getOrNull()
                val entities = response?.results?.map { it.toEntity() } ?: emptyList()

                if (entities.isNotEmpty()) {
                    locationDao.insertAll(entities)
                    localLocations = locationDao.getAllLocations()
                }
            } else {
                return emptyList()
            }
        }

        return localLocations.map { entity -> entity.toLocation() }
    }

    suspend fun getLocation(id: Int): Location {
        val entity = locationDao.getLocationById(id)
            ?: throw NoSuchElementException("Location with id $id not found")

        return entity.toLocation()
    }
}
