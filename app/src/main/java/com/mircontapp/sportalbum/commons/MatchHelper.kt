package com.mircontapp.sportalbum.commons

import com.mircontapp.sportalbum.domain.models.PlayerMatchModel

class MatchHelper {
    companion object {
        fun findTiratore(playerModels: List<PlayerMatchModel>): Int {
            var index = 0
            var mRig = 0.0
            for (i in playerModels.indices) {
                val p: PlayerMatchModel = playerModels[i]
                if (!p.isEspulso && p.rig >= mRig) {
                    index = i
                    mRig = p.rig.toDouble()
                }
            }
            return index
        }

        fun findGoalkeeper(playerModels: List<PlayerMatchModel>): PlayerMatchModel {
            return playerModels.find { PlayerHelper.isPortiere(it)} ?: playerModels.get(0)
        }

        fun partecipa(playerMatchModel: PlayerMatchModel, partRole: Double): Boolean {
            if (playerMatchModel.isEspulso) return false
            return (Math.random()*100.0).let {
                return (it < playerMatchModel.energy && it > partRole)
            }
        }
    }
}