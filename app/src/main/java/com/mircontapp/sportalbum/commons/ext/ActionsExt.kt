package com.mircontapp.sportalbum.commons.ext

import android.util.Log
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.domain.models.ActionModel
import com.mircontapp.sportalbum.domain.models.MatchModel
import com.mircontapp.sportalbum.domain.models.PlayerMatchModel

fun MatchModel.azione(): ActionModel {
    var protagonistaA = ""
    var attA = -1.0

    val attackers = if (possesso == Enums.TeamPosition.HOME) playersHome else playersAway
    val defenders = if (possesso == Enums.TeamPosition.HOME) playersAway else playersHome

    for (attacker in attackers) {
        if (attacker.partecipa(attacker.roleMatch.getPartAtt())) {
            val action = when (fase) {
                Enums.Fase.ATTACCO -> attacker.attacco()
                Enums.Fase.CONCLUSIONE -> attacker.tiro()
                else -> attacker.palleggio()
            }
           attacker.attacco()
            Log.i("BUPIAZIONE:", "${fase} ${attacker.name} $action")
            if (action > attA) {
                attA = action
                protagonistaA = attacker.name
            }
        }
    }

    var protagonistaD = ""
    var difD = -1.0
    for (defender in defenders) {
        if (defender.partecipa(defender.roleMatch.getPartDif())) {
            val action = when (fase) {
                Enums.Fase.ATTACCO -> defender.difesa()
                Enums.Fase.CONCLUSIONE -> defender.respinta()
                else -> defender.pressing()
            }
            Log.i("BUPIAZIONE:", "${fase} ${defender.name} $action")
            if (action > difD) {
                difD = action
                protagonistaD = defender.name
            }
        }
    }

    return ActionModel(protagonistaA, attA, protagonistaD, difD)
}

fun PlayerMatchModel.attacco(): Double {
    val pot = att / 4.0 + dri / 4.0 + tec / 4.0 + vel / 4.0
    val fixed = value?.toDouble() ?: 0.0
    return fixed * 0.25 + Math.random() * pot * 0.75
}

fun PlayerMatchModel.difesa(): Double {
    val pot = dif / 4.0 + bal / 4.0 + fis / 4.0 + vel / 4.0
    val fixed = value?.toDouble() ?: 0.0
    return fixed * 0.25 + pot * 0.25 + Math.random() * pot * 0.5
}

fun PlayerMatchModel.palleggio(): Double {
    val pot = tec / 2.0 + dri / 4.0 + vel / 4.0
    val fixed = value?.toDouble() ?: 0.0
    return fixed / 2.0 + Math.random() * pot / 2.0
}

fun PlayerMatchModel.pressing(): Double {
    val pot = dif / 2.0 + bal / 4.0 + vel / 4.0
    val fixed = value?.toDouble() ?: 0.0
    return fixed / 2.0 + Math.random() * pot / 2.0
}

fun PlayerMatchModel.tiro(): Double {
    val pot = fin / 2.0 + att / 4.0 + vel / 4.0
    val fixed = value?.toDouble() ?: 0.0
    return fixed * 0.25 + Math.random() * pot  * 0.75
}

fun PlayerMatchModel.respinta(): Double {
    val pot = dif / 4.0 + bal / 4.0 + fis / 4.0 + vel / 4.0
    return pot * 0.25 + Math.random() * pot  * 0.75
}

fun PlayerMatchModel.parata(difPower: Double): Double {
    val pot = por / 2.0 + bal / 4.0 + dif / 4.0
    val fixed = value ?: 0
    return fixed  * 0.25 + pot * 0.25 + Math.random() * pot * 0.5 + difPower
}

