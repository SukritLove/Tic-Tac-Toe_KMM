package ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import tic_tac_toe_kmm.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font
import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontStyle

@Composable
@OptIn(ExperimentalResourceApi::class)
fun Typo(): Typography {

    val rowdies = FontFamily(Font(Res.font.rowdies_bold))


    return Typography(
        titleLarge = TextStyle(
            fontFamily = rowdies,
            fontWeight = FontWeight.W700,
            fontStyle = FontStyle.Normal,
            fontSize = 36.sp,
            lineHeight = 53.82.sp,
            letterSpacing = 0.5.sp
        ),
        titleMedium = TextStyle(
            fontFamily = rowdies,
            fontWeight = FontWeight.W700,
            fontStyle = FontStyle.Normal,
            fontSize = 26.sp,
            lineHeight = 0.sp,
            letterSpacing = 0.5.sp
        ),
        titleSmall = TextStyle(
            fontFamily = rowdies,
            fontWeight = FontWeight.W700,
            fontStyle = FontStyle.Normal,
            fontSize = 17.sp,
            lineHeight = 0.sp,
            letterSpacing = 0.5.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = rowdies,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            fontSize = 25.sp,
            lineHeight = 0.sp,
            letterSpacing = 0.5.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = rowdies,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            fontSize = 15.sp,
            lineHeight = 0.sp,
            letterSpacing = 0.5.sp
        ),
        bodySmall = TextStyle(
            fontFamily = rowdies,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            fontSize = 7.sp,
            lineHeight = 0.sp,
            letterSpacing = 0.5.sp
        )
    )
}