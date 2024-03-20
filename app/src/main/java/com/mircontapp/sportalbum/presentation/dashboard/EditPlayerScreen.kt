package com.mircontapp.sportalbum.presentation.dashboard

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
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
import com.mircontapp.sportalbum.commons.PlayerHelper
import com.mircontapp.sportalbum.commons.TeamHelper
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPlayerScreen(navController: NavController, mainViewModel: MainViewModel) {
    val dashboardViewModel: DashboardViewModel = hiltViewModel()

    val playerModel = remember {
        mainViewModel.playerModel.let {
            if (it != null) {
                dashboardViewModel.updateType.value = DashboardViewModel.UpdateType.UPDATE
                it
            } else {
                dashboardViewModel.updateType.value = DashboardViewModel.UpdateType.NEW
                PlayerHelper.buildPlayerModel("Player")
            }
        }
    }
    
    val textFieldModifier: Modifier = Modifier.padding(4.dp)

    val name = remember { mutableStateOf(TextFieldValue(playerModel.name)) }
    val role = remember { mutableStateOf(TextFieldValue(playerModel.role.name)) }
    val gender = remember { mutableStateOf(TextFieldValue(playerModel.gender?.name ?: "")) }
    val team = remember { mutableStateOf(TextFieldValue(playerModel.team ?: "")) }
    val country = remember { mutableStateOf(TextFieldValue(playerModel.country ?: "")) }
    val birthyear = remember { mutableStateOf(TextFieldValue(playerModel.birthyear ?: "")) }
    val value = remember { mutableStateOf(TextFieldValue(playerModel.value.toString() ?: "")) }
    val valueleg = remember { mutableStateOf(TextFieldValue(playerModel.valueleg.toString() ?: "")) }
    val teamLegend = remember { mutableStateOf(TextFieldValue(playerModel.teamLegend ?: "")) }
    val national = remember { mutableStateOf(playerModel.national == 1) }
    val nationalLegend = remember { mutableStateOf(playerModel.nationalLegend == 1) }
    var roleLineUp = remember{ mutableStateOf(TextFieldValue(playerModel.roleLineUp?.name?:"")) }
    var att = remember{ mutableStateOf(TextFieldValue(playerModel.att.toString())) }
    var dif = remember{ mutableStateOf(TextFieldValue(playerModel.dif.toString())) }
    var tec = remember{ mutableStateOf(TextFieldValue(playerModel.tec.toString())) }
    var dri = remember{ mutableStateOf(TextFieldValue(playerModel.dri.toString())) }
    var fin = remember{ mutableStateOf(TextFieldValue(playerModel.fin.toString())) }
    var bal = remember{ mutableStateOf(TextFieldValue(playerModel.bal.toString())) }
    var fis = remember{ mutableStateOf(TextFieldValue(playerModel.fis.toString())) }
    var vel = remember{ mutableStateOf(TextFieldValue(playerModel.vel.toString())) }
    var rig = remember{ mutableStateOf(TextFieldValue(playerModel.rig.toString())) }
    var por = remember{ mutableStateOf(TextFieldValue(playerModel.por.toString())) }

    Row(modifier = Modifier.verticalScroll(rememberScrollState()).padding(8.dp)) {
        Column {

            TextField(value = name.value, onValueChange = { name.value = it }, modifier = textFieldModifier,
                label = { Text(text = SportAlbumApplication.instance.getString(R.string.name)) })

            TextField(value = team.value, onValueChange = { team.value = it }, modifier = textFieldModifier,
                label = { Text(text = SportAlbumApplication.instance.getString(R.string.team)) })


            Row {
                TextField(value = role.value,
                    onValueChange = { role.value = it },
                    modifier = textFieldModifier,
                    label = { Text(text = SportAlbumApplication.instance.getString(R.string.role)) })

                TextField(value = roleLineUp.value,
                    onValueChange = { roleLineUp.value = it },
                    modifier = textFieldModifier,
                    label = { Text(text = SportAlbumApplication.instance.getString(R.string.roleLineUp)) })
            }

            TextField(value = country.value, onValueChange = { country.value = it }, modifier = textFieldModifier,
                label = { Text(text = SportAlbumApplication.instance.getString(R.string.country)) })

            TextField(value = teamLegend.value, onValueChange = { teamLegend.value = it}, modifier = textFieldModifier,
                label = {Text(text = SportAlbumApplication.instance.getString(R.string.isClubLegend))})


            Row {
                TextField(value = birthyear.value, onValueChange = { birthyear.value = it}, modifier = textFieldModifier.weight(1f),
                    label = {Text(text = SportAlbumApplication.instance.getString(R.string.birthyear))})

                TextField(value = gender.value, onValueChange = { gender.value = it }, modifier = textFieldModifier.weight(1f),
                    label = { Text(text = SportAlbumApplication.instance.getString(R.string.gender)) })

            }

            Row {
                TextField(value = valueleg.value, onValueChange = { valueleg.value = it}, modifier = textFieldModifier.weight(1f),
                    label = {Text(text = SportAlbumApplication.instance.getString(R.string.valueleg))})

                TextField(value = value.value, onValueChange = { value.value = it}, modifier = textFieldModifier.weight(1f),
                    label = {Text(text = SportAlbumApplication.instance.getString(R.string.value))})
            }

            Row {
                TextField(value = att.value, onValueChange = { att.value = it}, modifier = textFieldModifier.weight(1f),
                    label = {Text(text = SportAlbumApplication.instance.getString(R.string.att))})

                TextField(value = dif.value, onValueChange = { dif.value = it}, modifier = textFieldModifier.weight(1f),
                    label = {Text(text = SportAlbumApplication.instance.getString(R.string.dif))})
            }

            Row {
                TextField(value = tec.value, onValueChange = { tec.value = it}, modifier = textFieldModifier.weight(1f),
                    label = {Text(text = SportAlbumApplication.instance.getString(R.string.tec))})

                TextField(value = dri.value, onValueChange = { dri.value = it}, modifier = textFieldModifier.weight(1f),
                    label = {Text(text = SportAlbumApplication.instance.getString(R.string.dri))})
            }

            Row {
                TextField(value = fin.value, onValueChange = { fin.value = it}, modifier = textFieldModifier.weight(1f),
                    label = {Text(text = SportAlbumApplication.instance.getString(R.string.fin))})

                TextField(value = bal.value, onValueChange = { bal.value = it}, modifier = textFieldModifier.weight(1f),
                    label = {Text(text = SportAlbumApplication.instance.getString(R.string.bal))})

            }

            Row {
                TextField(value = fis.value, onValueChange = { fis.value = it}, modifier = textFieldModifier.weight(1f),
                    label = {Text(text = SportAlbumApplication.instance.getString(R.string.fis))})

                TextField(value = vel.value, onValueChange = { vel.value = it}, modifier = textFieldModifier.weight(1f),
                    label = {Text(text = SportAlbumApplication.instance.getString(R.string.vel))})

            }

            Row {
                TextField(value = rig.value, onValueChange = { rig.value = it}, modifier = textFieldModifier.weight(1f),
                    label = {Text(text = SportAlbumApplication.instance.getString(R.string.rig))})

                TextField(value = por.value, onValueChange = { por.value = it}, modifier = textFieldModifier.weight(1f),
                    label = {Text(text = SportAlbumApplication.instance.getString(R.string.por))})
            }


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
                dashboardViewModel.updatePlayer(
                    PlayerModel(name.value.text,
                        PlayerHelper.roleFromString(role.value.text) ?: Enums.Role.PP,
                        PlayerHelper.genderFromString(gender.value.text),
                        team.value.text,
                        country.value.text,
                        birthyear.value.text,
                        value.value.text.toInt(),
                        valueleg.value.text.toInt(),
                        teamLegend.value.text,
                        if (national.value) 1 else 0,
                        if (nationalLegend.value) 1 else 0,
                        PlayerHelper.roleLineUpFromString(roleLineUp.value.text) ?: Enums.RoleLineUp.PTC,
                        att.value.text.toInt(),
                        dif.value.text.toInt(),
                        tec.value.text.toInt(),
                        dri.value.text.toInt(),
                        fin.value.text.toInt(),
                        bal.value.text.toInt(),
                        fis.value.text.toInt(),
                        vel.value.text.toInt(),
                        rig.value.text.toInt(),
                        por.value.text.toInt())
                )
                navController.popBackStack()
            }) {
                Text(text = SportAlbumApplication.instance.getString(R.string.update))
            }

        }




    }

}

