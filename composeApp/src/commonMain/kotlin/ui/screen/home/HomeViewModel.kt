package ui.screen.home

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import data.DataRepo
import data.DataRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ui.screen.playscreen.PlayScreen

class HomeViewModel : ViewModel() {

    private val _gridSize = MutableStateFlow(3) // Backing property
    val gridSize: StateFlow<Int> = _gridSize // Publicly exposed as read-only


    fun incrementCount() = viewModelScope.launch {
        if (_gridSize.value != 9) {
            _gridSize.value++
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

    fun createGrid(): List<List<String>> {
        return List(gridSize.value) {
            List(gridSize.value) { "x" }
        }

    }
}