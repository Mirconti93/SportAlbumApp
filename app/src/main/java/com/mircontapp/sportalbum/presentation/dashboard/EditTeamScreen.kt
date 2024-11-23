package com.mircontapp.sportalbum.presentation.dashboard

import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.google.gson.Gson
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.ext.findAreaEnum
import com.mircontapp.sportalbum.commons.ext.findModuleEnum
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.commons.CustomTextField
import com.mircontapp.sportalbum.presentation.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTeamScreen(navController: NavController, teamArg: String?) {
    val dashboardViewModel: DashboardViewModel = hiltViewModel()
    val teamModel = remember {
        teamArg.let {
            if (it != null) {
                Gson().fromJson(teamArg, TeamModel::class.java)
            } else {
                dashboardViewModel.updateType.value = Enums.UpdateType.NEW
                TeamModel("New", "", "", "", "", "", "", Enums.Area.OTHER, Enums.Area.OTHER,false,"", "", Enums.MatchModule.M442)
            }
        }
    }

    val name = rememberSaveable { mutableStateOf(TextFieldValue(teamModel.name)) }
    val city = rememberSaveable { mutableStateOf(TextFieldValue(teamModel.city ?: "")) }
    val country = rememberSaveable { mutableStateOf(TextFieldValue(teamModel.country ?: "")) }
    val type = rememberSaveable { mutableStateOf(TextFieldValue(teamModel.type ?: "")) }
    val color1 = rememberSaveable { mutableStateOf(TextFieldValue(teamModel.color1 ?: "")) }
    val color2 = rememberSaveable { mutableStateOf(TextFieldValue(teamModel.color2 ?: "")) }
    val coach = rememberSaveable { mutableStateOf(TextFieldValue(teamModel.coach ?: "")) }
    val coachlegend = rememberSaveable { mutableStateOf(TextFieldValue(teamModel.coachlegend ?: "")) }
    val stadium = rememberSaveable { mutableStateOf(TextFieldValue(teamModel.stadium ?: "")) }
    val area = rememberSaveable { mutableStateOf(TextFieldValue(teamModel.area?.name ?: "")) }
    val arealegend = rememberSaveable { mutableStateOf(TextFieldValue(teamModel.arealegend?.name ?: "")) }
    var isSuperlega = rememberSaveable{ mutableStateOf(teamModel.superlega) }
    val module = rememberSaveable { mutableStateOf(TextFieldValue(teamModel.module.name)) }

    Row(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Column {

            CustomTextField(value = name.value, onValueChange = { name.value = it })

            CustomTextField(value = city.value, onValueChange = { city.value = it })

            CustomTextField(value = country.value, onValueChange = { country.value = it })

            CustomTextField(value = type.value, onValueChange = { type.value = it })

            CustomTextField(value = stadium.value, onValueChange = { stadium.value = it})

            CustomTextField(value = color1.value, onValueChange = { color1.value = it})

            CustomTextField(value = color2.value, onValueChange = { color2.value = it})

            CustomTextField(value = coach.value, onValueChange = { coach.value = it})

            CustomTextField(value = coachlegend.value, onValueChange = { coachlegend.value = it})

            CustomTextField(value = area.value, onValueChange = { area.value = it})

            CustomTextField(value = arealegend.value, onValueChange = { arealegend.value = it})

            CustomTextField(value = module.value, onValueChange = { module.value = it})

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 32.dp)) {
                Checkbox(checked = isSuperlega.value ?: false, onCheckedChange = {
                    isSuperlega.value = it
                })

                Text(text = SportAlbumApplication.instance.getString(R.string.superlega))
            }
            
            Button(onClick = {
                dashboardViewModel.updateTeam(
                    TeamModel(
                        name = name.value.text,
                        city = city.value.text,
                        country = country.value.text,
                        type = type.value.text,
                        stadium = stadium.value.text,
                        color1 = color1.value.text,
                        color2 = color2.value.text,
                        coach = coach.value.text,
                        coachlegend = coach.value.text,
                        area = area.value.text.findAreaEnum(),
                        arealegend = arealegend.value.text.findAreaEnum(),
                        superlega = isSuperlega.value,
                        module = module.value.text.findModuleEnum()
                    )
                )
                Toast.makeText(SportAlbumApplication.instance, SportAlbumApplication.instance.getString(R.string.dataUpdated), Toast.LENGTH_LONG)
            }) {
                Text(text = if (dashboardViewModel.updateType.value == Enums.UpdateType.NEW)
                    SportAlbumApplication.instance.getString(R.string.add)
                    else SportAlbumApplication.instance.getString(R.string.update))
            }


        }
    }

}

