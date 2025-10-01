package com.magicbag.laboratorio_continuo.charactersprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun CharacterProfileScreen(
    id: Int,
    name: String,
    status: String,
    species: String,
    gender: String,
    image: String,
    onBackClick: () -> Unit,
    viewModel: CharacterProfileViewModel = viewModel()
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
            state.data?.let { character ->
                CharacterProfileContent(
                    character = character,
                    onBackClick = onBackClick
                )
            }
        }
    }
}

@Composable
private fun CharacterProfileContent(
    character: Character,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
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
                text = "Character Details",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = FontWeight.Bold
            )
        }

        AsyncImage(
            model = character.image,
            contentDescription = character.name,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Text(
            text = character.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Details("Species:", character.species)
            Details("Status:", character.status)
            Details("Gender:", character.gender)
        }
    }
}

@Composable
fun Details(
    title: String,
    body: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 64.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = body,
            fontWeight = FontWeight.Normal
        )
    }
}