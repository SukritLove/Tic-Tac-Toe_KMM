package ui.screen.playscreen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.compose.AppColor
import dev.icerock.moko.mvvm.livedata.compose.observeAsState
import kotlinx.coroutines.launch
import ui.component.TicTacToeGrid
import ui.model.DialogueState

class PlayScreen : Screen {
    private val viewModel = PlayViewModel()

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val currentPlayer = viewModel.currentPlayer.observeAsState()
        val dialogueStatus = viewModel.dialogueStatus.observeAsState()
        val dialogueMessage = viewModel.dialogueMessage.observeAsState()
        val coroutineScope = rememberCoroutineScope()
        Column(
            modifier = Modifier.fillMaxSize().background(AppColor.background).padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TicTacToeGrid(
                boxModifier = Modifier.weight(1f).aspectRatio(1f),
                viewModel
            )
            if (dialogueStatus.value == DialogueState.OnShow) {
                Dialog(onDismissRequest = {
                    coroutineScope.launch {
                        viewModel.setDialogue(
                            DialogueState.OnDismiss
                        )
                    }
                }) {
                    Box(
                        Modifier.aspectRatio(1f).background(Color.White)
                    ) {
                        Column(
                            Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("${dialogueMessage.value}")
                            Button(onClick = {
                                coroutineScope.launch {
                                    viewModel.setDialogue(
                                        DialogueState.OnDismiss
                                    )
                                }
                            }) {
                                Text("Restart")
                            }
                            Button(onClick = { viewModel.onBackOrExit(navigator) }
                            ) {
                                Text("Exit")
                            }
                        }
                    }
                }
            }
            Text("Game Mode -> ${viewModel.gameMode}")
            Text("Player ${currentPlayer.value.name}")
        }

    }
}