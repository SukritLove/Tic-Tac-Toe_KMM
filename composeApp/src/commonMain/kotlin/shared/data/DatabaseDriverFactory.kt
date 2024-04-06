package shared.data

import app.cash.sqldelight.db.SqlDriver
import com.database.gameplay.history.GameHistoryDatabase

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}
