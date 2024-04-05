package ui.screen.playscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.ui.graphics.Color

class PlayScreen : Screen {
    private val viewModel = PlayViewModel()

    @Composable
    override fun Content() {

        Column(
            Modifier.fillMaxSize().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column {
                viewModel.createGrid().first.forEach { row ->
                    Row {
                        row.forEach { cell ->
                            Box(
                                modifier = Modifier
                                    .size(70.dp)
                                    .border(
                                        BorderStroke(
                                            1.dp,
                                            Color.Black
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = cell)
                            }
                        }
                    }
                }
            }
        }
    }
}