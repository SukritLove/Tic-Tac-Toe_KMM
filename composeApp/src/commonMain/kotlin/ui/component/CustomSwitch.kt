package ui.component

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import com.example.compose.AppColor
import ui.model.GameMode
import ui.theme.Typo

@Composable
fun GameModeSwitch(checked: GameMode, onCheckChange: (Boolean) -> Unit) {
    Switch(
        modifier = Modifier.scale(2.5f),
        checked = checked != GameMode.PvP,
        onCheckedChange = onCheckChange,
        thumbContent = {
            Text(
                if (checked == GameMode.PvP) {
                    "PvP"
                } else {
                    "AI"
                },
                style = Typo().bodySmall
            )
        },
        colors = SwitchDefaults.colors(
            uncheckedThumbColor = AppColor.light_sienna,
            uncheckedTrackColor = AppColor.yellow_wheat,
            uncheckedBorderColor = AppColor.light_sienna,
            uncheckedIconColor = AppColor.yellow_wheat,
            //UnChecked
            checkedThumbColor = AppColor.yellow_wheat,
            checkedTrackColor = AppColor.light_sienna,
            checkedBorderColor = AppColor.light_sienna,
            checkedIconColor = AppColor.light_sienna
        )
    )
}