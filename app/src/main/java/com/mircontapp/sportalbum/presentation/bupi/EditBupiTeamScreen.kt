package com.mircontapp.sportalbum.presentation.bupi

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.domain.models.BupiTeamModel
import com.mircontapp.sportalbum.presentation.commons.CustomTextField


@Composable
fun EditBupiTeamScreen(navController: NavController, bupiTeamArg: String?) {
    val bupiViewModel: BupiViewModel = hiltViewModel()
    val team = remember {
        bupiTeamArg.let {
            if (it != null) {
                Gson().fromJson(bupiTeamArg, BupiTeamModel::class.java)
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

