package com.mircontapp.sportalbum.presentation.commons

interface ShortListElement {
    fun getTitle(): String
    fun getSubtitle(): String

    fun onItemClick()
    fun onEditClick()
}