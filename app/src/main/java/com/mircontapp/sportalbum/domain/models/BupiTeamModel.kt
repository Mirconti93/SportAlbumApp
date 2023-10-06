package com.mircontapp.sportalbum.domain.models
data class BupiTeamModel (
    val name: String,
    val area: String
) : GenericModel() {

    override fun getTitle(): String? {
        return name
    }

    override fun getSubtitle(): String? {
        return area
    }
}