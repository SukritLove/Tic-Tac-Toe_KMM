import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.screen.home.HomeScreen

@Composable
@Preview
fun App(
) {
    Navigator(
        HomeScreen(
        )
    )
}