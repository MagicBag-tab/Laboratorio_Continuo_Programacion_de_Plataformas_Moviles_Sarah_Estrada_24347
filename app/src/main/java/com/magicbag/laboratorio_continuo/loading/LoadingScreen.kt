package com.magicbag.laboratorio_continuo.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.magicbag.laboratorio_continuo.ui.theme.AppTheme


@Composable
fun isLoadingScreen(modifier: Modifier = Modifier){

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        CircularProgressIndicator(
            modifier = Modifier
        )
        Text(
            text = "Cargando"
        )
    }
}


@Preview(showBackground = true)
@Composable
fun LoadingPreview() {
    AppTheme {
        isLoadingScreen()
    }
}