package data.domain

import com.database.gameplay.history.GameHistoryDatabase
import kotlinx.coroutines.flow.Flow

interface DataSource {

    fun getHistory(): Flow<List<HistoryData>>
    fun addHistory(winner: String?, end_time: String?)
    fun clearHistory()
}