package data

import kotlinx.datetime.Clock
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.TimeZone

fun getCurrentTime(): String {
    val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    return "${currentTime.hour.toString().padStart(2, '0')}:${
        currentTime.minute.toString().padStart(2, '0')
    }"
}