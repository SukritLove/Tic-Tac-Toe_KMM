package ui.screen.play


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.example.compose.AppColor
import dev.icerock.moko.mvvm.livedata.compose.observeAsState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import tic_tac_toe_kmm.composeapp.generated.resources.Res
import ui.component.CustomButton
import ui.component.CustomDialogueBox
import ui.component.CustomText
import ui.component.TicTacToeGrid
import ui.model.GameMode
import ui.model.PlayDialogueState
import ui.model.Player
import ui.theme.Typo

class PlayScreen(navigator: Navigator) : Screen {
    private val viewModel = PlayViewModel()
    private val nav = navigator

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {

        val currentPlayer = viewModel.currentPlayer.observeAsState()
        val dialogueStatus = viewModel.dialogueStatus.observeAsState()
        val dialogueMessage = viewModel.dialogueMessage.observeAsState()
        val coroutineScope = rememberCoroutineScope()
        Column(
            modifier = Modifier.fillMaxSize().background(AppColor.background).padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            CustomText.logoText(
                text = stringResource(Res.string.app_name),
                textStyle = Typo().titleLarge
            )
            Column(
                modifier = Modifier.fillMaxSize().background(AppColor.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                Text(
                    viewModel.gameMode.name, style = Typo().titleMedium
                )
                Box(contentAlignment = Alignment.Center) {
                    TicTacToeGrid(
                        boxModifier = Modifier.size(350.dp),
                        viewModel
                    )
                }

                if (dialogueStatus.value == PlayDialogueState.onGameEnd) {
                    CustomDialogueBox.customDecisionPlayScreen(
                        onDismissRequest = {
                        },
                        dialogueMessage = dialogueMessage.value,
                        onConfirmClicked = {
                            coroutineScope.launch {
                                viewModel.setDialogue(
                                    PlayDialogueState.onRestart
                                )
                                viewModel.startNewGame()
                            }
                        },
                        onDeclineClicked = {
                            viewModel.onBackOrExit(nav)
                        })
                }
                if (dialogueStatus.value == PlayDialogueState.onQuitShow) {
                    CustomDialogueBox.customDecision(onDismissRequest = {
                        viewModel.setDialogue(
                            PlayDialogueState.onDismiss
                        )
                    },
                        dialogueMessage = "Leave the game?",
                        onConfirmClicked = {
                            viewModel.onBackOrExit(nav)
                        },
                        onDeclineClicked = {
                            viewModel.setDialogue(PlayDialogueState.onDismiss)
                        })
                }


                Text(
                    if (viewModel.gameMode == GameMode.AI &&
                        currentPlayer.value == Player.O
                    ) {
                        "Computer"
                    } else {
                        "Player ${currentPlayer.value.name}"
                    }, style = Typo().titleMedium
                )
                CustomButton.styledButton(
                    btnText = "Quit",
                    onButtonClick = { viewModel.onQuitClicked(PlayDialogueState.onQuitShow) })
            }
        }

    }
}