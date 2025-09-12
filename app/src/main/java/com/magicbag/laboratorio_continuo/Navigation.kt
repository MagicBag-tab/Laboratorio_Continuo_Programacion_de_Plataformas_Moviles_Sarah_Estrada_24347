package com.magicbag.laboratorio_continuo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

@Composable
fun AppNavigation(modifier: Modifier = Modifier){

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Login,
        modifier = modifier
    ){
        composable<Login>{
            LoginScreen(
                onLoginClick = {
                    navController.navigate(Characters)
                }
            )
        }

        composable<Characters> {
            CharactersScreen(
                onCharacterClick = { character ->
                    navController.navigate(
                        CharacterProfile(
                            id = character.id,
                            name = character.name,
                            status = character.status,
                            species = character.species,
                            gender = character.gender,
                            image = character.image
                        )
                    )
                },
                onBackClickLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable<CharacterProfile> { backStackEntry ->
            val characterProfile = backStackEntry.toRoute<CharacterProfile>()
            CharacterProfileScreen(
                id = characterProfile.id,
                name = characterProfile.name,
                status = characterProfile.status,
                species = characterProfile.species,
                gender = characterProfile.gender,
                image = characterProfile.image,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}