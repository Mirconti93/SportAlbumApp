package com.mircontapp.sportalbum.presentation.commons

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun SearchBar(
    onSearchTermChanged: (String) -> Unit
) {
   /* var searchQuery by remember { mutableStateOf("") }

    Row {
        Icon(Icons.Filled.Search, contentDescription = "Search")
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                onSearchTermChanged(it)
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder =
        )
    }*/
}