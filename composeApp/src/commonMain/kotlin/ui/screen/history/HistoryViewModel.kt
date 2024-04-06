package ui.screen.history

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import data.database.DatabaseManagement
import data.domain.HistoryData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch
import ui.screen.home.HomeViewModel

class HistoryViewModel : ViewModel() {


    private val dataManagement = DatabaseManagement()

    private val _history = MutableLiveData<List<HistoryData>>(emptyList())
    val history: MutableLiveData<List<HistoryData>> = _history
    suspend fun getHistory() {
        viewModelScope.launch {
            _history.value = dataManagement.getHistory()
        }
    }

    fun clearHistory() {
        dataManagement.clearHistory()
        _history.value = emptyList()
    }


}