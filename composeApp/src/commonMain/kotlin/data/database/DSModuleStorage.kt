package data.database

import data.domain.DataSource
import shared.data.DataSourceModule

object DSModuleStorage {
    private lateinit var dataSourceModule: DataSourceModule

    fun init(db: DataSourceModule) {
        dataSourceModule = db
    }

    fun dataSource(): DataSource {
        return dataSourceModule.dataSource
    }
}