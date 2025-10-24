package com.magicbag.laboratorio_continuo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey
    val id: Int,
    val nameCharacter : String,
    val status : String,
    val species : String,
    val gender : String,
    val image : String,
)