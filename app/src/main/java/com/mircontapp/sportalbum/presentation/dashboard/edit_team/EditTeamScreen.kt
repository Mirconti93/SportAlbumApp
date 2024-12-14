package com.mircontapp.sportalbum.presentation.dashboard.edit_team

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.mircontapp.sportalbum.presentation.commons.CustomCircularProgress
import com.mircontapp.sportalbum.presentation.commons.CustomTextField
import com.mircontapp.sportalbum.presentation.dashboard.dashboard_list.DashboardViewModel
import com.mircontapp.sportalbum.presentation.dashboard.edit_player.EditPlayerAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTeamScreen(navController: NavController, teamArg: String?) {
    val viewModel: EditTeamViewModel = hiltViewModel()

    teamArg.let {
        if (it != null) {
            Gson().fromJson(teamArg, TeamModel::class.java).let {
                viewModel.onAction(EditTeamAction.ShowEdit(it))
            }
        }
    }

    viewModel.state.collectAsState().value.let {
        when {
            it.isLoading -> CustomCircularProgress(modifier = Modifier.fillMaxWidth())
            else -> {
                it.team.let { teamModel ->

                    val name = remember { mutableStateOf(TextFieldValue(teamModel.name)) }
                    val city = remember { mutableStateOf(TextFieldValue(teamModel.city ?: "")) }
                    val country = remember { mutableStateOf(TextFieldValue(teamModel.country ?: "")) }
                    val type = remember { mutableStateOf(TextFieldValue(teamModel.type ?: "")) }
                    val color1 = remember { mutableStateOf(TextFieldValue(teamModel.color1 ?: "")) }
                    val color2 = remember { mutableStateOf(TextFieldValue(teamModel.color2 ?: "")) }
                    val coach = remember { mutableStateOf(TextFieldValue(teamModel.coach ?: "")) }
                    val coachlegend = remember { mutableStateOf(TextFieldValue(teamModel.coachlegend ?: "")) }
                    val stadium = remember { mutableStateOf(TextFieldValue(teamModel.stadium ?: "")) }
                    val area = remember { mutableStateOf(TextFieldValue(teamModel.area?.name ?: "")) }
                    val arealegend = remember { mutableStateOf(TextFieldValue(teamModel.arealegend?.name ?: "")) }
                    var isSuperlega = remember{ mutableStateOf(teamModel.superlega) }
                    val module = remember { mutableStateOf(TextFieldValue(teamModel.module.name)) }

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
                                viewModel.onAction(EditTeamAction.SaveEdit(
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
                                ))
                                navController.popBackStack()
                            }) {
                                Text(SportAlbumApplication.instance.getString(R.string.save))
                            }

                        }
                    }
                }
            }
        }
    }

}

