package com.mircontapp.sportalbum.presentation.bupi

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.TeamHelper
import com.mircontapp.sportalbum.commons.UIHelper
import com.mircontapp.sportalbum.commons.customTextEdit
import com.mircontapp.sportalbum.domain.models.BupiPlayerModel
import com.mircontapp.sportalbum.domain.models.BupiTeamModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.commons.CustomTextField
import com.mircontapp.sportalbum.presentation.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBupiTeamScreen(navController: NavController, mainViewModel: MainViewModel) {
    val bupiViewModel: BupiViewModel = hiltViewModel()
    val team = remember {
        mainViewModel.selectedBupiTeam.let {
            if (it != null) {
                it
            } else {
                BupiTeamModel("Team", "")
            }
        }
    }

    val name = remember { mutableStateOf(TextFieldValue(team.name)) }
    val area = remember { mutableStateOf(TextFieldValue(team.area ?:"")) }

        Column {

            CustomTextField(value = name.value, onValueChange = { name.value = it })

            CustomTextField(value = area.value, onValueChange = { area.value = it})

            Button(onClick = {
                bupiViewModel.updateTeam(
                    BupiTeamModel(
                        name.value.text,
                        area.value.text
                    )
                )
                navController.popBackStack()
            }) {
                Text(text = SportAlbumApplication.instance.getString(R.string.update))
            }

        }

}

