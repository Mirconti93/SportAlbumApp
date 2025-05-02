package com.mircontapp.sportalbum.commons.ext

import android.util.Log
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.ActionModel
import com.mircontapp.sportalbum.domain.models.MatchModel
import com.mircontapp.sportalbum.domain.models.PlayerMatchModel

fun MatchModel.genericAction(players: List<PlayerMatchModel>, faseAction: (player: PlayerMatchModel)->Double): ActionModel {
    var protagonista = ""
    var att = -1.0

    val attackers = if (possesso == Enums.TeamPosition.HOME) playersHome else playersAway

    for (attacker in attackers) {
        if (attacker.partecipa(attacker.roleMatch.getPartAtt())) {
            val action = faseAction(attacker)
            Log.i("BUPIAZIONE:", "${fase} ${attacker.name} $action")
            if (action > att) {
                att = action
                protagonista = attacker.name
            }
        }
    }

    return ActionModel(protagonista, att)
}

fun PlayerMatchModel.attacco(): Double {
    val pot = att / 4.0 + dri / 4.0 + tec / 4.0 + vel / 4.0
    return value * 0.25 + Math.random() * pot * 0.75
}

fun PlayerMatchModel.difesa(): Double {
    val pot = dif / 4.0 + bal / 4.0 + fis / 4.0 + vel / 4.0
    return value * 0.25 + pot * 0.25 + Math.random() * pot * 0.5
}

fun PlayerMatchModel.palleggio(): Double {
    val pot = tec / 2.0 + dri / 4.0 + vel / 4.0
    return value / 2.0 + Math.random() * pot / 2.0
}

fun PlayerMatchModel.pressing(): Double {
    val pot = dif / 2.0 + bal / 4.0 + vel / 4.0
    return value / 2.0 + Math.random() * pot / 2.0
}

fun PlayerMatchModel.tiro(): Double {
    val pot = fin / 2.0 + att / 4.0 + vel / 4.0
    return value * 0.25 + Math.random() * pot  * 0.75
}

fun PlayerMatchModel.respinta(): Double {
    val pot = dif / 4.0 + bal / 4.0 + fis / 4.0 + vel / 4.0
    return pot * 0.25 + Math.random() * pot  * 0.75
}

fun PlayerMatchModel.parata(difPower: Double): Double {
    val pot = por / 2.0 + bal / 4.0 + dif / 4.0
    return value  * 0.25 + pot * 0.25 + Math.random() * pot * 0.5 + difPower
}

fun PlayerMatchModel.punizioneDiretta(): Double {
    return value * 0.5 + Math.random() * rig * 0.5
}

fun MatchModel.setAmmonito(name: String): PlayerMatchModel? {
    var isEspulso = false
    val defenders = if (possesso == Enums.TeamPosition.HOME) playersAway else playersHome
    defenders.find { it.name == name }?.let {
        if (it.isAmmonito) {
            it.isEspulso = true
        } else {
            it.isAmmonito = true
        }
        return it
    }
    return null
}

fun MatchModel.setEspulso(name: String) {
    val defenders = if (possesso == Enums.TeamPosition.HOME) playersAway else playersHome
    defenders.find { it.name == name }?.isEspulso = true
}

