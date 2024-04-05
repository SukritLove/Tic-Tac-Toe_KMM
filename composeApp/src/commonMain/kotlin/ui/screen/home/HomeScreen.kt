package ui.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.compose.AppColor
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tic_tac_toe_kmm.composeapp.generated.resources.Res
import ui.component.CustomButton
import ui.component.CustomText
import ui.theme.Typo

@OptIn(ExperimentalResourceApi::class)

class HomeScreen : Screen {

    private val viewModel = HomeViewModel()

    @Composable
    override fun Content() {

        val typo = Typo()

        val gridSize by viewModel.gridSize.collectAsState()

        val navigator = LocalNavigator.currentOrThrow


        Column(
            modifier = Modifier.fillMaxSize().background(AppColor.background).padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            CustomText.logoText(
                text = stringResource(Res.string.app_name),
                textStyle = typo.titleLarge
            )
            Text("Select Grid Size")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(Modifier.weight(1f))
                CustomButton.addRemove("-") { viewModel.decrementCount() }
                Spacer(Modifier.padding(20.dp))
                Text("$gridSize x $gridSize")
                Spacer(Modifier.padding(20.dp))
                CustomButton.addRemove("+") { viewModel.incrementCount() }
                Spacer(Modifier.weight(1f))
            }
            Column(Modifier) {
                viewModel.createGrid().forEach { row ->
                    Row {
                        row.forEach { cell ->
                            Card(
                                modifier = Modifier
                                    .weight(5f) // This makes each box take up equal space.
                                    .aspectRatio(1f), // This makes the height equal to the width, creating a square.
                                border = BorderStroke(
                                    0.5.dp,
                                    Color.Black
                                )
                            ) {
                                Row(
                                    Modifier
                                        .aspectRatio(1f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Image(
                                        painterResource(Res.drawable.iconX),
                                        contentDescription = "x",
                                        modifier = Modifier.aspectRatio(1f / 3f)
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}

