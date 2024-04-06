package data.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.database.gameplay.history.GameHistoryDatabase
import data.domain.DataSource
import data.domain.HistoryData
import data.domain.toHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SQLDelightDatasource(db: GameHistoryDatabase) : DataSource {
    private val historyQueries = db.gameHistoryQueries
    override fun getHistory(): Flow<List<HistoryData>> {
        return historyQueries
            .selectAllGames()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { historyEntities ->
                historyEntities.map { historyEntity ->
                    historyEntity.toHistory()
                }
            }
    }

    override fun addHistory(winner: String?, end_time: String?) {
        historyQueries.insertHistory(
            winner = winner,
            end_time = end_time
        )
    }

    override fun clearHistory() {
        historyQueries.deleteHistory()
    }

}