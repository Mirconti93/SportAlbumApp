package com.mircontapp.sportalbum.presentation.dashboard

import android.text.Editable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.TeamModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTeamScreen() {
    val viewModel: DashboardViewModel = hiltViewModel()

    val teamModel = viewModel.team.value ?: TeamModel("New", "", "", "", "", "", "", Enums.Area.OTHER, Enums.Area.OTHER,false,"", "", Enums.MatchModule.M442)

    val name = remember { mutableStateOf(TextFieldValue(teamModel.name)) }
    TextField(value = name.value, onValueChange = { name.value = it},
        label = {Text(text = "Name")})

    val city = remember { mutableStateOf(TextFieldValue(teamModel.city?: "")) }
    TextField(value = city.value, onValueChange = { city.value = it},
        label = {Text(text = "City")})

    val country = remember { mutableStateOf(TextFieldValue(teamModel.country ?: "")) }
    TextField(value = country.value, onValueChange = { country.value = it},
        label = {Text(text = "Country")})

    val type = remember { mutableStateOf(TextFieldValue(teamModel.type ?: "")) }
    TextField(value = type.value, onValueChange = { type.value = it},
        label = {Text(text = "Type")})




}
