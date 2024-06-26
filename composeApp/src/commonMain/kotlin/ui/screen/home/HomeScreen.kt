package ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.compose.AppColor
import dev.icerock.moko.mvvm.livedata.compose.observeAsState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import shared.common.exitApplication
import tic_tac_toe_kmm.composeapp.generated.resources.Res
import ui.component.CustomButton
import ui.component.CustomDialogueBox
import ui.component.CustomText
import ui.component.GameModeSwitch
import ui.model.ButtonType
import ui.model.DialogueState
import ui.theme.Typo

@OptIn(ExperimentalResourceApi::class)
class HomeScreen() : Screen {
    private val viewModel = HomeViewModel()


    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow

        val typo = Typo()

        val gridSize by viewModel.gridSize.collectAsState()

        val checked = viewModel.isEnable.observeAsState().value

        val dialogueState = viewModel.dialogueState.observeAsState().value



        Column(
            modifier = Modifier.fillMaxSize().background(AppColor.background).padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            CustomText.logoText(
                text = stringResource(Res.string.app_name),
                textStyle = typo.titleLarge
            )
            Column(
                Modifier.fillMaxWidth().padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(35.dp)
            )
            {
                Text(text = stringResource(Res.string.select_board_size), style = Typo().bodyLarge)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(Modifier.weight(1f))
                    CustomButton.iconButton(
                        btnText = ButtonType.Minus,
                        onButtonClick = { viewModel.decrementCount() },
                        isEnable = gridSize != 3 && checked
                    )
                    Spacer(Modifier.padding(20.dp))
                    Text("$gridSize x $gridSize", style = Typo().bodyLarge)
                    Spacer(Modifier.padding(20.dp))
                    CustomButton.iconButton(
                        btnText = ButtonType.Plus,
                        onButtonClick = { viewModel.incrementCount() },
                        isEnable = gridSize != 8 && checked
                    )
                    Spacer(Modifier.weight(1f))
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GameModeSwitch(
                        checked = checked,
                        onCheckChange = { viewModel.onSelectGameMode() })
                }
            }

            Column(
                Modifier.fillMaxWidth().padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(35.dp)
            ) {
                CustomButton.styledButton(
                    btnText = stringResource(Res.string.play),
                    onButtonClick = { viewModel.onClickPlay(nav) })
                CustomButton.styledButton(
                    btnText = stringResource(Res.string.history),
                    onButtonClick = { viewModel.onClickHistory(nav) })
                CustomButton.styledButton(
                    btnText = stringResource(Res.string.exit),
                    onButtonClick = { viewModel.onExit(DialogueState.OnShow) })
            }
            if (dialogueState == DialogueState.OnShow) {
                CustomDialogueBox.customDecision(
                    onDismissRequest = {
                        viewModel.onExit(DialogueState.OnDismiss)
                    },
                    dialogueMessage = stringResource(Res.string.exit_confirmation),
                    onConfirmClicked = { exitApplication() },
                    onDeclineClicked = { viewModel.onExit(DialogueState.OnDismiss) })
            }
        }
    }


}




