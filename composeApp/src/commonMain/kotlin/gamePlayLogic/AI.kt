package gamePlayLogic

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import ui.model.GameState
import ui.model.Player


class Computer(grid: Int) : ViewModel() {
    private val gridSize = grid

    fun findBestMove(grid: List<MutableList<MutableStateFlow<Player?>>>): Pair<Int, Int> {

        var bestScore = Int.MIN_VALUE
        var move = Pair(-1, -1)
        for (row in 0 until gridSize) {
            for (col in 0 until gridSize) {
                if (grid[row][col].value == null) {
                    grid[row][col].value = Player.O // AI makes its move
                    val score = minimax(grid, 0, Int.MIN_VALUE, Int.MAX_VALUE, false)
                    grid[row][col].value = null
                    if (score > bestScore) {
                        bestScore = score
                        move = Pair(row, col)
                    }
                }
            }
        }
        return move
    }

    private fun minimax(
        grid: List<MutableList<MutableStateFlow<Player?>>>,
        depth: Int,
        alpha: Int,
        beta: Int,
        isMaximizing: Boolean,
    ): Int {
        val currentStatus = checkWinner(grid)
        when (currentStatus) {
            GameState.OnWin -> return if (isMaximizing) -10 + depth else 10 - depth
            GameState.OnTie -> return 0
            else -> {}
        }

        if (depth == 0) {
            return evaluateBoard(grid)
        }

        if (isMaximizing) {
            var bestScore = Int.MIN_VALUE
            var a = alpha
            for (row in 0 until gridSize) {
                for (col in 0 until gridSize) {
                    if (grid[row][col].value == null) {
                        grid[row][col].value = Player.O
                        val score = minimax(grid, depth - 1, a, beta, false)
                        grid[row][col].value = null
                        bestScore = maxOf(score, bestScore)
                        a = maxOf(a, score)
                        if (beta <= a) {
                            break
                        }
                    }
                }
            }
            return bestScore
        } else {
            var bestScore = Int.MAX_VALUE
            var b = beta
            for (row in 0 until gridSize) {
                for (col in 0 until gridSize) {
                    if (grid[row][col].value == null) {
                        grid[row][col].value = Player.X
                        val score = minimax(grid, depth - 1, alpha, b, true)
                        grid[row][col].value = null
                        bestScore = minOf(score, bestScore)
                        b = minOf(b, score)
                        if (b <= alpha) {
                            break
                        }
                    }
                }
            }
            return bestScore
        }
    }

    private fun evaluateBoard(grid: List<MutableList<MutableStateFlow<Player?>>>): Int {
        var score = 0

        if (grid[1][1].value == Player.O) score += 3
        else if (grid[1][1].value == Player.X) score -= 3

        val corners = listOf(grid[0][0], grid[0][2], grid[2][0], grid[2][2])
        for (corner in corners) {
            if (corner.value == Player.O) score += 2
            else if (corner.value == Player.X) score -= 2
        }
        val edges = listOf(grid[0][1], grid[1][0], grid[1][2], grid[2][1])
        for (edge in edges) {
            if (edge.value == Player.O) score += 1
            else if (edge.value == Player.X) score -= 1
        }
        score += checkForImminentWinOrBlock(grid, Player.O) * 5
        score -= checkForImminentWinOrBlock(grid, Player.X) * 5

        return score
    }

    private fun checkForImminentWinOrBlock(
        grid: List<MutableList<MutableStateFlow<Player?>>>,
        player: Player
    ): Int {
        var count = 0

        // Check rows and columns
        for (i in 0 until gridSize) {
            // Check row
            if (grid[i].count { it.value == player } == 2 && grid[i].any { it.value == null }) {
                count++
            }

            // Check column
            if (grid.map { it[i] }.count { it.value == player } == 2 && grid.map { it[i] }
                    .any { it.value == null }) {
                count++
            }
        }

        // Check diagonals
        val diagonal1 = listOf(grid[0][0], grid[1][1], grid[2][2])
        val diagonal2 = listOf(grid[0][2], grid[1][1], grid[2][0])

        if (diagonal1.count { it.value == player } == 2 && diagonal1.any { it.value == null }) {
            count++
        }

        if (diagonal2.count { it.value == player } == 2 && diagonal2.any { it.value == null }) {
            count++
        }

        return count
    }
}