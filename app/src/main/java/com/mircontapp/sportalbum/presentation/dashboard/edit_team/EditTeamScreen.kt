package com.mircontapp.sportalbum.presentation.dashboard.edit_team

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.ext.findAreaEnum
import com.mircontapp.sportalbum.commons.ext.findModuleEnum
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.commons.BooleanEntrySaver
import com.mircontapp.sportalbum.presentation.commons.CustomCircularProgress
import com.mircontapp.sportalbum.presentation.commons.CustomTextField
import com.mircontapp.sportalbum.presentation.commons.EntrySaver

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

                    val name = rememberSaveable(stateSaver = EntrySaver) { mutableStateOf(TextFieldValue(teamModel.name)) }
                    val city = rememberSaveable(stateSaver = EntrySaver) { mutableStateOf(TextFieldValue(teamModel.city ?: "")) }
                    val country = rememberSaveable(stateSaver = EntrySaver) { mutableStateOf(TextFieldValue(teamModel.country ?: "")) }
                    val type = rememberSaveable(stateSaver = EntrySaver) { mutableStateOf(TextFieldValue(teamModel.type ?: "")) }
                    val color1 = rememberSaveable(stateSaver = EntrySaver) { mutableStateOf(TextFieldValue(teamModel.color1 ?: "")) }
                    val color2 = rememberSaveable(stateSaver = EntrySaver) { mutableStateOf(TextFieldValue(teamModel.color2 ?: "")) }
                    val coach = rememberSaveable(stateSaver = EntrySaver) { mutableStateOf(TextFieldValue(teamModel.coach ?: "")) }
                    val coachlegend = rememberSaveable(stateSaver = EntrySaver) { mutableStateOf(TextFieldValue(teamModel.coachlegend ?: "")) }
                    val stadium = rememberSaveable(stateSaver = EntrySaver) { mutableStateOf(TextFieldValue(teamModel.stadium ?: "")) }
                    val area = rememberSaveable(stateSaver = EntrySaver) { mutableStateOf(TextFieldValue(teamModel.area?.name ?: "")) }
                    val arealegend = rememberSaveable(stateSaver = EntrySaver) { mutableStateOf(TextFieldValue(teamModel.arealegend?.name ?: "")) }
                    var isMatch = rememberSaveable(stateSaver = BooleanEntrySaver){ mutableStateOf(teamModel.isMatch ?: false) }
                    val module = rememberSaveable(stateSaver = EntrySaver) { mutableStateOf(TextFieldValue(teamModel.module.name)) }

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
                                Checkbox(checked = isMatch.value, onCheckedChange = {
                                    isMatch.value = it
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
                                        isMatch = isMatch.value,
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




