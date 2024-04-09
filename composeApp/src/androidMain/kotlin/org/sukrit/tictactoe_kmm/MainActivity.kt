package org.sukrit.tictactoe_kmm

import App
import android.app.Application
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.ContentView
import androidx.annotation.NavigationRes
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.lifecycle.getNavigatorScreenLifecycleProvider
import cafe.adriel.voyager.core.registry.screenModule
import cafe.adriel.voyager.navigator.NavigatorContent
import shared.common.exitApplication
import shared.data.DataSourceModule
import ui.screen.home.HomeScreen

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

        println(ContentView())
        if (backPressedTime + backPressThreshold > System.currentTimeMillis()) {
            finish()
            super.onBackPressed()
        } else {
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