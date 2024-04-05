package ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.compose.AppColor
import org.jetbrains.compose.resources.stringResource
import tic_tac_toe_kmm.composeapp.generated.resources.Res

object CustomText {

    @Composable
    fun logoText(text: String, textStyle: TextStyle) {

        val appendText = text.split(" ")
        val annotatedString = buildAnnotatedString {
            // Tic in Red
            withStyle(style = SpanStyle(color = AppColor.tic)) {
                append(appendText[0])
            }
            append(" ") // Adding space between words
            // Tac in Green
            withStyle(style = SpanStyle(color = AppColor.tac)) {
                append(appendText[1])
            }
            append(" ") // Adding space between words
            // Toe in Blue
            withStyle(style = SpanStyle(color = AppColor.toe)) {
                append(appendText[2])
            }
        }
        Text(
            text = annotatedString,
            style = textStyle
        )

    }

}