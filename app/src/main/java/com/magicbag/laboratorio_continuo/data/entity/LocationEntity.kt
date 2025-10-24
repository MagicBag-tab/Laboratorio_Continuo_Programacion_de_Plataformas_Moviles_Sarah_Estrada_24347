package com.magicbag.laboratorio_continuo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey
    val id: Int,
    val nameLocation : String,
    val type : String,
    val dimension : String,
)