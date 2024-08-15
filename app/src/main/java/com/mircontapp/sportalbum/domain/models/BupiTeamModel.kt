package com.mircontapp.sportalbum.domain.models

import com.mircontapp.sportalbum.data.database.BupiTeam
import com.mircontapp.sportalbum.presentation.commons.ShortListItem

data class BupiTeamModel (
    val name: String,
    val area: String?
)

fun BupiTeamModel.entityFromBupiTeam() : BupiTeam {
    return BupiTeam(this.name, this.area ?: "Free")
}

fun BupiTeamModel.toShortItem(onItemClick : () -> Unit) : ShortListItem {
    val team = this
    return object : ShortListItem {
        override fun getTitle(): String {
            return team.name
        }

        override fun getSubtitle(): String {
            return team.area ?: "Free"
        }

        override fun onItemClick() {
            onItemClick()
        }

        override fun onEditClick() {
            onItemClick()
        }

    }

}
