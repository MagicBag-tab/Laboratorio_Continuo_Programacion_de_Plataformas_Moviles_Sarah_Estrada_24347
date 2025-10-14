package com.magicbag.laboratorio_continuo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.magicbag.laboratorio_continuo.ui.theme.AppTheme
import kotlinx.coroutines.launch


//Sarah Rachel Estrada Bonilla
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = LaboratorioDatabase.getDatabase(this)

        lifecycleScope.launch {
            val characterDao = database.characterDao()
            val locationDao = database.locationDao()

            if (characterDao.getAllCharacters().isEmpty()) {
                val dummyCharacters = listOf(
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

                characterDao.insertAll(dummyCharacters.map { character ->
                    CharacterEntity(
                        id = character.id,
                        nameCharacter = character.name,
                        status = character.status,
                        species = character.species,
                        gender = character.gender,
                        image = character.image
                    )
                })
            }

            if (locationDao.getAllLocations().isEmpty()) {
                val dummyLocations = listOf(
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

                locationDao.insertAll(dummyLocations.map { location ->
                    LocationEntity(
                        id = location.id,
                        nameLocation = location.name,
                        type = location.type,
                        dimension = location.dimension
                    )
                })
            }
        }

        setContent {
            AppTheme(dynamicColor = false) {
                AppNavigation(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}