package ui.screen.playscreen

import GameLogic.AI
import GameLogic.checkWinner
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
import kotlin.random.Random


class PlayViewModel : ViewModel() {

    private val dataManagement = DatabaseManagement()
    val gridSize = DataRepo.data.gridSize
    val gameMode = DataRepo.data.gameMode
    val AI = AI(gridSize)

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
    private val lastGame = MutableStateFlow<DialogueState?>(null)


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
                    } else {

                        when (_currentPlayer.value) {
                            Player.X -> _currentPlayer.value = Player.O
                            Player.O -> _currentPlayer.value = Player.X
                            else -> _currentPlayer.value = Player.X
                        }

                        println("who play ->  ${_currentPlayer.value}")
                        println("last game ->  ${lastGame.value}")
                        println("last winner ->  ${lastWinner.value}")


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


    private fun findBestMove(grid: List<MutableList<MutableStateFlow<Player?>>>): Pair<Int, Int> {

        var bestScore = Int.MIN_VALUE
        var move = Pair(-1, -1)
        for (row in 0 until gridSize) {
            for (col in 0 until gridSize) {
                if (grid[row][col].value == null) {
                    grid[row][col].value = Player.O // AI makes its move
                    val score = AI.minimax(grid, 0, Int.MIN_VALUE, Int.MAX_VALUE, false)
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


    private fun startNewGame() {
        _grid.value = List(gridSize) {
            MutableList(gridSize) {
                MutableStateFlow<Player?>(null)
            }
        }
        lastGame.value = _dialogueStatus.value
        _currentPlayer.value = lastWinner.value ?: Player.X
        _dialogueStatus.value = DialogueState.OnDefault
        println(lastGame.value)

        if (gameMode == GameMode.AI && _currentPlayer.value == Player.O) {
            println("logic2")
            playTurn()
        }
        println("outoflogic")
    }


}
