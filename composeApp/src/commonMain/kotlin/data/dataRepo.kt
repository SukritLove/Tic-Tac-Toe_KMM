package data

data class DataRepository(
    val gridSize: Int
)

object DataRepo {
    var data = DataRepository(3)
}