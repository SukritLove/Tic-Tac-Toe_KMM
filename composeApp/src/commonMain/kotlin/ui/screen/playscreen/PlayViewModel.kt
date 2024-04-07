package ui.screen.playscreen

import data.dataRepository.DataRepo
import data.database.DatabaseManagement
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ui.model.DialogueState
import ui.model.GameMode
import ui.model.Player


class PlayViewModel : ViewModel() {

    private val dataManagement = DatabaseManagement()
    val gridSize = DataRepo.data.gridSize
    val gameMode = DataRepo.data.gameMode

    private val _grid: MutableLiveData<List<MutableList<MutableStateFlow<Player?>>>> =
        MutableLiveData(List(gridSize) {
            MutableList(gridSize) {
                MutableStateFlow(null)
            }
        })
    val grid: MutableLiveData<List<MutableList<MutableStateFlow<Player?>>>> = _grid

    private val _currentPlayer: MutableLiveData<Player> = MutableLiveData(Player.X)
    val currentPlayer: LiveData<Player> = _currentPlayer

    private val _dialogueStatus: MutableLiveData<DialogueState> =
        MutableLiveData(DialogueState.OnDefault)
    val dialogueStatus: MutableLiveData<DialogueState> = _dialogueStatus

    private val _dialogueMessage: MutableLiveData<String> =
        MutableLiveData("")
    val dialogueMessage: MutableLiveData<String> = _dialogueMessage

    private val lastWinner = MutableStateFlow<Player?>(null)

    private var i = 1
    fun playTurn(row: Int? = 0, col: Int? = 0) {
        val currentGrid = _grid.value.map { it.toMutableList() }
        if (currentGrid[row!!][col!!].value == null && _dialogueStatus.value == DialogueState.OnDefault) {
            currentGrid[row][col].value = _currentPlayer.value
            val status = checkWinner(currentGrid)
            when (gameMode) {
                GameMode.Player -> {
                    if (status == DialogueState.OnWin || status == DialogueState.OnTie) {
                        setDialogue(state = status, whoTurn = _currentPlayer.value)
                    } else {
                        _currentPlayer.value =
                            when (_currentPlayer.value) {
                                Player.X -> Player.O
                                Player.O -> Player.X
                                else -> _currentPlayer.value
                            }
                        _grid.value = currentGrid
                    }

                }

                GameMode.AI -> {
                    if (status == DialogueState.OnWin || status == DialogueState.OnTie) {
                        setDialogue(state = status, whoTurn = _currentPlayer.value)
                        //startNewGame()
                    } else {

                        _currentPlayer.value =
                            when (_currentPlayer.value) {
                                Player.X -> Player.O
                                Player.O -> Player.X
                                else -> Player.X
                            }
                        println("who play ->  ${_currentPlayer.value}")
                        if (_currentPlayer.value == Player.O) { // Assuming the player X just played, and now it's AI's turn

                            val aiMove = findBestMove(currentGrid)
                            println(aiMove)
                            if (aiMove != Pair(-1, -1)) {
                                // Apply AI's best move
                                println("calculating")
                                playTurn(aiMove.first, aiMove.second)
                                // Note: Ensure playTurn can handle AI moves without causing recursion or incorrect state transitions
                            }

                            _grid.value = currentGrid
                        } else {
                            _grid.value = currentGrid
                        }

                    }

                }
            }
        }
    }


    private fun minimax(
        grid: List<MutableList<MutableStateFlow<Player?>>>,
        depth: Int,
        isMaximizing: Boolean
    ): Int {
        i += 1
        val currentStatus = checkWinner(grid)
        when (currentStatus) {
            DialogueState.OnWin -> return if (isMaximizing) -10 else 10
            DialogueState.OnTie -> return 0
            else -> { /* Continue with the minimax algorithm */
            }
        }

        if (isMaximizing) {
            var bestScore = Int.MIN_VALUE
            for (row in 0 until gridSize) {
                for (col in 0 until gridSize) {
                    if (grid[row][col].value == null) {
                        grid[row][col].value = Player.O
                        val score = minimax(grid, depth + 1, false)
                        grid[row][col].value = null
                        bestScore = maxOf(score, bestScore)
                    }
                }
            }
            return bestScore
        } else {
            var bestScore = Int.MAX_VALUE
            for (row in 0 until gridSize) {
                for (col in 0 until gridSize) {
                    if (grid[row][col].value == null) {
                        grid[row][col].value = Player.X
                        val score = minimax(grid, depth + 1, true)
                        grid[row][col].value = null
                        bestScore = minOf(score, bestScore)
                    }
                }
            }
            println(i)
            return bestScore
        }
    }

    private fun findBestMove(grid: List<MutableList<MutableStateFlow<Player?>>>): Pair<Int, Int> {
        i = 1
        var bestScore = Int.MIN_VALUE
        var move = Pair(-1, -1)
        for (row in 0 until gridSize) {
            for (col in 0 until gridSize) {
                if (grid[row][col].value == null) {
                    grid[row][col].value = Player.O // AI makes its move
                    val score = minimax(grid, 0, false)
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

    fun setDialogue(state: DialogueState, whoTurn: Player = Player.NULL) {
        when (state) {
            DialogueState.OnDefault, DialogueState.OnDismiss -> {
                startNewGame()
            }

            DialogueState.OnWin, DialogueState.OnTie -> {
                _dialogueStatus.value = state
                when (state) {
                    DialogueState.OnWin -> {
                        dataManagement.addHistory(winner = "Player $whoTurn Win", end_time = "11")
                        lastWinner.value = whoTurn
                        _dialogueMessage.value = "Player $whoTurn Win"
                    }

                    DialogueState.OnTie -> {
                        dataManagement.addHistory(winner = "TIE", end_time = "11")
                        lastWinner.value = whoTurn
                        _dialogueMessage.value = "This Game are tie"
                    }

                    else -> {}
                }
            }
        }
    }

    private fun checkWinner(grid: List<MutableList<MutableStateFlow<Player?>>>): DialogueState {
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
            }) DialogueState.OnWin else if (isBoardFull(grid) && winCheck.none { it.contains(true) }) DialogueState.OnTie else DialogueState.OnDefault
    }

    private fun isBoardFull(grid: List<MutableList<MutableStateFlow<Player?>>>): Boolean {
        return grid.all { row -> row.all { cell -> cell.value != null } }
    }

    private fun startNewGame() {
        _grid.value = List(gridSize) {
            MutableList(gridSize) {
                MutableStateFlow<Player?>(null)
            }
        }
        _currentPlayer.value = lastWinner.value ?: Player.X

        _dialogueStatus.value = DialogueState.OnDefault
        if (gameMode == GameMode.AI && _currentPlayer.value == Player.O) {
            playTurn()
        }
        lastWinner.value = null
    }


}
