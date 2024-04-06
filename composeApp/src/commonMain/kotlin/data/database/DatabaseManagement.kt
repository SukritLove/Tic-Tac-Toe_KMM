package data.database

import data.domain.HistoryData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import kotlinx.coroutines.flow.first

class DatabaseManagement {
    private val db = DSModuleStorage.dataSource()
    private val history = MutableLiveData<List<HistoryData>>(emptyList())
    suspend fun getHistory(): List<HistoryData> {
        history.value = db.getHistory().first()
        return history.value.also {
            history.value = emptyList()
        }
    }

    fun addHistory(winner: String, end_time: String) {
        db.addHistory(winner, end_time)
    }

    fun clearHistory() {
        db.clearHistory()
    }

}