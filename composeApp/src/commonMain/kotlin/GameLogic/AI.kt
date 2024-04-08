package GameLogic

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import ui.model.DialogueState
import ui.model.Player
import kotlin.random.Random

private var i = 1

class AI(grid: Int) : ViewModel() {
    val gridSize = grid

    fun minimax(
        grid: List<MutableList<MutableStateFlow<Player?>>>,
        depth: Int,
        alpha: Int,
        beta: Int,
        isMaximizing: Boolean,
    ): Int {
        i += 1
        println(i)
        val currentStatus = checkWinner(grid)
        when (currentStatus) {
            DialogueState.OnWin -> return if (isMaximizing) -10 + depth else 10 - depth
            DialogueState.OnTie -> return 0
            else -> { /* Continue with the minimax algorithm */
            }
        }
        if (i <= 50000) {
            if (isMaximizing) {
                var bestScore = Int.MIN_VALUE
                var a = alpha
                loop@ for (row in 0 until gridSize) {
                    for (col in 0 until gridSize) {
                        if (grid[row][col].value == null) {
                            grid[row][col].value = Player.O
                            val score = minimax(grid, depth-1, a, beta, false)
                            grid[row][col].value = null
                            bestScore = maxOf(score, bestScore)
                            a = maxOf(a, score)
                            if (beta <= a) {
                                break@loop
                            }
                        }
                    }
                }
                return bestScore
            } else {
                var bestScore = Int.MAX_VALUE
                var b = beta
                loop@ for (row in 0 until gridSize) {
                    for (col in 0 until gridSize) {
                        if (grid[row][col].value == null) {
                            grid[row][col].value = Player.X
                            val score = minimax(grid, depth-1, alpha, b, true)
                            grid[row][col].value = null
                            bestScore = minOf(score, bestScore)
                            b = minOf(b, score)
                            if (b <= alpha) {
                                break@loop
                            }
                        }
                    }
                }
                println(i)
                return bestScore
            }
        } else {
            return Random(Int.MAX_VALUE).nextInt()
        }
    }
}