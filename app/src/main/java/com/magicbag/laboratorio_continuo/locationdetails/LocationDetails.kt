package com.magicbag.laboratorio_continuo.locationdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.magicbag.laboratorio_continuo.Location
import com.magicbag.laboratorio_continuo.loading.hasErrorScreen
import com.magicbag.laboratorio_continuo.loading.isLoadingScreen

@Composable
fun LocationDetailsScreen(
    locationId: Int,
    onBackClick: () -> Unit,
    viewModel: LocationDetailsViewModel = viewModel()
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
            state.data?.let { location ->
                LocationDetailsContent(
                    location = location,
                    onBackClick = onBackClick
                )
            }
        }
    }
}

@Composable
private fun LocationDetailsContent(
    location: Location,
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
                text = "Location details",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = location.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            LocationDetailItem("ID:", location.id.toString())
            LocationDetailItem("Name:", location.name)
            LocationDetailItem("Type:", location.type)
            LocationDetailItem("Dimension:", location.dimension)
        }
    }
}

@Composable
fun LocationDetailItem(
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