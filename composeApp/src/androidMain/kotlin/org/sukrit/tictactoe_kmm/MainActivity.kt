package org.sukrit.tictactoe_kmm

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import shared.data.DataSourceModule

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(dataSourceModule = DataSourceModule(LocalContext.current.applicationContext))
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(dataSourceModule = DataSourceModule(LocalContext.current.applicationContext))
}