package ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.compose.AppColor
import shared.common.exitApplication
import ui.model.ButtonType
import ui.model.DialogueState
import ui.theme.Typo

object CustomDialogueBox {

    @Composable
    fun customDecision(
        onDismissRequest: () -> Unit,
        dialogueMessage: String,
        onConfirmClicked: () -> Unit,
        onDeclineClicked: () -> Unit
    ) {
        Dialog(onDismissRequest = onDismissRequest) {
            Box(
                Modifier.size(width = 250.dp, height = 150.dp)
                    .background(AppColor.background, shape = RoundedCornerShape(10.dp))
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(dialogueMessage, style = Typo().titleSmall)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CustomButton.decisionButton(
                            btnText = ButtonType.Yes,
                            onButtonClick = onConfirmClicked
                        )
                        CustomButton.decisionButton(
                            btnText = ButtonType.No,
                            onButtonClick = onDeclineClicked
                        )
                    }

                }
            }
        }
    }

    @Composable
    fun customDecisionPlayScreen(
        onDismissRequest: () -> Unit,
        dialogueMessage: String,
        onConfirmClicked: () -> Unit,
        onDeclineClicked: () -> Unit
    ) {
        Dialog(onDismissRequest = onDismissRequest) {
            Box(
                Modifier.size(width = 400.dp, height = 200.dp)
                    .background(AppColor.background, shape = RoundedCornerShape(10.dp))
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(dialogueMessage, style = Typo().titleSmall)
                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        CustomButton.decisionButton(
                            btnText = ButtonType.Restart,
                            onButtonClick = onConfirmClicked
                        )
                        CustomButton.decisionButton(
                            btnText = ButtonType.Quit,
                            onButtonClick = onDeclineClicked
                        )
                    }
                }
            }
        }
    }

}