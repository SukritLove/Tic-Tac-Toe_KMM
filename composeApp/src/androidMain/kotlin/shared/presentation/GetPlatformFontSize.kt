package shared.presentation

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun getPlatformFontSize(): Float {
    return when (LocalContext.current.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) {
        Configuration.SCREENLAYOUT_SIZE_LARGE -> 20f
        Configuration.SCREENLAYOUT_SIZE_NORMAL -> 16f
        Configuration.SCREENLAYOUT_SIZE_SMALL -> 12f
        else -> 14f
    }

}