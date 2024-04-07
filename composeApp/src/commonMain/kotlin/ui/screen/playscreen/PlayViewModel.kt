package ui.screen.playscreen

import data.dataRepository.DataRepo
import data.database.DatabaseManagement
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import ui.model.DialogueState
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

    fun playTurn(row: Int, col: Int) {
        val currentGrid = _grid.value.map { it.toMutableList() }
        if (currentGrid[row][col].value == null && _dialogueStatus.value == DialogueState.OnDefault) {
            currentGrid[row][col].value = _currentPlayer.value
            val status = checkWinner(currentGrid)
            if (status == DialogueState.OnWin || status == DialogueState.OnTie) {
                setDialogue(state = status, whoTurn = _currentPlayer.value)
                //startNewGame()
            } else {

                _currentPlayer.value =
                    when (_currentPlayer.value) {
                        Player.X -> Player.O
                        Player.O -> Player.X
                        else -> _currentPlayer.value
                    }
            }
            _grid.value = currentGrid
        }
    }


    fun startNewGame() {
        _grid.value = List(gridSize) {
            MutableList(gridSize) {
                MutableStateFlow<Player?>(null)
            }
        }
        _dialogueStatus.value = DialogueState.OnDefault
        _currentPlayer.value = lastWinner.value ?: Player.X
        lastWinner.value = null
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
                    println("row")
                }
                //Column
                if (grid[y][x].value != grid[0][x].value ||
                    grid[y][x].value == null
                ) {
                    winCheck[2][x] = false
                    println("col")
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
        println(winCheck)
        return if (winCheck.any {
                it.contains(true)
            }) DialogueState.OnWin else if (isBoardFull(grid) && winCheck.none { it.contains(true) }) DialogueState.OnTie else DialogueState.OnDefault
    }

    private fun isBoardFull(grid: List<MutableList<MutableStateFlow<Player?>>>): Boolean {
        return grid.all { row -> row.all { cell -> cell.value != null } }
    }
}
