package ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.compose.AppColor
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import tic_tac_toe_kmm.composeapp.generated.resources.Res
import ui.model.ButtonType
import ui.model.GameMode
import ui.theme.Typo

object CustomButton {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun iconButton(
        modifier: Modifier = Modifier,
        btnText: ButtonType,
        onButtonClick: () -> Unit,
        gameMode: GameMode = GameMode.PvP
    ) {
        val isEnable = gameMode == GameMode.PvP
        Box(
            modifier = modifier.size(50.dp).clickable(onClick = onButtonClick, enabled = isEnable),
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(
                        when (btnText) {
                            ButtonType.Plus -> Res.drawable.plus
                            ButtonType.Minus -> Res.drawable.minus
                            ButtonType.Next -> Res.drawable.next
                            ButtonType.Previous -> Res.drawable.previous
                            else -> Res.drawable.blank
                        }
                    ),
                    contentDescription = when (btnText) {
                        ButtonType.Plus -> ButtonType.Plus.name
                        ButtonType.Minus -> ButtonType.Minus.name
                        ButtonType.Next -> ButtonType.Next.name
                        ButtonType.Previous -> ButtonType.Previous.name
                        else -> ""
                    },
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.colorMatrix(
                        colorMatrix = if (isEnable) {
                            ColorMatrix()
                        } else {
                            ColorMatrix().apply {
                                setToSaturation(
                                    0f
                                )
                            }
                        }
                    )
                )
            }
        }

    }

    @Composable
    fun styledButton(modifier: Modifier = Modifier, btnText: String, onButtonClick: () -> Unit) {
        ElevatedButton(
            onClick = onButtonClick,
            modifier = modifier.size(width = 200.dp, height = 50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColor.pinkish_gray,
                contentColor = AppColor.light_sienna
            )
        ) {
            Text(btnText, style = Typo().titleMedium)
        }
    }

    @Composable
    fun decisionButton(btnText: ButtonType, onButtonClick: () -> Unit) {
        ElevatedButton(
            onClick = onButtonClick,
            modifier = Modifier,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = when (btnText) {
                    ButtonType.Yes -> AppColor.soft_sage_green
                    ButtonType.No -> AppColor.dusty_coral_red
                    else -> AppColor.yellow_wheat
                },
                contentColor = AppColor.background
            )
        ) {
            Text(btnText.name, style = Typo().titleSmall)
        }
    }
}