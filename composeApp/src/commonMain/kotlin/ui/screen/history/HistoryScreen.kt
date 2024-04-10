package ui.screen.history

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.example.compose.AppColor
import dev.icerock.moko.mvvm.livedata.compose.observeAsState
import shared.common.exitApplication
import ui.component.CustomButton
import ui.component.CustomDialogueBox
import ui.component.CustomText
import ui.model.ButtonType
import ui.model.DialogueState
import ui.model.GameMode
import ui.theme.Typo

class HistoryScreen(navigator: Navigator) : Screen {
    private val viewModel = HistoryViewModel()
    private val nav = navigator

    @Composable
    override fun Content() {
        val history = viewModel.showHistory.observeAsState()
        val filter = viewModel.filterType.observeAsState()
        val isLoading = viewModel.isLoading.observeAsState().value
        val dialogueState = viewModel.dialogueState.observeAsState().value

        Column(
            Modifier.fillMaxSize().background(AppColor.background).padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            CustomText.historyTopBar()
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Spacer(Modifier.weight(1f))
                CustomButton.iconButton(
                    modifier = Modifier.alpha(if (filter.value == GameMode.AI) 1f else 0f),
                    btnText = ButtonType.Previous,
                    onButtonClick = { viewModel.onNextAndPreviousClicked(ButtonType.Previous) })
                Spacer(Modifier.padding(20.dp))
                Text(filter.value.name, style = Typo().titleMedium)
                Spacer(Modifier.padding(20.dp))
                CustomButton.iconButton(
                    modifier = Modifier.alpha(if (filter.value == GameMode.PvP) 1f else 0f),
                    btnText = ButtonType.Next,
                    onButtonClick = { viewModel.onNextAndPreviousClicked(ButtonType.Next) })
                Spacer(Modifier.weight(1f))
            }
            Box(
                Modifier.size(width = 450.dp, height = 450.dp).padding(20.dp)
                    .background(Color.White, shape = RoundedCornerShape(10.dp))
                    .border(3.dp, Color.Black, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.TopCenter
            ) {
                Column() {
                    Row(
                        Modifier.fillMaxWidth().background(
                            color = AppColor.yellow_wheat,
                            shape = RoundedCornerShape(10.dp)
                        ).border(3.dp, Color.Black, RoundedCornerShape(10.dp)).padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Winner",
                            style = Typo().bodyMedium.copy(AppColor.sienna),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1.5f)
                        )
                        Text(
                            text = "Grid",
                            style = Typo().bodyMedium.copy(AppColor.sienna),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "End Time",
                            style = Typo().bodyMedium.copy(AppColor.sienna),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (isLoading) {
                        if (history.value.isNotEmpty()) {
                            LazyColumn(
                                Modifier.fillMaxWidth().padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ) {
                                items(history.value) { historyData ->
                                    Row(
                                        Modifier.fillMaxWidth().padding(bottom = 5.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = historyData.winner,
                                            style = Typo().bodyMedium,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.weight(1.5f)
                                        )
                                        Text(
                                            text = "${historyData.gridSize} x ${historyData.gridSize}",
                                            style = Typo().bodyMedium,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Text(
                                            text = historyData.end_Time,
                                            style = Typo().bodyMedium,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                }

                            }
                        } else {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "-No Data-",
                                    style = Typo().titleMedium,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "-Loading-",
                                style = Typo().titleMedium,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            CustomButton.styledButton(
                modifier = Modifier.width(250.dp),
                btnText = "Clear History",
                onButtonClick = { viewModel.setDialogue(DialogueState.OnShow) })

            CustomButton.styledButton(
                btnText = "Back",
                onButtonClick = { viewModel.onBackClicked(nav) })
        }
        if (dialogueState == DialogueState.OnShow) {
            CustomDialogueBox.customDecision(
                onDismissRequest = {
                    viewModel.setDialogue(DialogueState.OnDismiss)
                },
                dialogueMessage = "Wipe your history clean?",
                onConfirmClicked = { viewModel.clearHistory() },
                onDeclineClicked = { viewModel.setDialogue(DialogueState.OnDismiss) })
        }

    }

}
