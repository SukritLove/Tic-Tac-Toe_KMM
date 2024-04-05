package ui.screen.playscreen

import data.DataRepo
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import tic_tac_toe_kmm.composeapp.generated.resources.Res

enum class Player {
    X, O
}

data class Cell(var value: Player? = null)
class PlayViewModel : ViewModel() {
    private val dataRepo = DataRepo.data

    private val _grid = MutableLiveData(List(getGridSize()) {
        MutableList(getGridSize()) {
            MutableStateFlow<Player?>(null)
        }
    })
    val grid = _grid


    private val _currentPlayer: MutableLiveData<Player> = MutableLiveData(Player.X)
    val currentPlayer: LiveData<Player> = _currentPlayer

    @OptIn(ExperimentalResourceApi::class)
    fun playTurn(row: Int, col: Int) {
        val currentGrid = _grid.value.map { it.toMutableList() }
        if (currentGrid[row][col].value == null) {
            currentGrid[row][col].value = _currentPlayer.value
            _currentPlayer.value =
                when (_currentPlayer.value) {
                    Player.X -> Player.O
                    Player.O -> Player.X
                }
            _grid.value = currentGrid // Trigger LiveData update
        }

    }

    fun getGridSize(): Int {
        return dataRepo.gridSize
    }


}
