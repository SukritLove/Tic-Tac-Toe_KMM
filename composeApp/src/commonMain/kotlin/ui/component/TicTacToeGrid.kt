package ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.icerock.moko.mvvm.livedata.compose.observeAsState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import tic_tac_toe_kmm.composeapp.generated.resources.Res
import ui.model.Player
import ui.screen.playscreen.PlayViewModel


@Composable
fun TicTacToeGrid(boxModifier: Modifier, viewModel: PlayViewModel) {

    val grid = viewModel.gridSize

    BoxWithConstraints(
        modifier = boxModifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
                .border(5.dp, Color.Black, RoundedCornerShape(5.dp))
        ) {

            val thirdWidth = size.width / grid
            val thirdHeight = size.height / grid

            // Draw vertical lines
            for (i in 1 until grid) {
                val x = thirdWidth * i
                drawLine(
                    color = Color.Black,
                    start = Offset(x, 0f),
                    end = Offset(x, size.height),
                    strokeWidth = 5.dp.toPx(),
                    //cap = StrokeCap.Round
                )
            }

            // Draw horizontal lines
            for (i in 1 until grid) {
                val y = thirdHeight * i
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = 5.dp.toPx(),
                    //cap = StrokeCap.Round
                )
            }
        }

        TicTacToeCellGroup(viewModel)

    }
}


@Composable
fun TicTacToeCellGroup(viewModel: PlayViewModel) {
    val gridState = viewModel.grid.observeAsState()
    val onWorking = viewModel.onWorking.observeAsState()

    Column {
        gridState.value.forEachIndexed { rowIndex, row ->
            Row {
                row.forEachIndexed { colIndex, cell ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .then(
                                if (onWorking.value) Modifier.clickable {
                                    viewModel.playTurn(
                                        rowIndex,
                                        colIndex
                                    )
                                } else {
                                    Modifier
                                }

                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        TicTacToeCell(cell.collectAsState().value)
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun TicTacToeCell(cell: Player?) {
    when (cell) {
        Player.X -> Image(
            painter = painterResource(Res.drawable.iconX),
            contentDescription = "X",
            modifier = Modifier.aspectRatio(1f / 2f)
        )

        Player.O -> Image(
            painter = painterResource(Res.drawable.iconO),
            contentDescription = "O",
            modifier = Modifier.aspectRatio(1f / 2f)
        )

        else -> {}
    }
}
