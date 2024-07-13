package com.mircontapp.sportalbum.domain.models

import com.mircontapp.sportalbum.data.database.BupiPlayer
import com.mircontapp.sportalbum.presentation.commons.OnEditClickHandler
import com.mircontapp.sportalbum.presentation.commons.OnPlayerClickHandler
import com.mircontapp.sportalbum.presentation.commons.ShortListItem

data class BupiPlayerModel(
    val name: String,
    var team: String,
    val role: Int?
)

fun BupiPlayerModel.entityFromBupiPlayer() : BupiPlayer {
    return BupiPlayer(this.name, this.team, this.role.toString())
}

fun BupiPlayerModel.toShortItem(onItemClick : () -> Unit) : ShortListItem {
    val player = this
    return object : ShortListItem {
        override fun getTitle(): String {
            return player.name
        }

        override fun getSubtitle(): String {
            return player.team
        }

        override fun onItemClick() {
            onItemClick()
        }

        override fun onEditClick() {
            onItemClick()
        }

    }

}
