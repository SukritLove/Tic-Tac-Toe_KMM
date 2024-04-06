package ui.screen.playscreen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.screen.Screen
import com.example.compose.AppColor
import dev.icerock.moko.mvvm.livedata.compose.observeAsState
import ui.component.TicTacToeGrid
import ui.model.DialogueState

class PlayScreen : Screen {
    private val viewModel = PlayViewModel()

    @Composable
    override fun Content() {

        val currentPlayer = viewModel.currentPlayer.observeAsState()
        val dialogueStatus = viewModel.dialogueStatus.observeAsState()
        val dialogueMessage = viewModel.dialogueMessage.observeAsState()
        Column(
            modifier = Modifier.fillMaxSize().background(AppColor.background).padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            TicTacToeGrid(
                boxModifier = Modifier.weight(1f).aspectRatio(1f),
                viewModel
            )
            if (dialogueStatus.value == DialogueState.OnWin || dialogueStatus.value == DialogueState.OnTie) {
                Dialog(onDismissRequest = { viewModel.setDialogue(DialogueState.OnDismiss) }) {
                    Box(
                        Modifier.size(100.dp).background(Color.White)
                    ) { Text("${dialogueMessage.value}") }
                }
            }
            Text("Player ${currentPlayer.value.name}")
        }

    }
}