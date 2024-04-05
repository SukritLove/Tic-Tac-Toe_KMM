package ui.screen.playscreen

import data.DataRepo
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

enum class Player {
    X, O
}

class PlayViewModel : ViewModel() {
    private val dataRepo = DataRepo.data

    private val _grid: MutableLiveData<List<MutableList<MutableStateFlow<Player?>>>> =
        MutableLiveData(List(getGridSize()) {
            MutableList(getGridSize()) {
                MutableStateFlow<Player?>(null)
            }
        })
    val grid: MutableLiveData<List<MutableList<MutableStateFlow<Player?>>>> = _grid

    private val _winner = MutableLiveData<String?>(null)
    val winner: LiveData<String?> = _winner

    private val _currentPlayer: MutableLiveData<Player> = MutableLiveData(Player.X)
    val currentPlayer: LiveData<Player> = _currentPlayer

    fun playTurn(row: Int, col: Int) {
        val currentGrid = _grid.value.map { it.toMutableList() }
        if (currentGrid[row][col].value == null && _winner.value == null) {
            currentGrid[row][col].value = _currentPlayer.value
            _currentPlayer.value =
                when (_currentPlayer.value) {
                    Player.X -> Player.O
                    Player.O -> Player.X
                }
            _grid.value = currentGrid // Trigger LiveData update
            _winner.value = checkWinner(currentGrid)
        }
        println(winner.value)

    }

    private fun checkWinner(currentGrid: List<MutableList<MutableStateFlow<Player?>>>): String? {
        val g = currentGrid.size
        val winCheck = mutableListOf(
            mutableListOf(true, true),  // diagonal check
            MutableList(g) { true },    // rows check
            MutableList(g) { true }     // columns check
        )
        for (x in 0 until g) {
            for (y in 0 until g) {
                //Row
                if (currentGrid[x][y].value != currentGrid[x][0].value ||
                    currentGrid[x][y].value == null
                ) {
                    winCheck[1][x] = false
                    println("row")
                }
                //Column
                if (currentGrid[y][x].value != currentGrid[0][x].value ||
                    currentGrid[y][x].value == null
                ) {
                    winCheck[2][x] = false
                    println("col")
                }
                //Diagonal
                if (currentGrid[x][x].value != currentGrid[0][0].value ||
                    currentGrid[x][x].value == null
                ) {
                    winCheck[0][0] = false
                }
                //Reverse Diagonal
                if (currentGrid[x][g - x - 1].value != currentGrid[0][g - 1].value ||
                    currentGrid[x][g - x - 1].value == null
                )
                    winCheck[0][1] = false
            }

        }
        println(winCheck)
        return if (winCheck.any { it.contains(true) }) "win" else null
    }

    fun getGridSize(): Int {
        return dataRepo.gridSize
    }

}
