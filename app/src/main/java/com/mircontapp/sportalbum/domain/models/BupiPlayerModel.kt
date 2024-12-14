package com.mircontapp.sportalbum.domain.models

import com.mircontapp.sportalbum.data.database.BupiPlayer
import com.mircontapp.sportalbum.presentation.commons.ShortListElement

data class BupiPlayerModel(
    val name: String,
    var team: String,
    var country: String?,
    val role: Int?
)

fun BupiPlayerModel.entityFromBupiPlayer() : BupiPlayer {
    return BupiPlayer(this.name, this.team, this.country, this.role.toString())
}

fun BupiPlayerModel.toShortItem(onItemClick : () -> Unit) : ShortListElement {
    val player = this
    return object : ShortListElement {
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
