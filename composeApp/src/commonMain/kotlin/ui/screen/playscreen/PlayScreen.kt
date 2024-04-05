package ui.screen.playscreen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.example.compose.AppColor
import ui.component.TicTacToeGrid

class PlayScreen : Screen {
    private val viewModel = PlayViewModel()

    @Composable
    override fun Content() {

        Column(
            modifier = Modifier.fillMaxSize().background(AppColor.background).padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            TicTacToeGrid(
                boxModifier = Modifier.weight(1f).aspectRatio(1f),
                viewModel
            )
        }

    }
}