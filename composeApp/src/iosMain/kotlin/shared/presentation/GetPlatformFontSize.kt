package shared.presentation

import androidx.compose.runtime.Composable
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIScreen

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun getPlatformFontSize(): Float {
    val screenSize = UIScreen.mainScreen.bounds.size
    return if (screenSize > 600) {
        20f
    } else {
        16f
    }
}