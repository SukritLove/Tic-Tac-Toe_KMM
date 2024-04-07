package ui.screen.home

import cafe.adriel.voyager.navigator.Navigator
import data.dataRepository.DataRepo
import data.dataRepository.DataRepository
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ui.model.GameMode
import ui.screen.history.HistoryScreen
import ui.screen.playscreen.PlayScreen

class HomeViewModel : ViewModel() {

    private val _gridSize = MutableStateFlow(3)
    val gridSize: StateFlow<Int> = _gridSize

    private val _gameMode = MutableLiveData(GameMode.Player)
    val gameMode: LiveData<GameMode> = _gameMode


    fun incrementCount() = viewModelScope.launch {
        if (_gridSize.value != 8) {
            _gridSize.value++
            println(gridSize)
        }
    }

    fun decrementCount() = viewModelScope.launch {
        if (_gridSize.value != 3) {
            _gridSize.value--
        }
    }

    fun onClickPlay(navigator: Navigator) {
        DataRepo.data = DataRepository(gridSize = gridSize.value, gameMode = _gameMode.value)
        navigator.push(PlayScreen())
    }

    fun onClickHistory(navigator: Navigator) {
        navigator.push(HistoryScreen())
    }

    fun onSelectGameMode() {

        _gameMode.value = when (_gameMode.value) {
            GameMode.Player -> GameMode.AI
            GameMode.AI -> GameMode.Player
        }
    }

}