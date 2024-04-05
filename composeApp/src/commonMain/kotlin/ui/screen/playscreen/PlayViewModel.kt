package ui.screen.playscreen

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import data.DataRepo
import dev.icerock.moko.mvvm.viewmodel.ViewModel

class PlayViewModel : ViewModel() {
    private val dataRepo = DataRepo.data


    fun createGrid(): Pair<List<List<String>>, Dp> {
        return Pair(List(dataRepo.gridSize) {
            List(dataRepo.gridSize) { "" }
        }, when (dataRepo.gridSize){
            3 -> 120.dp
            4 -> 90.dp
            5 -> 70.dp
            6 -> 60.dp
            else -> {40.dp}
        })
    }
}
