package com.mircontapp.sportalbum.commons.ext

import com.mirco.sportalbum.utils.Enums
import com.mirco.sportalbum.utils.Enums.RoleLineUp
import com.mircontapp.sportalbum.commons.AlbumHelper
import com.mircontapp.sportalbum.data.database.Player
import com.mircontapp.sportalbum.domain.models.PlayerMatchModel
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.presentation.commons.OnEditClickHandler
import com.mircontapp.sportalbum.presentation.commons.OnPlayerClickHandler
import com.mircontapp.sportalbum.presentation.commons.ShortListElement


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
        value = this.value ?: 50,
        valueleg = this.valueleg,
        teamLegend = this.teamLegend,
        national = this.national,
        nationalLegend = this.nationalLegend,
        roleLineUp = this.roleLineUp,
        style = style,
        att = AlbumHelper.getMatchValue(style.att, currentValue),
        dif = AlbumHelper.getMatchValue(style.dif, currentValue),
        tec = AlbumHelper.getMatchValue(style.tec, currentValue),
        dri = AlbumHelper.getMatchValue(style.dri, currentValue),
        fin = AlbumHelper.getMatchValue(style.fin, currentValue),
        bal = AlbumHelper.getMatchValue(style.bal, currentValue),
        fis = AlbumHelper.getMatchValue(style.fis, currentValue),
        vel = AlbumHelper.getMatchValue(style.vel, currentValue),
        rig = AlbumHelper.getMatchValue(style.rig, currentValue),
        por = AlbumHelper.getMatchValue(style.por, currentValue),
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

fun PlayerModel.toShortItem(onPlayerClickHandler: OnPlayerClickHandler, onEditClickHandler: OnEditClickHandler) : ShortListElement {
    val playerModel = this
    return object : ShortListElement {
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

fun String.roleFromString(): Enums.Role? {
    for (role in Enums.Role.values()) {
        if (this.equals(role.toString(), ignoreCase = true)) {
            return role
        }
    }
    return Enums.Role.PP
}

fun String.roleLineUpFromString(): RoleLineUp? {
    for (role in Enums.RoleLineUp.values()) {
        if (this.equals(role.toString(), ignoreCase = true)) {
            return role
        }
    }
    return RoleLineUp.PPM
}

fun String.genderFromString(): Enums.Gender? {
    for (gender in Enums.Gender.values()) {
        if (this.equals(gender.toString(), ignoreCase = true)) {
            return gender
        }
    }
    return Enums.Gender.OTHER
}

fun String.styleFromString(): Enums.PlayStyle? {
    for (style in Enums.PlayStyle.values()) {
        if (this.equals(style.toString(), ignoreCase = true)) {
            return style
        }
    }
    return Enums.PlayStyle.NORMAL
}






