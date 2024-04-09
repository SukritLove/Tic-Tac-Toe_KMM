package shared.common

import platform.posix.exit

actual fun exitApplication() {
    exit(0)
}