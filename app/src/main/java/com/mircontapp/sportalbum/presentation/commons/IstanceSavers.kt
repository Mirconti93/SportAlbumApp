package com.mircontapp.sportalbum.presentation.commons

import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.ui.text.input.TextFieldValue

val EntrySaver = run {
    val nameKey = "Name"
    mapSaver (
        save = { mapOf(nameKey to it.text) },
        restore = { TextFieldValue(it[nameKey] as String) }
    )
}

val BooleanEntrySaver = run {
    val nameKey = "Name"
    mapSaver (
        save = { mapOf(nameKey to it) },
        restore = { it[nameKey] as Boolean }
    )
}