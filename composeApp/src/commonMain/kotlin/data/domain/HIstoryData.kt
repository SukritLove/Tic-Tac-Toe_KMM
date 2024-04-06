package data.domain

data class HistoryData(
    val game_id: Long,
    val winner: String? = "",
    val end_Time: String? = ""
)