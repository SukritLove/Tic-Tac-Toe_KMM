package data.domain

import com.database.gameplay.history.GameHistory

fun GameHistory.toHistory(): HistoryData {
    return HistoryData(
        game_id = game_id,
        gameMode = gameMode,
        winner = winner,
        gridSize = gridSize,
        end_Time = end_time
    )
}
