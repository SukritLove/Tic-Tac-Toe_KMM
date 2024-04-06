import androidx.compose.ui.window.ComposeUIViewController

import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle
import shared.data.DataSourceModule
import ui.screen.home.HomeScreen

fun MainViewController() = ComposeUIViewController {
    App(dataSourceModule = DataSourceModule())
}