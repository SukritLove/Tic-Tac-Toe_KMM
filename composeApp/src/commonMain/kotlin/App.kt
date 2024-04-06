import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import data.database.DSModuleStorage
import org.jetbrains.compose.ui.tooling.preview.Preview
import shared.data.DataSourceModule
import ui.screen.home.HomeScreen

@Composable
@Preview
fun App(dataSourceModule: DataSourceModule) {
    DSModuleStorage.init(dataSourceModule)
    Navigator(HomeScreen())
}