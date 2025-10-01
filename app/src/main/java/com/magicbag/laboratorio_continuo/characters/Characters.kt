package com.magicbag.laboratorio_continuo.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.magicbag.laboratorio_continuo.Character
import com.magicbag.laboratorio_continuo.loading.hasErrorScreen
import com.magicbag.laboratorio_continuo.loading.isLoadingScreen

@Composable
fun CharactersScreen(
    modifier: Modifier = Modifier,
    viewModel: CharactersViewModel = viewModel(),
    onCharacterClick: (Character) -> Unit = {},
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    when {
        state.isLoading -> {
            isLoadingScreen()
        }
        state.hasError -> {
            hasErrorScreen(
                onRetry = { viewModel.onRetry() }
            )
        }
        else -> {
            CharactersContent(
                characters = state.data,
                onCharacterClick = onCharacterClick,
                onBackClick= onBackClick,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun CharactersContent(
    characters: List<Character>,
    onCharacterClick: (Character) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
                .height(56.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            Text(
                text = "Characters",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = FontWeight.Bold
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(characters) { character ->
                Card(
                    onClick = {
                        onCharacterClick(character)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    CharacterItem(character = character)
                }
            }
        }
    }
}

@Composable
fun CharacterItem(
    character: Character,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            model = character.image,
            contentDescription = character.name,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = character.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "${character.species} - ${character.status} - ${character.gender}",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}