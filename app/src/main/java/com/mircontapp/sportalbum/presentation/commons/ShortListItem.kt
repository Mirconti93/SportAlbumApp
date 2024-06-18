package com.mircontapp.sportalbum.presentation.commons

interface ShortListItem {
    fun getTitle(): String
    fun getSubtitle(): String

    fun onItemClick()
    fun onEditClick()
}