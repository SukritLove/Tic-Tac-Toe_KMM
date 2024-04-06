package data.domain

import com.database.gameplay.history.GameHistory

fun GameHistory.toHistory(): HistoryData {
    return HistoryData(
        game_id = game_id,
        winner = winner,
        end_Time = end_time
    )
}
