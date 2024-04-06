package ui.screen.home

import cafe.adriel.voyager.navigator.Navigator
import data.dataRepository.DataRepo
import data.dataRepository.DataRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ui.screen.history.HistoryScreen
import ui.screen.playscreen.PlayScreen

class HomeViewModel : ViewModel() {

    private val _gridSize = MutableStateFlow(3)
    val gridSize: StateFlow<Int> = _gridSize


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
        DataRepo.data = DataRepository(gridSize = gridSize.value)
        navigator.push(PlayScreen())
    }

    fun onClickHistory(navigator: Navigator) {
        navigator.push(HistoryScreen())
    }

}