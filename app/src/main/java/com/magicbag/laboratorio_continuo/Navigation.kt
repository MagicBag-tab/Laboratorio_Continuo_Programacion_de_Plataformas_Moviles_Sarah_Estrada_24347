package com.magicbag.laboratorio_continuo

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.magicbag.laboratorio_continuo.login.Login
import com.magicbag.laboratorio_continuo.login.LoginScreen
import com.magicbag.laboratorio_continuo.characters.Characters
import com.magicbag.laboratorio_continuo.characters.CharactersScreen
import com.magicbag.laboratorio_continuo.charactersprofile.CharacterProfile
import com.magicbag.laboratorio_continuo.charactersprofile.CharacterProfileScreen
import com.magicbag.laboratorio_continuo.locations.Locations
import com.magicbag.laboratorio_continuo.locations.LocationsScreen
import com.magicbag.laboratorio_continuo.locationdetails.LocationDetails
import com.magicbag.laboratorio_continuo.locationdetails.LocationDetailsScreen
import com.magicbag.laboratorio_continuo.profile.Profile
import com.magicbag.laboratorio_continuo.profile.ProfileScreen
import kotlinx.serialization.Serializable

@Serializable
data object MainScreen

@Serializable
data object CharactersRoute

@Serializable
data object LocationsRoute

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Login,
        modifier = modifier
    ) {
        composable<Login> {
            LoginScreen(
                onLoginClick = {
                    navController.navigate(MainScreen) {
                        popUpTo(Login) { inclusive = true }
                    }
                }
            )
        }

        composable<MainScreen> {
            MainScreenWithBottomNav(
                onLogout = {
                    navController.navigate(Login) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}

@Composable
fun MainScreenWithBottomNav(
    onLogout: () -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val activity = LocalActivity.current as? Activity

    val bottomNavItems = listOf(
        BottomNavItem("Characters", Icons.Default.Star, CharactersRoute),
        BottomNavItem("Locations", Icons.Default.LocationOn, LocationsRoute),
        BottomNavItem("Profile", Icons.Default.Person, Profile)
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ) {
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label
                            )
                        },
                        label = {
                            Text(
                                text = item.label,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Medium
                            )
                        },
                        selected = currentDestination?.hierarchy?.any {
                            it.hasRoute(item.route::class)
                        } == true,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = CharactersRoute,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            navigation<CharactersRoute>(startDestination = Characters) {
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
                        onBackClick = {
                            activity?.finish()
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

            navigation<LocationsRoute>(startDestination = Locations) {
                composable<Locations> {
                    LocationsScreen(
                        onLocationClick = { location ->
                            navController.navigate(
                                LocationDetails(id = location.id)
                            )
                        },
                        onBackClick = {
                            activity?.finish()
                        }
                    )
                }

                composable<LocationDetails> { backStackEntry ->
                    val locationDetails = backStackEntry.toRoute<LocationDetails>()
                    LocationDetailsScreen(
                        locationId = locationDetails.id,
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
            }

            composable<Profile> {
                ProfileScreen(
                    onLogoutClick = onLogout
                )
            }
        }
    }
}

data class BottomNavItem(
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val route: Any
)