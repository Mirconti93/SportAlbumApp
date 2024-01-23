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
import com.mircontapp.sportalbum.commons.TeamHelper
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel
import com.mircontapp.sportalbum.presentation.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPlayerScreen(navController: NavController, mainViewModel: MainViewModel) {
    val playerModel = remember {
        mainViewModel.playerModel ?: PlayerModel("Player", Enums.Role.PP, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null )
    }
    
    val dashboardViewModel: DashboardViewModel = hiltViewModel()
    
    val textFieldModifier: Modifier = Modifier.padding(4.dp)

    val name = remember { mutableStateOf(TextFieldValue(playerModel.name)) }
    val role = remember { mutableStateOf(TextFieldValue(playerModel.role.name)) }
    val gender = remember { mutableStateOf(TextFieldValue(playerModel.gender?.name ?: "")) }
    val team = remember { mutableStateOf(TextFieldValue(playerModel.team ?: "")) }
    val country = remember { mutableStateOf(TextFieldValue(playerModel.country ?: "")) }
    val birthyear = remember { mutableStateOf(TextFieldValue(playerModel.birthyear ?: "")) }
    val value = remember { mutableStateOf(TextFieldValue(playerModel.value ?: "")) }
    val valueleg = remember { mutableStateOf(TextFieldValue(playerModel.valueleg ?: "")) }
    val teamLegend = remember { mutableStateOf(TextFieldValue(playerModel.teamLegend ?: "")) }
    val national = remember { mutableStateOf(playerModel.national == 1) }
    val nationalLegend = remember { mutableStateOf(playerModel.nationalLegend == 1) }
    var roleLineUp = rememberSaveable{ mutableStateOf(playerModel.roleLineUp) }

    Row(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Column {

            TextField(value = name.value, onValueChange = { name.value = it }, modifier = textFieldModifier,
                label = { Text(text = SportAlbumApplication.instance.getString(R.string.name)) })

            TextField(value = role.value, onValueChange = { role.value = it }, modifier = textFieldModifier,
                label = { Text(text = SportAlbumApplication.instance.getString(R.string.role)) })

            TextField(value = gender.value, onValueChange = { gender.value = it }, modifier = textFieldModifier,
                label = { Text(text = SportAlbumApplication.instance.getString(R.string.gender)) })

            TextField(value = team.value, onValueChange = { team.value = it }, modifier = textFieldModifier,
                label = { Text(text = SportAlbumApplication.instance.getString(R.string.team)) })

            TextField(value = country.value, onValueChange = { country.value = it }, modifier = textFieldModifier,
                label = { Text(text = SportAlbumApplication.instance.getString(R.string.country)) })

        }

        Column {
            TextField(value = birthyear.value, onValueChange = { birthyear.value = it}, modifier = textFieldModifier,
                label = {Text(text = SportAlbumApplication.instance.getString(R.string.birthyear))})

            TextField(value = value.value, onValueChange = { value.value = it}, modifier = textFieldModifier,
                label = {Text(text = SportAlbumApplication.instance.getString(R.string.value))})

            TextField(value = valueleg.value, onValueChange = { valueleg.value = it}, modifier = textFieldModifier,
                label = {Text(text = SportAlbumApplication.instance.getString(R.string.valueleg))})

            TextField(value = teamLegend.value, onValueChange = { teamLegend.value = it}, modifier = textFieldModifier,
                label = {Text(text = SportAlbumApplication.instance.getString(R.string.isClubLegend))})

        }
        Column() {
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
                /*dashboardViewModel.updateTeam(
                    PlayerModel(playerModel.name, Enums.Role.PP, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null )
                )*/
                Toast.makeText(SportAlbumApplication.instance, SportAlbumApplication.instance.getString(R.string.dataUpdated), Toast.LENGTH_LONG)
            }) {
                Text(text = SportAlbumApplication.instance.getString(R.string.add))
            }


        }
    }

}

