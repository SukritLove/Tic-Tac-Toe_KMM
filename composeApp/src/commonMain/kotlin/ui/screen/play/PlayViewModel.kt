package ui.screen.play

import cafe.adriel.voyager.navigator.Navigator
import gamePlayLogic.Computer
import gamePlayLogic.checkWinner
import data.dataRepository.DataRepo
import data.database.DatabaseManagement
import data.getCurrentTime
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ui.model.GameMode
import ui.model.GameState
import ui.model.PlayDialogueState
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

    private val _dialogueStatus: MutableLiveData<PlayDialogueState> =
        MutableLiveData(PlayDialogueState.onDismiss)
    val dialogueStatus: MutableLiveData<PlayDialogueState> = _dialogueStatus

    private val _dialogueMessage: MutableLiveData<String> =
        MutableLiveData("")
    val dialogueMessage: MutableLiveData<String> = _dialogueMessage

    private val lastWinner = MutableStateFlow<Player?>(null)
    private val lastGame = MutableStateFlow<GameState?>(null)

    private val _onWorking: MutableLiveData<Boolean> = MutableLiveData(true)
    val onWorking: LiveData<Boolean> = _onWorking
    fun playTurn(row: Int? = 0, col: Int? = 0) {
        _onWorking.value = false
        viewModelScope.launch {
            if (gameState.value == GameState.NONE && isMoveValid(row, col)) {
                updateGameGrid(row, col)
                val status = checkWinner(_grid.value)
                if (status == GameState.OnWin || status == GameState.OnTie) {
                    winningHandler(state = status, whoTurn = _currentPlayer.value)
                } else {

                    switchCurrentPlayer()
                    if (gameMode == GameMode.AI && _currentPlayer.value == Player.O) {
                        _onWorking.value = false
                        val aiMove = Computer(gridSize).findBestMove(_grid.value)
                        delay(500)
                        playTurn(aiMove.first, aiMove.second)
                    }
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

    fun setDialogue(status: PlayDialogueState) {
        _onWorking.value = false
        _dialogueStatus.value = status
        if (status == PlayDialogueState.onRestart) {
            startNewGame()
        } else if (status == PlayDialogueState.onDismiss) {
            _onWorking.value = true
        }
    }

    private fun winningHandler(state: GameState, whoTurn: Player = Player.NULL) {
        when (state) {
            GameState.NONE -> {}

            GameState.OnWin -> {
                setDialogue(PlayDialogueState.onGameEnd)
                dataManagement.addHistory(
                    gameMode = getGameModeName(),
                    winner =
                    if (gameMode == GameMode.AI)
                        if (whoTurn == Player.O) {
                            "Computer"
                        } else {
                            "Player"
                        }
                    else {
                        "Player $whoTurn"
                    } + " Win",
                    gridSize = gridSize.toLong(),
                    end_time = getCurrentTime()
                )
                lastGame.value = GameState.OnWin
                lastWinner.value = whoTurn
                _dialogueMessage.value = "Player $whoTurn takes the win!"
            }

            GameState.OnTie -> {
                setDialogue(PlayDialogueState.onGameEnd)
                dataManagement.addHistory(
                    gameMode = getGameModeName(),
                    winner = "TIE",
                    gridSize = gridSize.toLong(),
                    end_time = getCurrentTime()
                )
                lastGame.value = GameState.OnTie
                lastWinner.value = whoTurn
                _dialogueMessage.value = "It's a tie!"

            }
        }
    }

    private fun getGameModeName(): String {
        return when (gameMode) {
            GameMode.PvP -> "PvP"
            GameMode.AI -> "AI"
        }
    }

    fun startNewGame() {

        when (gameMode) {
            GameMode.PvP -> {
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

        _onWorking.value = true
    }

    fun onQuitClicked(status: PlayDialogueState) {
        _dialogueStatus.value = status
    }

    fun onBackOrExit(navigator: Navigator) {
        navigator.pop()
    }

}
