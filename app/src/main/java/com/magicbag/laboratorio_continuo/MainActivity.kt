package com.magicbag.laboratorio_continuo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.magicbag.laboratorio_continuo.ui.theme.AppTheme


//Sarah Rachel Estrada Bonilla
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme(dynamicColor = false) {
                AppNavigation(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}