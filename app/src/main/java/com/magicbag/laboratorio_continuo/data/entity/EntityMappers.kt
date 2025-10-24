package com.magicbag.laboratorio_continuo.data.entity

import com.magicbag.laboratorio_continuo.Character
import com.magicbag.laboratorio_continuo.Location

fun CharacterEntity.toCharacter(): Character {
    return Character(
        id = this.id,
        name = this.nameCharacter,
        status = this.status,
        species = this.species,
        gender = this.gender,
        image = this.image
    )
}

fun LocationEntity.toLocation(): Location {
    return Location(
        id = this.id,
        name = this.nameLocation,
        type = this.type,
        dimension = this.dimension
    )
}
