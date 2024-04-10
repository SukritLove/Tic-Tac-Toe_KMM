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
import ui.model.DialogueState
import ui.model.GameMode
import ui.screen.history.HistoryScreen
import ui.screen.play.PlayScreen

class HomeViewModel : ViewModel() {

    private val _gridSize = MutableStateFlow(3)
    val gridSize: StateFlow<Int> = _gridSize

    private val _gameMode = MutableLiveData(GameMode.PvP)
    val gameMode: LiveData<GameMode> = _gameMode

    private val _dialogueState = MutableLiveData(DialogueState.OnDismiss)
    val dialogueState: LiveData<DialogueState> = _dialogueState

    private val _isEnable = MutableLiveData(true)
    val isEnable: LiveData<Boolean> = _isEnable


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
        navigator.push(PlayScreen(navigator))
    }

    fun onClickHistory(navigator: Navigator) {
        navigator.push(HistoryScreen(navigator))
    }

    fun onSelectGameMode() {
        when (_gameMode.value) {
            GameMode.PvP -> {
                _gameMode.value = GameMode.AI
                _isEnable.value = false
                _gridSize.value = 3
            }

            GameMode.AI -> {
                _gameMode.value = GameMode.PvP
                _isEnable.value = true
            }
        }
    }

    fun onExit(state: DialogueState) {
        _dialogueState.value = state
    }

    fun enableButton() {

    }


}