package data.dataRepository

import ui.model.GameMode

data class DataRepository(
    val gridSize: Int,
    val gameMode: GameMode
)

object DataRepo {
    var data = DataRepository(3, GameMode.PvP)
}