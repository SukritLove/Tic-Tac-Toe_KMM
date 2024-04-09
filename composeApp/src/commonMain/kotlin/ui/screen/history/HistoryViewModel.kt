package ui.screen.history

import cafe.adriel.voyager.navigator.Navigator
import data.database.DatabaseManagement
import data.domain.HistoryData
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ui.model.ButtonType
import ui.model.GameMode

class HistoryViewModel : ViewModel() {


    private val dataManagement = DatabaseManagement()

    private val _history = MutableLiveData<List<HistoryData>>(emptyList())

    private val _showHistory = MutableLiveData<List<HistoryData>>(emptyList())
    val showHistory: LiveData<List<HistoryData>> = _showHistory

    private val _filterType = MutableLiveData(GameMode.PvP)
    val filterType: LiveData<GameMode> = _filterType

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            _history.value = dataManagement.getHistory()
            _showHistory.value = _history.value.filter { it.gameMode == GameMode.PvP.name }
            if (_showHistory.value.isNotEmpty()) {
                delay(350)
                _isLoading.value = true
            }
        }
    }

    fun clearHistory() {
        dataManagement.clearHistory()
        _showHistory.value = emptyList()
        _history.value = emptyList()
    }

    fun onBackClicked(navigator: Navigator) {
        navigator.pop()
    }

    fun onNextAndPreviousClicked(filter: ButtonType) {
        _filterType.value = when (filter) {
            ButtonType.Next -> GameMode.AI
            ButtonType.Previous -> GameMode.PvP
            else -> GameMode.AI
        }
        filterHistory()
    }

    private fun filterHistory() {
        _isLoading.value = false
        viewModelScope.launch {
            _showHistory.value = _history.value.filter { it.gameMode == _filterType.value.name }
            delay(350)
            _isLoading.value = true
        }

    }

}


