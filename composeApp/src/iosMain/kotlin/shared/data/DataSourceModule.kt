package shared.data

import com.database.gameplay.history.GameHistoryDatabase
import data.domain.DataSource
import data.database.SQLDelightDatasource

actual class DataSourceModule(
) {
    actual val dataSource: DataSource by lazy {
        SQLDelightDatasource(
            db = GameHistoryDatabase(driver = DatabaseDriverFactory().createDriver())
        )
    }
}