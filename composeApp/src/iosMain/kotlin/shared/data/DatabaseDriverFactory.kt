package shared.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.database.gameplay.history.GameHistoryDatabase

actual class DatabaseDriverFactory() {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(GameHistoryDatabase.Schema, "gameplay-history.db")
    }
}