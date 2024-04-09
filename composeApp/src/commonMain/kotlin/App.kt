import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.OnBackPressed
import cafe.adriel.voyager.navigator.currentOrThrow
import data.database.DSModuleStorage
import shared.data.DataSourceModule
import ui.screen.home.HomeScreen

@Composable
fun App(dataSourceModule: DataSourceModule) {
    DSModuleStorage.init(dataSourceModule)
    Navigator(HomeScreen())

}