package com.mircontapp.sportalbum.presentation.bupi

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.PlayerHelper
import com.mircontapp.sportalbum.commons.UIHelper
import com.mircontapp.sportalbum.commons.customTextEdit
import com.mircontapp.sportalbum.domain.models.BupiPlayerModel
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.presentation.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBupiPlayerScreen(navController: NavController, mainViewModel: MainViewModel) {
    val bupiViewModel: BupiViewModel = hiltViewModel()
    val player = remember {
        mainViewModel.selectedBupiPlayer.let {
            if (it != null) {
                bupiViewModel.updateType.value = Enums.UpdateType.UPDATE
                it
            } else {
                bupiViewModel.updateType.value = Enums.UpdateType.NEW
                PlayerHelper.buildBupiPlayerModel("Player")
            }
        }
    }

    val name = remember { mutableStateOf(TextFieldValue(player.name)) }
    val team = remember { mutableStateOf(TextFieldValue(player.team)) }
    val country = remember { mutableStateOf(TextFieldValue(player.country ?: "")) }
    val role = remember { mutableStateOf(TextFieldValue(player.role.toString())) }

    Row(modifier = Modifier.verticalScroll(rememberScrollState()).padding(8.dp)) {
        Column {

            BasicTextField(value = name.value, textStyle = UIHelper.getTextInEditStyle() , onValueChange = { name.value = it }, modifier = Modifier.customTextEdit())

            BasicTextField(value = team.value, textStyle = UIHelper.getTextInEditStyle() , onValueChange = { team.value = it }, modifier = Modifier.customTextEdit(),)

            BasicTextField(value = country.value, textStyle = UIHelper.getTextInEditStyle() , onValueChange = { country.value = it }, modifier = Modifier.customTextEdit(),)

            BasicTextField(value = role.value, textStyle = UIHelper.getTextInEditStyle() , onValueChange = { role.value = it }, modifier = Modifier.customTextEdit(),)

            Button(onClick = {
                bupiViewModel.updatePlayer(
                    BupiPlayerModel(
                        name.value.text,
                        team.value.text,
                        country.value.text,
                        role.value.text.toIntOrNull()
                    )
                )
                navController.popBackStack()
            }) {
                Text(text = SportAlbumApplication.instance.getString(R.string.update))
            }

        }

    }

}

