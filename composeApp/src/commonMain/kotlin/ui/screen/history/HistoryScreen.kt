package ui.screen.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import dev.icerock.moko.mvvm.livedata.compose.observeAsState

class HistoryScreen : Screen {

    private val viewModel = HistoryViewModel()

    @Composable
    override fun Content() {
        LaunchedEffect(true) {
            viewModel.getHistory()
        }

        val history = viewModel.history.observeAsState()
        Column(
            Modifier.fillMaxSize().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (history.value.isNotEmpty()) {
                LazyColumn(
                    Modifier.fillMaxWidth().height(450.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    items(history.value) { historyData ->
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("${historyData.gameMode}")
                            Text("${historyData.winner}")
                            Text("${historyData.gridSize}")
                            Text("${historyData.end_Time}")
                        }
                    }

                }
            } else {
                Text("No Data")
            }
            Button(onClick = { viewModel.clearHistory() }) {
                Text("Clear")
            }


        }

    }
}