package data.dataRepository

data class DataRepository(
    val gridSize: Int
)

object DataRepo {
    var data = DataRepository(3)
}