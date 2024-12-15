package com.mircontapp.sportalbum.presentation.dashboard.edit_player

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
import com.mircontapp.sportalbum.commons.AlbumHelper
import com.mircontapp.sportalbum.commons.ext.genderFromString
import com.mircontapp.sportalbum.commons.ext.roleFromString
import com.mircontapp.sportalbum.commons.ext.roleLineUpFromString
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.presentation.commons.BooleanEntrySaver
import com.mircontapp.sportalbum.presentation.commons.CustomCircularProgress
import com.mircontapp.sportalbum.presentation.commons.CustomTextField
import com.mircontapp.sportalbum.presentation.commons.EntrySaver

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPlayerScreen(navController: NavController, playerArg: String?) {
    val viewModel: EditPlayerViewModel = hiltViewModel()

    playerArg.let {
        if (it != null) {
            Gson().fromJson(playerArg, PlayerModel::class.java).let {
                viewModel.onAction(EditPlayerAction.ShowEdit(it))
            }
        }
    }

    viewModel.state.collectAsState().value.let {
        when {
            it.isLoading-> CustomCircularProgress(modifier = Modifier.fillMaxWidth())
            else -> {
                it.playerModel.let { playerModel ->
                    val name = rememberSaveable(stateSaver = EntrySaver) { mutableStateOf(TextFieldValue(playerModel.name)) }
                    val role = rememberSaveable(stateSaver = EntrySaver) { mutableStateOf(TextFieldValue(playerModel.role.name)) }
                    val gender = rememberSaveable(stateSaver = EntrySaver) { mutableStateOf(TextFieldValue(playerModel.gender?.name ?: "")) }
                    val team = rememberSaveable(stateSaver = EntrySaver) { mutableStateOf(TextFieldValue(playerModel.team ?: "")) }
                    val country = rememberSaveable(stateSaver = EntrySaver) { mutableStateOf(TextFieldValue(playerModel.country ?: "")) }
                    val birthyear = rememberSaveable(stateSaver = EntrySaver) { mutableStateOf(TextFieldValue(playerModel.birthyear ?: "")) }
                    val value = rememberSaveable(stateSaver = EntrySaver) { mutableStateOf(TextFieldValue(playerModel.value.toString() ?: "")) }
                    val valueleg = rememberSaveable(stateSaver = EntrySaver) { mutableStateOf(TextFieldValue(playerModel.valueleg.toString() ?: "")) }
                    val teamLegend = rememberSaveable(stateSaver = EntrySaver) { mutableStateOf(TextFieldValue(playerModel.teamLegend ?: "")) }
                    val national = rememberSaveable(stateSaver = BooleanEntrySaver) { mutableStateOf(playerModel.national == 1) }
                    val nationalLegend = rememberSaveable(stateSaver = BooleanEntrySaver) { mutableStateOf(playerModel.nationalLegend == 1) }
                    var roleLineUp = rememberSaveable(stateSaver = EntrySaver){ mutableStateOf(TextFieldValue(playerModel.roleLineUp?.name?:"")) }
                    var style = rememberSaveable(stateSaver = EntrySaver){ mutableStateOf(TextFieldValue(playerModel.style?.name?:"")) }


                    Row(modifier = Modifier.verticalScroll(rememberScrollState()).padding(8.dp)) {
                        Column {

                            CustomTextField(value = name.value, onValueChange = { name.value = it })

                            CustomTextField(value = team.value, onValueChange = { team.value = it })

                            Row {
                                CustomTextField(value = role.value,
                                    onValueChange = { role.value = it })

                                CustomTextField(value = roleLineUp.value,
                                    onValueChange = { roleLineUp.value = it },)
                            }

                            CustomTextField(value = country.value, onValueChange = { country.value = it })

                            CustomTextField(value = teamLegend.value, onValueChange = { teamLegend.value = it})


                            Row {
                                CustomTextField(value = birthyear.value, onValueChange = { birthyear.value = it})

                                CustomTextField(value = gender.value, onValueChange = { gender.value = it })

                            }

                            Row {
                                CustomTextField(value = valueleg.value, onValueChange = { valueleg.value = it})

                                CustomTextField(value = value.value, onValueChange = { value.value = it})
                            }

                            CustomTextField(value = style.value, onValueChange = { style.value = it})


                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
                                Checkbox(checked = national.value, onCheckedChange = {
                                    national.value = it
                                })

                                Text(text = SportAlbumApplication.instance.getString(R.string.national))
                            }

                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
                                Checkbox(checked = nationalLegend.value, onCheckedChange = {
                                    nationalLegend.value = it
                                })

                                Text(text = SportAlbumApplication.instance.getString(R.string.nationLegend))
                            }

                            Button(onClick = {
                                viewModel.onAction(EditPlayerAction.SaveEdit(
                                    PlayerModel(name.value.text,
                                        role.value.text.roleFromString() ?: Enums.Role.PP,
                                        gender.value.text.genderFromString(),
                                        team.value.text,
                                        country.value.text,
                                        birthyear.value.text,
                                        value.value.text.toInt(),
                                        valueleg.value.text.toInt(),
                                        teamLegend.value.text,
                                        if (national.value) 1 else 0,
                                        if (nationalLegend.value) 1 else 0,
                                        roleLineUp.value.text.roleLineUpFromString() ?: Enums.RoleLineUp.PTC,
                                        style = Enums.PlayStyle.NORMAL
                                    )
                                ))
                                navController.popBackStack()
                            }) {
                                Text(text = SportAlbumApplication.instance.getString(R.string.save))
                            }

                        }
                    }
                }
            }
        }
    }




}

