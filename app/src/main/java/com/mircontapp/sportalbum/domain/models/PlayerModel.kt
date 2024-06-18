package com.mircontapp.sportalbum.domain.models

import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.commons.PlayerHelper
import com.mircontapp.sportalbum.data.database.Player
import com.mircontapp.sportalbum.presentation.commons.OnEditClickHandler
import com.mircontapp.sportalbum.presentation.commons.OnPlayerClickHandler
import com.mircontapp.sportalbum.presentation.commons.ShortListItem

data class PlayerModel(
    val name: String,
    var role: Enums.Role,
    var gender: Enums.Gender?,
    var team: String?,
    val country: String?,
    val birthyear: String?,
    val value: Int?,
    val valueleg: Int?,
    val teamLegend: String?,
    val national: Int?,
    val nationalLegend: Int?,
    val roleLineUp: Enums.RoleLineUp,
    val style: Enums.PlayStyle?,
)

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
    return object :ShortListItem {
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


