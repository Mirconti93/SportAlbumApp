package com.mircontapp.sportalbum.presentation.draw

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.mircontapp.sportalbum.presentation.commons.ShortListElement

@Composable
fun DrawScreen(navController: NavController) {
    val viewModel: DrawViewModel = hiltViewModel()

    val state = viewModel.state.collectAsState()

    val options = remember { viewModel.options.value }

    val entries = ArrayList<MutableState<TextFieldValue>>()

    Column {

        Text(text = SportAlbumApplication.instance.getString(R.string.sorting))

        state.value.beforeDraw.let { beforeDraw->

            if (beforeDraw) {
                repeat(8) {
                    val name = remember { mutableStateOf(TextFieldValue("")) }

                    AutoCompleteEditText(
                        value = name.value,
                        onValueChange = { name.value = it },
                        onOptionSelected = {},
                        suggestions = options
                    )

                    entries.add(name)
                }
            } else {
                state.value.drawModel.list.forEach {
                    CardText(it, Modifier.fillMaxWidth())
                    Spacer(Modifier.height(2.dp))
                }
            }

            Button(onClick = {
                if (beforeDraw) {
                    viewModel.onAction(DrawAction.Draw(
                        DrawModel(
                            hasPlots = false,
                            list = ArrayList<String>().also { list->
                                for (name in entries) {
                                    list.add(name.value.text)
                                }
                            }
                        )
                    ))
                } else {
                    navController.popBackStack()
                }
            }) { Text(text  = SportAlbumApplication.instance.getString(if (beforeDraw) R.string.draw else R.string.back)) }

        }

    }
}