package ui.screen.playscreen

import gamePlayLogic.Computer
import gamePlayLogic.checkWinner
import data.dataRepository.DataRepo
import data.database.DatabaseManagement
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import ui.model.DialogueState
import ui.model.GameMode
import ui.model.GameState
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

    private val gameState: MutableStateFlow<GameState> =
        MutableStateFlow(GameState.NONE)

    private val _dialogueStatus: MutableLiveData<DialogueState> =
        MutableLiveData(DialogueState.OnDismiss)
    val dialogueStatus: MutableLiveData<DialogueState> = _dialogueStatus

    private val _dialogueMessage: MutableLiveData<String> =
        MutableLiveData("")
    val dialogueMessage: MutableLiveData<String> = _dialogueMessage

    private val lastWinner = MutableStateFlow<Player?>(null)
    private val lastGame = MutableStateFlow<GameState?>(null)

    private val _onWorking: MutableLiveData<Boolean> = MutableLiveData(true)
    val onWorking: LiveData<Boolean> = _onWorking
    fun playTurn(row: Int? = 0, col: Int? = 0) {
        _onWorking.value = false
        if (gameState.value == GameState.NONE && isMoveValid(row, col)) {
            updateGameGrid(row, col)
            val status = checkWinner(_grid.value)
            if (status == GameState.OnWin || status == GameState.OnTie) {
                winningHandler(state = status, whoTurn = _currentPlayer.value)
            } else {

                switchCurrentPlayer()
                if (gameMode == GameMode.AI && _currentPlayer.value == Player.O) {
                    val aiMove = Computer(gridSize).findBestMove(_grid.value)
                    playTurn(aiMove.first, aiMove.second)
                }

            }
        }
        _onWorking.value = true
    }

    private fun switchCurrentPlayer() {
        _currentPlayer.value = when (_currentPlayer.value) {
            Player.X -> Player.O
            Player.O -> Player.X
            else -> Player.NULL
        }
    }

    private fun updateGameGrid(row: Int? = 0, col: Int? = 0) {
        val updatedGrid = _grid.value.map { it.toMutableList() }

        if (row != null && col != null && updatedGrid[row][col].value == null) {
            updatedGrid[row][col].value = _currentPlayer.value
            _grid.value = updatedGrid
        }
    }

    private fun isMoveValid(row: Int?, col: Int?): Boolean {
        return _grid.value[row!!][col!!].value == null
    }


    fun setDialogue(status: DialogueState) {
        _onWorking.value = false
        _dialogueStatus.value = status
        if (status == DialogueState.OnDismiss) {
            startNewGame()
        }
        _onWorking.value = status == DialogueState.OnDismiss
    }

    private fun winningHandler(state: GameState, whoTurn: Player = Player.NULL) {
        when (state) {
            GameState.NONE -> {}

            GameState.OnWin -> {
                setDialogue(DialogueState.OnShow)
                dataManagement.addHistory(winner = "Player $whoTurn Win", end_time = "11")
                lastGame.value = GameState.OnWin
                lastWinner.value = whoTurn
                _dialogueMessage.value = "Player $whoTurn Win"
            }

            GameState.OnTie -> {
                setDialogue(DialogueState.OnShow)
                dataManagement.addHistory(winner = "TIE", end_time = "11")
                lastGame.value = GameState.OnTie
                lastWinner.value = whoTurn
                _dialogueMessage.value = "This Game are tie"

            }
        }
    }


    private fun startNewGame() {

        when (gameMode) {
            GameMode.Player -> {
                _grid.value = List(gridSize) {
                    MutableList(gridSize) { MutableStateFlow<Player?>(null) }
                }
                gameState.value = GameState.NONE
                _currentPlayer.value = if (lastGame.value == GameState.OnTie) {
                    if (lastWinner.value == Player.X) Player.O else Player.X
                } else {
                    lastWinner.value ?: Player.X
                }
                println("Last game: ${lastGame.value}, Starting new game with player: ${currentPlayer.value}")
            }

            GameMode.AI -> {
                _grid.value = List(gridSize) {
                    MutableList(gridSize) {
                        MutableStateFlow<Player?>(null)
                    }
                }
                _currentPlayer.value = lastWinner.value ?: Player.X
                gameState.value = GameState.NONE
                println(lastGame.value)
                if (gameMode == GameMode.AI && _currentPlayer.value == Player.O) {
                    playTurn()
                }
            }
        }

    }


}
