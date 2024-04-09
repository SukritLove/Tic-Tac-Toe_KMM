package shared.common

import kotlin.system.exitProcess

actual fun exitApplication() {
    exitProcess(-1)
}