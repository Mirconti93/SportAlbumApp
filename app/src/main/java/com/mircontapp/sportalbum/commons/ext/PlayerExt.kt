package com.mircontapp.sportalbum.commons.ext

import android.util.Log
import com.mirco.sportalbum.utils.Enums
import com.mirco.sportalbum.utils.Enums.MatchModule
import com.mirco.sportalbum.utils.Enums.RoleLineUp
import com.mircontapp.sportalbum.commons.PlayerHelper
import com.mircontapp.sportalbum.commons.PlayerHelper.Companion.getDefaultRoles
import com.mircontapp.sportalbum.data.database.Player
import com.mircontapp.sportalbum.domain.models.BupiPlayerModel
import com.mircontapp.sportalbum.domain.models.PlayerMatchModel
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.presentation.commons.OnEditClickHandler
import com.mircontapp.sportalbum.presentation.commons.OnPlayerClickHandler
import com.mircontapp.sportalbum.presentation.commons.ShortListItem


fun PlayerModel.toPlayerMatchModel(isLegend: Boolean): PlayerMatchModel {

    val style = this.style ?: Enums.PlayStyle.NORMAL
    val currentValue = (if (isLegend) valueleg else value) ?: 50
    return PlayerMatchModel(
        name = this.name,
        role = this.role,
        gender = this.gender ?: Enums.Gender.OTHER,
        team = this.team,
        country = this.country,
        birthyear = this.birthyear,
        value = this.value,
        valueleg = this.valueleg,
        teamLegend = this.teamLegend,
        national = this.national,
        nationalLegend = this.nationalLegend,
        roleLineUp = this.roleLineUp,
        style = style,
        att = PlayerHelper.getMatchValue(style.att, currentValue),
        dif = PlayerHelper.getMatchValue(style.dif, currentValue),
        tec = PlayerHelper.getMatchValue(style.tec, currentValue),
        dri = PlayerHelper.getMatchValue(style.dri, currentValue),
        fin = PlayerHelper.getMatchValue(style.fin, currentValue),
        bal = PlayerHelper.getMatchValue(style.bal, currentValue),
        fis = PlayerHelper.getMatchValue(style.fis, currentValue),
        vel = PlayerHelper.getMatchValue(style.vel, currentValue),
        rig = PlayerHelper.getMatchValue(style.rig, currentValue),
        por = PlayerHelper.getMatchValue(style.por, currentValue),
        roleMatch = this.roleLineUp,
        isAmmonito = false,
        isEspulso = false,
        energy = 100.0
    )
}

fun PlayerModel.entityFromPlayerModel() : Player {
    return Player(
        name = this.name,
        role = this.role.name,
        gender = this.gender?.name,
        team = this.team,
        country = this.country,
        birthyear = this.birthyear,
        value = this.value.toString(),
        valueleg = this.valueleg.toString(),
        teamLegend = this.teamLegend,
        national = this.national,
        nationalLegend = this.nationalLegend,
        roleLineUp = this.roleLineUp.name,
        style = this.style?.name
    )
}

fun PlayerModel.toShortItem(onPlayerClickHandler: OnPlayerClickHandler, onEditClickHandler: OnEditClickHandler) : ShortListItem {
    val playerModel = this
    return object : ShortListItem {
        override fun getTitle(): String {
            return playerModel.name
        }

        override fun getSubtitle(): String {
            return playerModel.team?: "Free"
        }

        override fun onItemClick() {
            onPlayerClickHandler.onPlayerClick(playerModel)
        }

        override fun onEditClick() {
            onEditClickHandler.onPlayerClick(playerModel)
        }

    }

}

fun List<PlayerModel>.sortPlayerByRole(): List<PlayerModel> {
    return this.sortedBy { it?.role }
}

fun List<PlayerModel>.getBestInRole(role: Enums.Role, isLegend: Boolean): PlayerModel? {
    var playerModel: PlayerModel? = null
    for (p in this) {
        if (role == p.role) {
            if (playerModel != null) {
                val pVal: Int = if (isLegend) p.valueleg?.toInt() ?: 0  else p.value?.toInt() ?: 0
                val playerModelVal: Int =
                    if (isLegend) playerModel.valueleg?.toInt() ?: 0 else playerModel.value?.toInt() ?: 0
                if (pVal > playerModelVal) {
                    playerModel = p
                }
            }
        }
    }
    if (playerModel == null) {
        playerModel = this.getBestPlayer(isLegend)
    }
    return playerModel
}



