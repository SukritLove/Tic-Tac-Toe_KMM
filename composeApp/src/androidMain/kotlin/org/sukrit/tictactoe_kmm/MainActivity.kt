package org.sukrit.tictactoe_kmm

import App
import android.os.Bundle
import android.widget.Toast
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

    private var backPressedTime: Long = 0
    private val backPressThreshold = 2000 // 2 seconds

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (backPressedTime + backPressThreshold > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            // Show a Toast message or a Snackbar
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(dataSourceModule = DataSourceModule(LocalContext.current.applicationContext))
}