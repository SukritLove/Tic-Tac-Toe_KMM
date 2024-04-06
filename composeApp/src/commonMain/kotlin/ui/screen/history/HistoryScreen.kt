package ui.screen.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import data.domain.HistoryData
import dev.icerock.moko.mvvm.livedata.compose.observeAsState
import ui.screen.home.HomeViewModel

class HistoryScreen : Screen {

    private val viewModel = HistoryViewModel()

    @Composable
    override fun Content() {
        LaunchedEffect(true) {
            viewModel.getHistory()
        }

        val history = viewModel.history.observeAsState()
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (history.value.isNotEmpty()) {
                history.value.forEachIndexed { index, historyData ->
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("${historyData.winner}")
                        Text("${historyData.end_Time}")
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