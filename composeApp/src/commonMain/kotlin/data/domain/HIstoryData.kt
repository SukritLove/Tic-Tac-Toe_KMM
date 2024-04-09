package data.domain

import ui.model.GameMode

data class HistoryData(
    val game_id: Long,
    val gameMode: String,
    val winner: String,
    val gridSize: Long,
    val end_Time: String
)