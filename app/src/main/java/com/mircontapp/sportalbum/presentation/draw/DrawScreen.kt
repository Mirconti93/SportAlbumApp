package com.mircontapp.sportalbum.presentation.draw

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.domain.models.DrawModel
import com.mircontapp.sportalbum.presentation.album.ShortListItem
import com.mircontapp.sportalbum.presentation.commons.AutoCompleteEditText
import com.mircontapp.sportalbum.presentation.commons.CardText
import com.mircontapp.sportalbum.presentation.commons.CustomTextField
import com.mircontapp.sportalbum.presentation.commons.ShortListElement

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawScreen(navController: NavController) {
    val viewModel: DrawViewModel = hiltViewModel()

    val state = viewModel.state.collectAsState()

    val options = remember { viewModel.options.value }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Text(text = SportAlbumApplication.instance.getString(R.string.sorting), modifier = Modifier.padding(8.dp))

        state.value.drawPhase.let { drawPhase->

            when (drawPhase) {
                DrawPhase.INSERT -> {

                    val text = remember { mutableStateOf(TextFieldValue("")) }

                    TextField(
                        value = text.value,
                        onValueChange = { text.value = it },
                        label = { Text(SportAlbumApplication.getString(R.string.name)) }
                    )

                    state.value.drawModel.list.forEach {
                        CardText(it, Modifier.fillMaxWidth())
                        Spacer(Modifier.height(2.dp))
                    }

                    Button(onClick = {
                        viewModel.onAction(DrawAction.AddTeam(text.value.text))
                        text.value = TextFieldValue("")
                    }) { Text(text  = SportAlbumApplication.getString(R.string.add)) }

                }
                DrawPhase.RECAP -> {
                    Text(text = SportAlbumApplication.instance.getString(R.string.sortingRecap))
                    val list = state.value.drawModel.list
                    list.forEach {
                        CardText(it, Modifier.fillMaxWidth())
                        Spacer(Modifier.height(2.dp))
                    }
                    Text(text = SportAlbumApplication.instance.getString(R.string.sortingGo))
                    Button(onClick = {
                        viewModel.onAction(DrawAction.Draw(DrawModel(hasPlots = false, list = list)))
                    }) { Text(text  = SportAlbumApplication.getString(R.string.draw)) }

                }
                DrawPhase.DRAWN -> {
                    state.value.drawModel.list.forEach {
                        CardText(it, Modifier.fillMaxWidth())
                        Spacer(Modifier.height(2.dp))
                    }
                    Button(onClick = {
                        navController.popBackStack()
                    }) { Text(text  = SportAlbumApplication.getString(R.string.back)) }
                }
            }
        }

    }
}