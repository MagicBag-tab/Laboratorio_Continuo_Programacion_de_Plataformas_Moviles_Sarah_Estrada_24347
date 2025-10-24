package com.magicbag.laboratorio_continuo

import com.magicbag.laboratorio_continuo.data.entity.LocationEntity
import com.magicbag.laboratorio_continuo.data.dao.LocationDao

class LocationDb(
    private val locationDao: LocationDao
) {
    private val initialLocations: List<Location> = listOf(
        Location(1, "Earth (C-137)", "Planet", "Dimension C-137"),
        Location(2, "Abadango", "Cluster", "unknown"),
        Location(3, "Citadel of Ricks", "Space station", "unknown"),
        Location(4, "Worldender's lair", "Planet", "unknown"),
        Location(5, "Anatomy Park", "Microverse", "Dimension C-137"),
        Location(6, "Interdimensional Cable", "TV", "unknown"),
        Location(7, "Immortality Field Resort", "Resort", "unknown"),
        Location(8, "Post-Apocalyptic Earth", "Planet", "Post-Apocalyptic Dimension"),
        Location(9, "Purge Planet", "Planet", "Replacement Dimension"),
        Location(10, "Venzenulon 7", "Planet", "unknown"),
        Location(11, "Bepis 9", "Planet", "unknown"),
        Location(12, "Cronenberg Earth", "Planet", "Cronenberg Dimension"),
        Location(13, "Nuptia 4", "Planet", "unknown"),
        Location(14, "Giant's Town", "Fantasy town", "Fantasy Dimension"),
        Location(15, "Bird World", "Planet", "unknown"),
        Location(16, "St. Gloopy Noops Hospital", "Space station", "unknown"),
        Location(17, "Earth (5-126)", "Planet", "Dimension 5-126"),
        Location(18, "Mr. Goldenfold's dream", "Dream", "Dimension C-137"),
        Location(19, "Gromflom Prime", "Planet", "Replacement Dimension"),
        Location(20, "Earth (Replacement Dimension)", "Planet", "Replacement Dimension")
    )

    suspend fun getAllLocations(): List<Location> {
        return locationDao.getAllLocations().map { entity ->
            Location(
                id = entity.id,
                name = entity.nameLocation,
                type = entity.type,
                dimension = entity.dimension
            )
        }
    }

    suspend fun getLocationById(id: Int): Location {
        val entity = locationDao.getLocationById(id)
            ?: throw NoSuchElementException("Location with id $id not found")

        return Location(
            id = entity.id,
            name = entity.nameLocation,
            type = entity.type,
            dimension = entity.dimension
        )
    }

    suspend fun populateInitialData() {
        locationDao.insertAll(initialLocations.map { location ->
            LocationEntity(
                id = location.id,
                nameLocation = location.name,
                type = location.type,
                dimension = location.dimension
            )
        })
    }
}