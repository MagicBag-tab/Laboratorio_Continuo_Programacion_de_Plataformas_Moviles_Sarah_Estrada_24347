package com.magicbag.laboratorio_continuo

class CharacterDb(
    private val characterDao: CharacterDao
) {
    private val initialCharacters: List<Character> = listOf(
        Character(1, "Rick Sanchez", "Alive", "Human", "Male", "https://rickandmortyapi.com/api/character/avatar/1.jpeg"),
        Character(2, "Morty Smith", "Alive", "Human", "Male", "https://rickandmortyapi.com/api/character/avatar/2.jpeg"),
        Character(3, "Summer Smith", "Alive", "Human", "Female", "https://rickandmortyapi.com/api/character/avatar/3.jpeg"),
        Character(4, "Beth Smith", "Alive", "Human", "Female", "https://rickandmortyapi.com/api/character/avatar/4.jpeg"),
        Character(5, "Jerry Smith", "Alive", "Human", "Male", "https://rickandmortyapi.com/api/character/avatar/5.jpeg"),
        Character(6, "Abadango Cluster Princess", "Alive", "Alien", "Female", "https://rickandmortyapi.com/api/character/avatar/6.jpeg"),
        Character(7, "Abradolf Lincler", "unknown", "Human", "Male", "https://rickandmortyapi.com/api/character/avatar/7.jpeg"),
        Character(8, "Adjudicator Rick", "Dead", "Human", "Male", "https://rickandmortyapi.com/api/character/avatar/8.jpeg"),
        Character(9, "Agency Director", "Dead", "Human", "Male", "https://rickandmortyapi.com/api/character/avatar/9.jpeg"),
        Character(10, "Alan Rails", "Dead", "Human", "Male", "https://rickandmortyapi.com/api/character/avatar/10.jpeg"),
        Character(11, "Albert Einstein", "Dead", "Human", "Male", "https://rickandmortyapi.com/api/character/avatar/11.jpeg"),
        Character(12, "Alexander", "Dead", "Human", "Male", "https://rickandmortyapi.com/api/character/avatar/12.jpeg"),
        Character(13, "Alien Googah", "unknown", "Alien", "unknown", "https://rickandmortyapi.com/api/character/avatar/13.jpeg"),
        Character(14, "Alien Morty", "unknown", "Alien", "Male", "https://rickandmortyapi.com/api/character/avatar/14.jpeg"),
        Character(15, "Alien Rick", "unknown", "Alien", "Male", "https://rickandmortyapi.com/api/character/avatar/15.jpeg"),
        Character(16, "Amish Cyborg", "Dead", "Alien", "Male", "https://rickandmortyapi.com/api/character/avatar/16.jpeg"),
        Character(17, "Annie", "Alive", "Human", "Female", "https://rickandmortyapi.com/api/character/avatar/17.jpeg"),
        Character(18, "Antenna Morty", "Alive", "Human", "Male", "https://rickandmortyapi.com/api/character/avatar/18.jpeg"),
        Character(19, "Antenna Rick", "unknown", "Human", "Male", "https://rickandmortyapi.com/api/character/avatar/19.jpeg"),
        Character(20, "Ants in my Eyes Johnson", "unknown", "Human", "Male", "https://rickandmortyapi.com/api/character/avatar/20.jpeg")
    )

    suspend fun getAllCharacters(): List<Character> {
        return characterDao.getAllCharacters().map { entity ->
            Character(
                id = entity.id,
                name = entity.nameCharacter,
                status = entity.status,
                species = entity.species,
                gender = entity.gender,
                image = entity.image
            )
        }
    }
    suspend fun getCharacterById(id: Int): Character {
        val entity = characterDao.getCharacterById(id)
            ?: throw NoSuchElementException("Character with id $id not found")

        return Character(
            id = entity.id,
            name = entity.nameCharacter,
            status = entity.status,
            species = entity.species,
            gender = entity.gender,
            image = entity.image
        )
    }

    suspend fun populateInitialData() {
        characterDao.insertAll(initialCharacters.map { character ->
            CharacterEntity(
                id = character.id,
                nameCharacter = character.name,
                status = character.status,
                species = character.species,
                gender = character.gender,
                image = character.image
            )
            }
        )
    }
}