package shared.data

import android.content.Context
import com.database.gameplay.history.GameHistoryDatabase
import data.domain.DataSource
import data.domain.SQLDelightDatasource

actual class DataSourceModule(
    private val context: Context
) {
    actual val dataSource: DataSource by lazy {
        SQLDelightDatasource(
            db = GameHistoryDatabase(driver = DatabaseDriverFactory(context).createDriver())
        )
    }
}