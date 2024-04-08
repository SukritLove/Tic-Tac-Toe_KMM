package gamePlayLogic

import kotlinx.coroutines.flow.MutableStateFlow
import ui.model.GameState
import ui.model.Player

fun checkWinner(grid: List<MutableList<MutableStateFlow<Player?>>>): GameState {
    val g = grid.size
    val winCheck = mutableListOf(
        mutableListOf(true, true),  // diagonal check
        MutableList(g) { true },    // rows check
        MutableList(g) { true }     // columns check
    )
    for (x in 0 until g) {
        for (y in 0 until g) {
            //Row
            if (grid[x][y].value != grid[x][0].value ||
                grid[x][y].value == null
            ) {
                winCheck[1][x] = false
            }
            //Column
            if (grid[y][x].value != grid[0][x].value ||
                grid[y][x].value == null
            ) {
                winCheck[2][x] = false
            }
            //Diagonal
            if (grid[x][x].value != grid[0][0].value ||
                grid[x][x].value == null
            ) {
                winCheck[0][0] = false
            }
            //Reverse Diagonal
            if (grid[x][g - x - 1].value != grid[0][g - 1].value ||
                grid[x][g - x - 1].value == null
            )
                winCheck[0][1] = false
        }

    }
    return if (winCheck.any {
            it.contains(true)
        }) GameState.OnWin else if (isBoardFull(grid) && winCheck.none { it.contains(true) }) GameState.OnTie else GameState.NONE
}

private fun isBoardFull(grid: List<MutableList<MutableStateFlow<Player?>>>): Boolean {
    return grid.all { row -> row.all { cell -> cell.value != null } }
}
