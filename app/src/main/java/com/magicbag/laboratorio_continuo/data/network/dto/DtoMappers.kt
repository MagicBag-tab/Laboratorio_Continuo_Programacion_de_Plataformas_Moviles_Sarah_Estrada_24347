package com.magicbag.laboratorio_continuo.data.network.dto

import com.magicbag.laboratorio_continuo.data.entity.CharacterEntity
import com.magicbag.laboratorio_continuo.data.entity.LocationEntity

fun CharacterDto.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        nameCharacter = name,
        status = status,
        species = species,
        gender = gender,
        image = image
    )
}

fun LocationDto.toEntity(): LocationEntity {
    return LocationEntity(
        id = id,
        nameLocation = name,
        type = type,
        dimension = dimension
    )
}