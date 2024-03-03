import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.domain.models.CommentModel
import com.mircontapp.sportalbum.domain.models.MatchModel
import com.mircontapp.sportalbum.domain.models.PlayerMatchModel

class MatchUC() {

    fun centrocampo(matchModel: MatchModel): MatchModel {
        var protagonistaA = ""
        var cenA = -1.0
        var pot = 0.0
        var fixed = 0.0
        var dado = 0.0

        val attackers = if (matchModel.possesso == Enums.Possesso.HOME) matchModel.playersHome else matchModel.playersAway
        val defenders = if (matchModel.possesso == Enums.Possesso.HOME) matchModel.playersAway else matchModel.playersHome

        //centrocampo azione offensiva
        for (attacker in attackers) {
            var partecipa = Math.random() * 100.0
            if (partecipa < matchModel.minute / 5.0 - attacker.fis / 10.0
            ) {
                partecipa = 0.0
            }
            if (partecipa > attacker.roleMatch.getPartCen() && !attacker.isAmmonito) {
                pot = attacker.tec / 2.0 + attacker.dri / 4 + attacker.vel / 4
                fixed = if (matchModel.isLegend) attacker.valueleg?.toDouble() ?: 0.0 else attacker.value?.toDouble() ?: 0.0
                dado = fixed / 2 + Math.random() * pot / 2
                if (dado >= cenA) {
                    cenA = dado
                    protagonistaA = attacker.name
                }
            }
        }

        //centrocampo azione difensiva
        var protagonistaD = ""
        var cenD = -1.0
        for (defender in defenders) {
            var partecipa = Math.random() * 100.0
            if (partecipa < matchModel.minute * 2 - defender.fis) {
                partecipa = 0.0
            }
            if (partecipa > defender.roleLineUp.getPartCen() && !defender.isEspulso) {
                pot = defender.dif / 2.0 + defender.bal / 4 + defender.vel / 4
                fixed = if (matchModel.isLegend) defender.valueleg?.toDouble() ?: 0.0 else defender.value?.toDouble() ?: 0.0
                dado = fixed / 2 + Math.random() * pot / 2
                if (dado > cenD) {
                    cenD = dado
                    protagonistaD = defender.name
                }
            }
        }

        val context = SportAlbumApplication.instance.applicationContext
        var messaggio = ""
        val diff = cenD - cenA

        //vince la squadra attaccante
        if (diff < 0) {
            matchModel.fase = Enums.Fase.ATTACCO
            matchModel.evento = Enums.Evento.NONE
            matchModel.protagonista = protagonistaA
            messaggio = if (diff > -8) {
                String.format(context.getString(R.string.telecronacaCen1), protagonistaA)
            } else if (diff > -15) {
                String.format(context.getString(R.string.telecronacaCen2), protagonistaA)
            } else {
                String.format(context.getString(R.string.telecronacaCen3), protagonistaA)
            }
            //calcio di punizione
        } else if (diff >= 0 && diff < 2) {
            matchModel.fase = Enums.Fase.PUNIZIONE
            if (diff >= 1.6 && diff < 1.7) {
                matchModel.evento = Enums.Evento.ESPULSIONE
                messaggio = String.format(context.getString(R.string.telecronacaEsp), protagonistaD)
            } else if (diff >= 1.7 && diff < 2) {
                matchModel.evento = Enums.Evento.AMMONIZIONE
                messaggio = String.format(context.getString(R.string.telecronacaAmm), protagonistaD)
            }
            matchModel.protagonista =protagonistaA
            matchModel.coprotagonista = protagonistaD
            messaggio = messaggio + " " + String.format(
                context.getString(R.string.telecronacaPun),
                protagonistaD
            )
            //vince la squadra difendente
        } else {
            matchModel.fase = Enums.Fase.CENTROCAMPO
            matchModel.evento = Enums.Evento.RECUPERO
            matchModel.protagonista = protagonistaD
            matchModel.possesso = if (matchModel.possesso === Enums.Possesso.HOME) Enums.Possesso.AWAY else Enums.Possesso.HOME
            messaggio = if (diff > 15) {
                String.format(context.getString(R.string.telecronacaBal3), protagonistaD)
            } else if (diff > 8) {
                String.format(context.getString(R.string.telecronacaBal2), protagonistaD)
            } else {
                String.format(context.getString(R.string.telecronacaBal1), protagonistaD)
            }
        }

        matchModel.comment.add(CommentModel(messaggio, matchModel.minute, matchModel.possesso))
        return matchModel
    }
}

//package com.mircontapp.sportalbum.domain.usecases
//
//import android.content.Context
//import com.mirco.sportalbum.R
//import com.mirco.sportalbum.utils.Enums
//import com.mircontapp.sportalbum.SportAlbumApplication
//import com.mircontapp.sportalbum.domain.models.MatchModel
//import com.mircontapp.sportalbum.domain.models.PlayerModel
//
//object MatchUC {
//    var app: SportAlbumApplication = SportAlbumApplication.instance
//    fun centrocampo(matchManager: MatchManager): MatchModel {
//        val matchModel: MatchModel = matchManager.getMatchModel()
//        var protagonistaA = ""
//        var cenA = -1.0
//        var pot = 0.0
//        var fix = 0.0
//        var dado = 0.0
//
//        //centrocampo azione offensiva
//        for (attacker in matchManager.getAttackers()) {
//            var partecipa = Math.random() * 100.0
//            if (partecipa < matchModel.getMinuto() / 5.0 - attacker.getPlayerStatsModel()
//                    .getFis() / 10.0
//            ) {
//                partecipa = 0.0
//            }
//            if (partecipa > attacker.getRoleLineUp().getPartCen() && !attacker.isEspuslo()) {
//                val attStats: PlayerStatsModel = attacker.getPlayerStatsModel()
//                pot = attStats.getTec() / 2 + attStats.getDri() / 4 + attStats.getVel() / 4
//                fix =
//                    if (matchManager.getLegend()) attacker.getValueLegend() else attacker.getValue()
//                dado = fix / 2 + Math.random() * pot / 2
//                if (dado > cenA) {
//                    cenA = dado
//                    protagonistaA = attacker.getName()
//                }
//            }
//        }
//        if (cenA < 0) {
//            val pl = Math.random() * 10.9
//            val attacker: PlayerModel = matchManager.getAttackers().get(pl.toInt())
//            val attStats: PlayerStatsModel = attacker.getPlayerStatsModel()
//            pot = attStats.getTec() / 2 + attStats.getDri() / 4 + attStats.getVel() / 4
//            dado = fix / 2 + Math.random() * pot / 2
//            cenA = dado
//            protagonistaA = attacker.getName()
//        }
//
//        //centrocampo azione difensiva
//        var protagonistaD = ""
//        var cenD = -1.0
//        for (defender in matchManager.getDefenders()) {
//            var partecipa = Math.random() * 100.0
//            if (partecipa < matchModel.getMinuto() * 2 - defender.getPlayerStatsModel().getFis()) {
//                partecipa = 0.0
//            }
//            if (partecipa > defender.getRoleLineUp().getPartCen() && !defender.isEspuslo()) {
//                val difStats: PlayerStatsModel = defender.getPlayerStatsModel()
//                pot = difStats.getFis() / 2 + difStats.getBal() / 4 + difStats.getVel() / 4
//                fix =
//                    if (matchManager.getLegend()) defender.getValueLegend() else defender.getValue()
//                dado = fix / 2 + Math.random() * pot / 2
//                if (dado > cenD) {
//                    cenD = dado
//                    protagonistaD = defender.getName()
//                }
//            }
//        }
//        if (cenD < 0) {
//            val pl = Math.random() * 10.9
//            val defender: PlayerModel = matchManager.getDefenders().get(pl.toInt())
//            val difStats: PlayerStatsModel = defender.getPlayerStatsModel()
//            pot = difStats.getFis() / 2 + difStats.getBal() / 4 + difStats.getVel() / 4
//            fix = if (matchManager.getLegend()) defender.getValueLegend() else defender.getValue()
//            dado = fix / 2 + Math.random() * pot / 2
//            cenD = dado
//            protagonistaD = defender.getName()
//        }
//        val context: Context = app.getBaseContext()
//        var messaggio = ""
//        val diff = cenD - cenA
//
//        //vince la squadra attaccante
//        if (diff < 0) {
//            matchModel.setFase(Enums.Fase.ATTACCO)
//            matchModel.setEvento(Enums.Evento.NONE)
//            matchModel.setProtagonista(protagonistaA)
//            matchModel.setAzione(cenA.toInt())
//            messaggio = if (diff > -8) {
//                String.format(context.getString(R.string.telecronacaCen1), protagonistaA)
//            } else if (diff > -15) {
//                String.format(context.getString(R.string.telecronacaCen2), protagonistaA)
//            } else {
//                String.format(context.getString(R.string.telecronacaCen3), protagonistaA)
//            }
//            //calcio di punizione
//        } else if (diff >= 0 && diff < 2) {
//            matchModel.setFase(Enums.Fase.PUNIZIONE)
//            if (diff >= 1.6 && diff < 1.7) {
//                matchModel.setEvento(Enums.Evento.ESPULSIONE)
//                messaggio = String.format(context.getString(R.string.telecronacaEsp), protagonistaD)
//            } else if (diff >= 1.7 && diff < 2) {
//                matchModel.setEvento(Enums.Evento.AMMONIZIONE)
//                messaggio = String.format(context.getString(R.string.telecronacaAmm), protagonistaD)
//            }
//            matchModel.setProtagonista(protagonistaA)
//            matchModel.setCoprotagonista(protagonistaD)
//            matchModel.setAzione(cenA.toInt())
//            messaggio = messaggio + " " + String.format(
//                context.getString(R.string.telecronacaPun),
//                protagonistaD
//            )
//            //vince la squadra difendente
//        } else {
//            matchModel.setFase(Enums.Fase.CENTROCAMPO)
//            matchModel.setEvento(Enums.Evento.RECUPERO)
//            matchModel.setProtagonista(protagonistaD)
//            matchModel.setAzione(cenA.toInt())
//            matchModel.setPossesso(if (matchModel.getPossesso() === Enums.Possesso.HOME) Enums.Possesso.AWAY else Enums.Possesso.HOME)
//            messaggio = if (diff > 15) {
//                String.format(context.getString(R.string.telecronacaBal3), protagonistaD)
//            } else if (diff > 8) {
//                String.format(context.getString(R.string.telecronacaBal2), protagonistaD)
//            } else {
//                String.format(context.getString(R.string.telecronacaBal1), protagonistaD)
//            }
//        }
//        matchModel.setStato(matchModel.getPossesso().ordinal())
//        matchModel.setMessaggio(messaggio ?: protagonistaA)
//        return matchModel
//    }
//
//    fun attacco(matchManager: MatchManager): MatchModel {
//        val matchModel: MatchModel = matchManager.getMatchModel()
//        var protagonistaA = ""
//        var attA = -1.0
//        var pot = 0.0
//        var fix = 0.0
//        var dado = 0.0
//        for (attacker in matchManager.getAttackers()) {
//            val partecipa = Math.random() * 100.0
//            if (partecipa > attacker.getRoleLineUp().getPartAtt() && !attacker.isEspuslo()) {
//                val attStats: PlayerStatsModel = attacker.getPlayerStatsModel()
//                pot =
//                    attStats.getAtt() / 4 + attStats.getDri() / 4 + attStats.getTec() / 4 + attStats.getVel() / 4
//                fix =
//                    if (matchManager.getLegend()) attacker.getValueLegend() else attacker.getValue()
//                dado = fix / 4 + Math.random() * pot / 4 * 3
//                if (dado > attA) {
//                    attA = dado
//                    protagonistaA = attacker.getName()
//                }
//            }
//        }
//        if (attA < 0) {
//            val pl = Math.random() * 10.9
//            val attacker: PlayerModel = matchManager.getAttackers().get(pl.toInt())
//            val attStats: PlayerStatsModel = attacker.getPlayerStatsModel()
//            pot =
//                attStats.getAtt() / 4 + attStats.getDri() / 4 + attStats.getTec() / 4 + attStats.getVel() / 4
//            fix = if (matchManager.getLegend()) attacker.getValueLegend() else attacker.getValue()
//            dado = fix / 4 + Math.random() * pot / 4 * 3
//            attA = dado
//            protagonistaA = attacker.getName()
//        }
//        var protagonistaD = ""
//        var difD = -1.0
//        for (defender in matchManager.getDefenders()) {
//            var partecipa = Math.random() * 100.0
//            if (partecipa < matchModel.getMinuto() / 10.0) {
//                partecipa = 0.0
//            }
//            if (partecipa > defender.getRoleLineUp().getPartCen() && !defender.isEspuslo()) {
//                val difStats: PlayerStatsModel = defender.getPlayerStatsModel()
//                pot =
//                    difStats.getDif() / 4 + difStats.getBal() / 4 + difStats.getFis() / 4 + difStats.getVel() / 4
//                fix =
//                    if (matchManager.getLegend()) defender.getValueLegend() else defender.getValue()
//                dado = fix / 4 + Math.random() * pot / 4 * 3
//                if (dado > difD) {
//                    difD = dado
//                    protagonistaD = defender.getName()
//                }
//            }
//        }
//        if (difD < 0) {
//            val pl = Math.random() * 10.9
//            val defender: PlayerModel = matchManager.getDefenders().get(pl.toInt())
//            val difStats: PlayerStatsModel = defender.getPlayerStatsModel()
//            pot =
//                difStats.getDif() / 4 + difStats.getBal() / 4 + difStats.getFis() / 4 + difStats.getVel() / 4
//            fix = if (matchManager.getLegend()) defender.getValueLegend() else defender.getValue()
//            dado = fix / 4 + Math.random() * pot / 4 * 3
//            difD = dado
//            protagonistaD = defender.getName()
//        }
//        val context: Context = app.getBaseContext()
//        var messaggio = ""
//        val diff = difD - attA
//
//        //vince la squadra attaccante
//        if (diff < 0) {
//            matchModel.setFase(Enums.Fase.CONCLUSIONE)
//            matchModel.setEvento(Enums.Evento.NONE)
//            matchModel.setProtagonista(protagonistaA)
//            matchModel.setAzione(attA.toInt())
//            messaggio = if (diff < 10) {
//                String.format(context.getString(R.string.telecronacaAtt1), protagonistaA)
//            } else {
//                String.format(context.getString(R.string.telecronacaAtt2), protagonistaA)
//            }
//            //punizione
//        } else if (diff >= 0 && diff < 0.4) {
//            matchModel.setFase(Enums.Fase.PUNIZIONE)
//            if (diff >= 0.3 && diff < 0.32) {
//                matchModel.setEvento(Enums.Evento.ESPULSIONE)
//                messaggio = String.format(context.getString(R.string.telecronacaEsp), protagonistaD)
//            } else if (diff >= 0.32 && diff < 0.4) {
//                matchModel.setEvento(Enums.Evento.AMMONIZIONE)
//                messaggio = String.format(context.getString(R.string.telecronacaAmm), protagonistaD)
//            }
//            matchModel.setProtagonista(protagonistaA)
//            matchModel.setCoprotagonista(protagonistaD)
//            matchModel.setAzione(attA.toInt())
//            messaggio = messaggio + " " + String.format(
//                context.getString(R.string.telecronacaPun),
//                protagonistaD
//            )
//            //calcio di rigore
//        } else if (diff >= 0.4 && diff < 0.5) {
//            matchModel.setFase(Enums.Fase.RIGORE)
//            if (diff >= 0.45 && diff < 0.47) {
//                matchModel.setEvento(Enums.Evento.ESPULSIONE)
//                messaggio = String.format(context.getString(R.string.telecronacaEsp), protagonistaD)
//            } else if (diff >= 0.47 && diff < 0.5) {
//                matchModel.setEvento(Enums.Evento.AMMONIZIONE)
//                messaggio = String.format(context.getString(R.string.telecronacaAmm), protagonistaD)
//            }
//            matchModel.setProtagonista(protagonistaA)
//            matchModel.setCoprotagonista(protagonistaD)
//            matchModel.setAzione(attA.toInt())
//            messaggio =
//                messaggio + String.format(context.getString(R.string.telecronacaRig), protagonistaA)
//            //vince la squadra difendente
//        } else {
//            matchModel.setFase(Enums.Fase.CENTROCAMPO)
//            matchModel.setEvento(Enums.Evento.NONE)
//            matchModel.setProtagonista(protagonistaD)
//            matchModel.setAzione(attA.toInt())
//            matchModel.setPossesso(if (matchModel.getPossesso() === Enums.Possesso.HOME) Enums.Possesso.AWAY else Enums.Possesso.HOME)
//            messaggio = if (diff < 5) {
//                String.format(context.getString(R.string.telecronacaDif1), protagonistaD)
//            } else if (diff < 10) {
//                String.format(context.getString(R.string.telecronacaDif2), protagonistaD)
//            } else {
//                String.format(context.getString(R.string.telecronacaDif3), protagonistaD)
//            }
//        }
//        matchModel.setStato(matchModel.getPossesso().ordinal())
//        matchModel.setMessaggio(messaggio ?: protagonistaA)
//        return matchModel
//    }
//
//    fun finalizzazione(matchManager: MatchManager): MatchModel {
//        val matchModel: MatchModel = matchManager.getMatchModel()
//        var protagonistaA = ""
//        var goleador: PlayerModel? = null
//        var finA = -1.0
//        var pot = 0.0
//        var fix = 0.0
//        var dado = 0.0
//        for (attacker in matchManager.getAttackers()) {
//            var partecipa = Math.random() * 100.0
//            if (partecipa < matchModel.getMinuto() / 10.0) {
//                partecipa = 0.0
//            }
//            if (partecipa > attacker.getRoleLineUp().getPartfin() && !attacker.isEspuslo()) {
//                val attStats: PlayerStatsModel = attacker.getPlayerStatsModel()
//                pot = attStats.getFin() / 2 + attStats.getAtt() / 4 + attStats.getVel() / 4
//                fix =
//                    if (matchManager.getLegend()) attacker.getValueLegend() else attacker.getValue()
//                dado = Math.random() * fix / 2 + Math.random() * pot / 2
//                if (dado > finA) {
//                    finA = dado
//                    protagonistaA = attacker.getName()
//                    goleador = attacker
//                }
//            }
//        }
//        var difPower = 1.0
//        var part = 0.0
//        for (defender in matchManager.getDefenders()) {
//            val partecipa = Math.random() * 100.0
//            if (defender.getRole() !== Enums.Role.PT && partecipa > defender.getRoleLineUp()
//                    .getPartCen() && !defender.isEspuslo()
//            ) {
//                val difStats: PlayerStatsModel = defender.getPlayerStatsModel()
//                pot =
//                    difStats.getDif() / 4 + difStats.getBal() / 4 + difStats.getFis() / 4 + difStats.getVel() / 4
//                dado = Math.random() * pot
//                difPower += dado
//                part++
//            }
//        }
//        if (part > 0) {
//            difPower = difPower / part / 2
//        }
//        val portiere: PlayerModel = matchManager.getDefenders().get(0)
//        pot = portiere.getPlayerStatsModel().getPor() / 2 + portiere.getPlayerStatsModel()
//            .getBal() / 4 + portiere.getPlayerStatsModel().getDif() / 4
//        fix = if (matchManager.getLegend()) portiere.getValueLegend() else portiere.getValue()
//        val parata = fix / 2 + Math.random() * pot / 2 + difPower
//        val context: Context = app.getBaseContext()
//        var messaggio = ""
//        val diff = parata - finA
//        matchModel.setStato(matchModel.getPossesso().ordinal())
//
//        //segna l'attaccante
//        if (diff < 0) {
//            matchModel.setFase(Enums.Fase.CENTROCAMPO)
//            matchModel.setGol(true)
//            if (matchModel.getPossesso() === Enums.Possesso.HOME) {
//                matchModel.setScoreHome(matchModel.getScoreHome() + 1)
//            } else {
//                matchModel.setScoreAway(matchModel.getScoreAway() + 1)
//            }
//            if (goleador != null && matchModel.getMarcatori() != null) {
//                val marcatoreModel = MarcatoreModel(goleador)
//                marcatoreModel.setMinute(matchModel.getMinuto())
//                matchModel.getMarcatori().add(marcatoreModel)
//            }
//            matchModel.setProtagonista(protagonistaA)
//            matchModel.setCoprotagonista(portiere.getName())
//            messaggio = if (diff < 0 && diff >= -4) {
//                java.lang.String.format(
//                    context.getString(R.string.telecronacaGol1),
//                    goleador.getName()
//                )
//            } else if (diff < -4 && diff >= -8) {
//                java.lang.String.format(
//                    context.getString(R.string.telecronacaGol2),
//                    goleador.getName()
//                )
//            } else {
//                java.lang.String.format(
//                    context.getString(R.string.telecronacaGol3),
//                    goleador.getName()
//                )
//            }
//            matchModel.setPossesso(if (matchModel.getPossesso() === Enums.Possesso.HOME) Enums.Possesso.AWAY else Enums.Possesso.HOME)
//
//            //è rigore
//        } else if (diff >= 0 && diff < 0.25) {
//            matchModel.setFase(Enums.Fase.RIGORE)
//            if (diff >= 0.2 && diff < 0.25) {
//                matchModel.setEvento(Enums.Evento.ESPULSIONE)
//                messaggio =
//                    java.lang.String.format(context.getString(R.string.telecronacaEsp), portiere)
//            }
//            matchModel.setProtagonista(protagonistaA)
//            matchModel.setCoprotagonista(portiere.getName())
//            matchModel.setAzione(finA.toInt())
//            messaggio = messaggio + " " + java.lang.String.format(
//                context.getString(R.string.telecronacaRig),
//                goleador.getName()
//            )
//            //vince la squadra difendente
//        } else {
//            matchModel.setFase(Enums.Fase.CENTROCAMPO)
//            matchModel.setEvento(Enums.Evento.NONE)
//            matchModel.setProtagonista(portiere.getName())
//            matchModel.setCoprotagonista(protagonistaA)
//            matchModel.setAzione(finA.toInt())
//            messaggio = if (diff >= 1 && diff < 2) {
//                java.lang.String.format(
//                    context.getString(R.string.telecronacaPal),
//                    goleador.getName()
//                )
//            } else if (diff >= 2 && diff < 3) {
//                java.lang.String.format(
//                    context.getString(R.string.telecronacaTra),
//                    goleador.getName()
//                )
//            } else if (diff >= 3 && diff < 5) {
//                java.lang.String.format(
//                    context.getString(R.string.telecronacaPar1),
//                    goleador.getName(),
//                    portiere.getName()
//                )
//            } else if (diff >= 5 && diff < 7) {
//                java.lang.String.format(
//                    context.getString(R.string.telecronacaPar2),
//                    goleador.getName(),
//                    portiere.getName()
//                )
//            } else if (diff >= 7 && diff < 9) {
//                java.lang.String.format(
//                    context.getString(R.string.telecronacaOut1),
//                    goleador.getName()
//                )
//            } else {
//                java.lang.String.format(
//                    context.getString(R.string.telecronacaOut2),
//                    goleador.getName()
//                )
//            }
//            matchModel.setPossesso(if (matchModel.getPossesso() === Enums.Possesso.HOME) Enums.Possesso.AWAY else Enums.Possesso.HOME)
//        }
//        matchModel.setMessaggio(messaggio)
//        return matchModel
//    }
//
//    fun punizione(matchManager: MatchManager): MatchModel {
//        var matchModel: MatchModel = matchManager.getMatchModel()
//        val tiratori: List<PlayerModel> = ArrayList<Any?>(matchManager.getAttackers())
//        val tiratore1: PlayerModel = tiratori.removeAt(findTiratore(tiratori))
//        val tiratore2: PlayerModel = tiratori.removeAt(findTiratore(tiratori))
//        val soglia: Double = tiratore1.getPlayerStatsModel().getPor() * 2.0
//        val tot: Double = soglia + tiratore2.getPlayerStatsModel().getRig()
//        val dado = Math.random() * tot
//        val tiratore: PlayerModel = if (dado < soglia) tiratore1 else tiratore2
//        val finA = -1.0
//        val pot = 0.0
//        val fix = 0.0
//        matchModel = if (matchManager.getMatchModel().getAzione() > 70) {
//            punizioneDiretta(matchManager, tiratore)
//        } else {
//            punizioneGiocata(matchManager, tiratore)
//        }
//        return matchModel
//    }
//
//    private fun punizioneDiretta(matchManager: MatchManager, tiratore: PlayerModel?): MatchModel {
//        val matchModel: MatchModel = matchManager.getMatchModel()
//        val protagonistaA = ""
//        val goleador: PlayerModel? = null
//        var finA = -1.0
//        var pot = 0.0
//        var fix = 0.0
//        val dado = 0.0
//        pot = tiratore.getPlayerStatsModel().getRig()
//        fix = if (matchManager.getLegend()) tiratore.getValueLegend() else tiratore.getValue()
//        finA = fix / 2 + Math.random() * pot / 2
//        val portiere: PlayerModel = matchManager.getDefenders().get(0)
//        pot = portiere.getPlayerStatsModel().getPor() / 2 + portiere.getPlayerStatsModel()
//            .getBal() / 4 + portiere.getPlayerStatsModel().getDif() / 4
//        fix = if (matchManager.getLegend()) portiere.getValueLegend() else portiere.getValue()
//        val parata = fix / 4 * 3 + Math.random() * pot / 4 * 3
//        val diff = parata - finA
//        val context: Context = app.getBaseContext()
//        var messaggio: String? = ""
//        matchModel.setStato(matchModel.getPossesso().ordinal())
//
//        //gol su punizione
//        messaggio = if (diff < 0) {
//            matchModel.setFase(Enums.Fase.CENTROCAMPO)
//            matchModel.setGol(true)
//            if (matchModel.getPossesso() === Enums.Possesso.HOME) {
//                matchModel.setScoreHome(matchModel.getScoreHome() + 1)
//            } else {
//                matchModel.setScoreAway(matchModel.getScoreAway() + 1)
//            }
//            if (tiratore != null && matchModel.getMarcatori() != null) {
//                val marcatoreModel = MarcatoreModel(tiratore)
//                marcatoreModel.setMinute(matchModel.getMinuto())
//                matchModel.getMarcatori().add(marcatoreModel)
//            }
//            matchModel.setProtagonista(protagonistaA)
//            matchModel.setCoprotagonista(portiere.getName())
//            if (diff < 0 && diff >= -4) {
//                java.lang.String.format(
//                    context.getString(R.string.telecronacaPun1),
//                    tiratore.getName()
//                )
//            } else {
//                java.lang.String.format(
//                    context.getString(R.string.telecronacaPun2),
//                    tiratore.getName()
//                )
//            }
//            //vince la squadra difendente
//        } else {
//            matchModel.setFase(Enums.Fase.CENTROCAMPO)
//            matchModel.setEvento(Enums.Evento.NONE)
//            matchModel.setProtagonista(portiere.getName())
//            matchModel.setCoprotagonista(protagonistaA)
//            matchModel.setAzione(finA.toInt())
//            if (diff >= 2 && diff < 4) {
//                java.lang.String.format(
//                    context.getString(R.string.telecronacaPar1),
//                    tiratore.getName(),
//                    portiere.getName()
//                )
//            } else {
//                java.lang.String.format(
//                    context.getString(R.string.telecronacaPar2),
//                    tiratore.getName(),
//                    portiere.getName()
//                )
//            }
//        }
//        matchModel.setMessaggio(messaggio)
//        matchModel.setPossesso(if (matchModel.getPossesso() === Enums.Possesso.HOME) Enums.Possesso.AWAY else Enums.Possesso.HOME)
//        return matchModel
//    }
//
//    private fun punizioneGiocata(matchManager: MatchManager, tiratore: PlayerModel): MatchModel {
//        val matchModel: MatchModel = matchManager.getMatchModel()
//        var protagonistaA = ""
//        var goleador: PlayerModel? = null
//        var finA = -1.0
//        var pot = 0.0
//        var fix = 0.0
//        var dado = 0.0
//        pot = tiratore.getPlayerStatsModel().getRig() / 4 * 3 + tiratore.getPlayerStatsModel()
//            .getTec() / 4
//        fix = if (matchManager.getLegend()) tiratore.getValueLegend() else tiratore.getValue()
//        finA = fix / 2 + Math.random() * pot / 2
//        var protagonistaD = ""
//        var difD = -1.0
//        for (defender in matchManager.getDefenders()) {
//            val partecipa = Math.random() * 100.0
//            if (partecipa > defender.getRoleLineUp().getPartCen() && !defender.isEspuslo()) {
//                val difStats: PlayerStatsModel = defender.getPlayerStatsModel()
//                pot = difStats.getBal() / 2 + difStats.getDif() / 4 + difStats.getFis() / 4
//                fix =
//                    if (matchManager.getLegend()) defender.getValueLegend() else defender.getValue()
//                dado = fix / 4 * 3 + Math.random() * pot / 2
//                if (dado > difD) {
//                    difD = dado
//                    protagonistaD = defender.getName()
//                }
//            }
//        }
//        var diff = difD - finA
//        val context: Context = app.getBaseContext()
//        var messaggio: String? = ""
//        matchModel.setStato(matchModel.getPossesso().ordinal())
//        if (diff < 0) {
//            for (attacker in matchManager.getAttackers()) {
//                val partecipa: Double =
//                    attacker.getRoleLineUp().getPartfin() / 2 + Math.random() * 50.0
//                if (partecipa > attacker.getRoleLineUp().getPartfin() && !attacker.isEspuslo()) {
//                    val attStats: PlayerStatsModel = attacker.getPlayerStatsModel()
//                    pot = attStats.getFin() / 2 + attStats.getAtt() / 4 + attStats.getVel() / 4
//                    fix =
//                        if (matchManager.getLegend()) attacker.getValueLegend() else attacker.getValue()
//                    dado = fix / 4 * 3 + Math.random() * pot / 4
//                    if (dado > finA) {
//                        finA = dado
//                        protagonistaA = attacker.getName()
//                        goleador = attacker
//                    }
//                }
//            }
//            val portiere: PlayerModel = matchManager.getDefenders().get(0)
//            pot = portiere.getPlayerStatsModel().getPor() / 2 + portiere.getPlayerStatsModel()
//                .getBal() / 4 + portiere.getPlayerStatsModel().getDif() / 4
//            fix = if (matchManager.getLegend()) portiere.getValueLegend() else portiere.getValue()
//            val parata = fix / 8 * 7 + Math.random() * pot / 8
//            diff = parata - finA
//            if (diff < 0) {
//                matchModel.setFase(Enums.Fase.CENTROCAMPO)
//                matchModel.setGol(true)
//                if (matchModel.getPossesso() === Enums.Possesso.HOME) {
//                    matchModel.setScoreHome(matchModel.getScoreHome() + 1)
//                } else {
//                    matchModel.setScoreAway(matchModel.getScoreAway() + 1)
//                }
//                if (goleador == null) {
//                    goleador = tiratore
//                }
//                if (matchModel.getMarcatori() != null) {
//                    val marcatoreModel = MarcatoreModel(tiratore)
//                    marcatoreModel.setMinute(matchModel.getMinuto())
//                    matchModel.getMarcatori().add(marcatoreModel)
//                }
//                matchModel.setProtagonista(protagonistaA)
//                matchModel.setCoprotagonista(portiere.getName())
//                messaggio = if (diff < 0 && diff >= -2) {
//                    java.lang.String.format(
//                        context.getString(R.string.telecronacaGol1),
//                        goleador.getName()
//                    )
//                } else if (diff < -2 && diff >= -4) {
//                    java.lang.String.format(
//                        context.getString(R.string.telecronacaGol2),
//                        goleador.getName()
//                    )
//                } else {
//                    java.lang.String.format(
//                        context.getString(R.string.telecronacaGol3),
//                        goleador.getName()
//                    )
//                }
//            } else {
//                matchModel.setFase(Enums.Fase.CENTROCAMPO)
//                matchModel.setEvento(Enums.Evento.NONE)
//                matchModel.setProtagonista(portiere.getName())
//                matchModel.setCoprotagonista(protagonistaA)
//                matchModel.setAzione(finA.toInt())
//                messaggio = if (diff >= 2 && diff < 4) {
//                    java.lang.String.format(
//                        context.getString(R.string.telecronacaPar1),
//                        goleador.getName(),
//                        portiere.getName()
//                    )
//                } else {
//                    java.lang.String.format(
//                        context.getString(R.string.telecronacaPar2),
//                        goleador.getName(),
//                        portiere.getName()
//                    )
//                }
//            }
//        } else {
//            matchModel.setFase(Enums.Fase.CENTROCAMPO)
//            matchModel.setEvento(Enums.Evento.NONE)
//            matchModel.setProtagonista(protagonistaD)
//            matchModel.setCoprotagonista(protagonistaA)
//            matchModel.setAzione(finA.toInt())
//            if (diff > 4) {
//                messaggio =
//                    String.format(context.getString(R.string.telecronacaDif1), protagonistaD)
//            } else if (diff > 8) {
//                messaggio =
//                    String.format(context.getString(R.string.telecronacaDif2), protagonistaD)
//            } else if (diff > 15) {
//                messaggio =
//                    String.format(context.getString(R.string.telecronacaDif3), protagonistaD)
//            }
//        }
//        matchModel.setMessaggio(messaggio)
//        matchModel.setPossesso(if (matchModel.getPossesso() === Enums.Possesso.HOME) Enums.Possesso.AWAY else Enums.Possesso.HOME)
//        return matchModel
//    }
//
//    fun rigore(matchManager: MatchManager): MatchModel {
//        val matchModel: MatchModel = matchManager.getMatchModel()
//        val tiratori: List<PlayerModel> = ArrayList<Any?>(matchManager.getAttackers())
//        val tiratore: PlayerModel = tiratori.removeAt(findTiratore(tiratori))
//        var finA = -1.0
//        var pot = 0.0
//        var fix = 0.0
//        val attStats: PlayerStatsModel = tiratore.getPlayerStatsModel()
//        pot = attStats.getRig() / 4 * 3 + attStats.getFin() / 4
//        fix = if (matchManager.getLegend()) tiratore.getValueLegend() else tiratore.getValue()
//        finA = fix / 2 + Math.random() * pot
//        val portiere: PlayerModel = matchManager.getDefenders().get(0)
//        pot = portiere.getPlayerStatsModel().getPor() / 2 + portiere.getPlayerStatsModel()
//            .getBal() / 4 + portiere.getPlayerStatsModel().getDif() / 4
//        fix = if (matchManager.getLegend()) portiere.getValueLegend() else portiere.getValue()
//        val parata = fix / 2 + Math.random() * pot / 2
//        var messaggio: String? = ""
//        val context: Context = app.getBaseContext()
//        val diff = parata - finA
//        matchModel.setStato(matchModel.getPossesso().ordinal())
//        if (diff < 0) {
//            matchModel.setFase(Enums.Fase.CENTROCAMPO)
//            matchModel.setGol(true)
//            if (matchModel.getPossesso() === Enums.Possesso.HOME) {
//                matchModel.setScoreHome(matchModel.getScoreHome() + 1)
//            } else {
//                matchModel.setScoreAway(matchModel.getScoreAway() + 1)
//            }
//            if (tiratore != null && matchModel.getMarcatori() != null) {
//                val marcatoreModel = MarcatoreModel(tiratore)
//                marcatoreModel.setMinute(matchModel.getMinuto())
//                matchModel.getMarcatori().add(marcatoreModel)
//            }
//            matchModel.setProtagonista(tiratore.getName())
//            matchModel.setCoprotagonista(portiere.getName())
//            messaggio = if (diff < 0 && diff >= -4) {
//                java.lang.String.format(
//                    context.getString(R.string.telecronacaRig1),
//                    tiratore.getName()
//                )
//            } else {
//                java.lang.String.format(
//                    context.getString(R.string.telecronacaRig2),
//                    tiratore.getName()
//                )
//            }
//        } else {
//            matchModel.setFase(Enums.Fase.CENTROCAMPO)
//            matchModel.setEvento(Enums.Evento.NONE)
//            matchModel.setProtagonista(portiere.getName())
//            matchModel.setCoprotagonista(tiratore.getName())
//            matchModel.setAzione(finA.toInt())
//            if (diff >= 0 && diff < 1) {
//                messaggio = java.lang.String.format(
//                    context.getString(R.string.telecronacaPal),
//                    tiratore.getName()
//                )
//            } else if (diff >= 1 && diff < 2) {
//                messaggio = java.lang.String.format(
//                    context.getString(R.string.telecronacaTra),
//                    tiratore.getName()
//                )
//            } else if (diff >= 2 && diff < 5) {
//                messaggio = java.lang.String.format(
//                    context.getString(R.string.telecronacaParig),
//                    portiere.getName()
//                )
//            } else if (diff >= 5 && diff < 7) {
//                messaggio = java.lang.String.format(
//                    context.getString(R.string.telecronacaPar1),
//                    tiratore.getName(),
//                    portiere.getName()
//                )
//            } else if (diff >= 7 && diff < 9) {
//                messaggio = java.lang.String.format(
//                    context.getString(R.string.telecronacaOut2),
//                    tiratore.getName()
//                )
//            }
//        }
//        matchModel.setMessaggio(messaggio)
//        matchModel.setPossesso(if (matchModel.getPossesso() === Enums.Possesso.HOME) Enums.Possesso.AWAY else Enums.Possesso.HOME)
//        return matchModel
//    }
//
//    fun rigoreDiretto(
//        matchManager: MatchManager,
//        tiratore: TiratoreModel?,
//        portiere: PlayerModel
//    ): MatchModel {
//        val matchModel: MatchModel = matchManager.getMatchModel()
//        var finA = -1.0
//        var pot = 0.0
//        var fix = 0.0
//        val attStats: PlayerStatsModel = tiratore.getPlayerModel().getPlayerStatsModel()
//        pot = attStats.getRig() / 4 * 3 + attStats.getFin() / 4
//        fix = if (matchManager.getLegend()) tiratore.getPlayerModel()
//            .getValueLegend() else tiratore.getPlayerModel().getValue()
//        finA = fix / 2 + Math.random() * pot
//        pot = portiere.getPlayerStatsModel().getPor() / 2 + portiere.getPlayerStatsModel()
//            .getBal() / 4 + portiere.getPlayerStatsModel().getDif() / 4
//        fix = if (matchManager.getLegend()) portiere.getValueLegend() else portiere.getValue()
//        val parata = fix / 2 + Math.random() * pot / 2
//        var messaggio = ""
//        val context: Context = app.getBaseContext()
//        val diff = parata - finA
//        matchModel.setStato(matchModel.getPossesso().ordinal())
//        tiratore.setHasShot(true)
//        messaggio = if (diff < 0) {
//            matchModel.setGol(true)
//            if (matchModel.getPossesso() === Enums.Possesso.HOME) {
//                matchModel.setScoreHome(matchModel.getScoreHome() + 1)
//            } else {
//                matchModel.setScoreAway(matchModel.getScoreAway() + 1)
//            }
//            if (tiratore != null && matchModel.getMarcatori() != null) {
//                tiratore.setGol(true)
//            }
//            if (diff < 0 && diff >= -4) {
//                java.lang.String.format(
//                    context.getString(R.string.telecronacaRig1),
//                    tiratore.getPlayerModel().getName()
//                )
//            } else {
//                java.lang.String.format(
//                    context.getString(R.string.telecronacaRig2),
//                    tiratore.getPlayerModel().getName()
//                )
//            }
//        } else {
//            matchModel.setAzione(finA.toInt())
//            if (diff >= 0 && diff < 1) {
//                java.lang.String.format(
//                    context.getString(R.string.telecronacaPal),
//                    tiratore.getPlayerModel().getName()
//                )
//            } else if (diff >= 1 && diff < 2) {
//                java.lang.String.format(
//                    context.getString(R.string.telecronacaTra),
//                    tiratore.getPlayerModel().getName()
//                )
//            } else if (diff >= 2 && diff < 5) {
//                java.lang.String.format(
//                    context.getString(R.string.telecronacaParig),
//                    portiere.getName()
//                )
//            } else if (diff >= 5 && diff < 7) {
//                java.lang.String.format(
//                    context.getString(R.string.telecronacaPar1),
//                    tiratore.getPlayerModel().getName(),
//                    portiere.getName()
//                )
//            } else {
//                java.lang.String.format(
//                    context.getString(R.string.telecronacaOut2),
//                    tiratore.getPlayerModel().getName()
//                )
//            }
//        }
//        matchModel.setCoprotagonista(portiere.getName())
//        matchModel.setMessaggio(messaggio)
//        matchModel.setPossesso(if (matchModel.getPossesso() === Enums.Possesso.HOME) Enums.Possesso.AWAY else Enums.Possesso.HOME)
//        return matchModel
//    }
//
//    fun findTiratore(playerModels: List<PlayerModel>): Int {
//        var index = 0
//        var mRig = 0.0
//        for (i in playerModels.indices) {
//            val p: PlayerModel = playerModels[i]
//            if (!p.isEspuslo() && p.getPlayerStatsModel().getRig() >= mRig) {
//                index = i
//                mRig = p.getPlayerStatsModel().getRig()
//            }
//        }
//        return index
//    } /*
//
//    function Partita(squadraCasa, squadraOspite) {
//        this.squadraCasa=squadraCasa;
//        this.squadraOspite=squadraOspite;
//        this.azione=0;
//        this.possesso="Casa";
//    }
//
//    function MatchManager() {
//        this.azione=0;
//        this.evento="none";
//        this.possesso="Casa";
//        this.protagonista={};
//        this.coprotagonista={};
//        this.cp=false;
//        this.messaggio="";
//        this.gol=false;
//    }
//
//    function getValore(ruolo, valore) {
//        var val=new Array();
//        valore=parseFloat(valore);
//        //att=[0]; dif=[1]; tec=[2]; dri=[3]; fin=[4]; bal=[5]; fis=[6]; vel=[7]; rig=[8]; por=[9];
//        switch (ruolo) {
//            case "pt":
//                val[0]=valore/100.0*31.0; val[1]=valore/100.0*92.0; val[2]=valore/100.0*55.0; val[3]=valore/100.0*34.0; val[4]=valore/100.0*31.0; val[5]=valore/100.0*83.0; val[6]=valore/100.0*67.0; val[7]=valore/100.0*95.0; val[8]=valore/100.0*31.0; val[9]=valore;
//                break;
//            case "dc":
//                val[0]=valore/100.0*47.0; val[1]=valore; val[2]=valore/100.0*58.0; val[3]=valore/100.0*41.0; val[4]=valore/100.0*62.0; val[5]=valore/100.0*95.0; val[6]=valore/100.0*88.0; val[7]=valore/100.0*74.0; val[8]=valore/100.0*64.0; val[9]=valore/100.0*31.0;
//                break;
//            case "td":
//                val[0]=valore/100.0*64.0; val[1]=valore/100.0*88.0; val[2]=valore/100.0*83.0; val[3]=valore/100.0*75.0; val[4]=valore/100.0*62.0; val[5]=valore/100.0*65.0; val[6]=valore/100.0*85.0; val[7]=valore; val[8]=valore/100.0*61.0; val[9]=valore/100.0*31.0;
//                break;
//            case "ts":
//                val[0]=valore/100.0*64.0; val[1]=valore/100.0*88.0; val[2]=valore/100.0*83.0; val[3]=valore/100.0*75.0; val[4]=valore/100.0*62.0; val[5]=valore/100.0*65.0; val[6]=valore/100.0*85.0; val[7]=valore; val[8]=valore/100.0*61.0; val[9]=valore/100.0*31.0;
//                break;
//            case "md":
//                val[0]=valore/100.0*55.0; val[1]=valore/100.0*92.0; val[2]=valore/100.0*85.0; val[3]=valore/100.0*58.0; val[4]=valore/100.0*62.0; val[5]=valore/100.0*92.0; val[6]=valore; val[7]=valore/100.0*77.0; val[8]=valore/100.0*77.0; val[9]=valore/100.0*31.0;
//                break;
//            case "cc":
//                val[0]=valore/100.0*70.0; val[1]=valore/100.0*68.0; val[2]=valore; val[3]=valore/100.0*82.0; val[4]=valore/100.0*80.0; val[5]=valore/100.0*71.0; val[6]=valore/100.0*75.0; val[7]=valore/100.0*68.0; val[8]=valore/100.0*95.0; val[9]=valore/100.0*31.0;
//                break;
//            case "tq":
//                val[0]=valore/100.0*88.0; val[1]=valore/100.0*58.0; val[2]=valore; val[3]=valore/100.0*92.0; val[4]=valore/100.88*.0; val[5]=valore/100.0*58.0; val[6]=valore/100.0*55.0; val[7]=valore/100.0*71.0; val[8]=valore/100.0*95.0; val[9]=valore/100.0*31.0;
//                break;
//            case "ad":
//                val[0]=valore/100.0*92.0; val[1]=valore/100.0*62.0; val[2]=valore/100.0*92.0; val[3]=valore; val[4]=valore/100.0*85.0; val[5]=valore/100.0*73.0; val[6]=valore/100.0*85.0; val[7]=valore/100.0*95.0; val[8]=valore/100.0*83.0; val[9]=valore/100.0*31.0;
//                break;
//            case "as":
//                val[0]=valore/100.0*92.0; val[1]=valore/100.0*62.0; val[2]=valore/100.0*92.0; val[3]=valore; val[4]=valore/100.0*85.0; val[5]=valore/100.0*64.0; val[6]=valore/100.0*85.0; val[7]=valore/100.0*95.0; val[8]=valore/100.0*83.0; val[9]=valore/100.0*31.0;
//                break;
//            case "sp":
//                val[0]=valore; val[1]=valore/100.0*51.0; val[2]=valore/100.0*95.0; val[3]=valore/100.0*88.0; val[4]=valore/100.0*92.0; val[5]=valore/100.0*71.0; val[6]=valore/100.0*68.0; val[7]=valore/100.0*80.0; val[8]=valore/100.0*95.0; val[9]=valore/100.0*31.0;
//                break;
//            case "pp":
//                val[0]=valore/100.0*95.0; val[1]=valore/100.0*45.0; val[2]=valore/100.0*77.0; val[3]=valore/100.0*82.0; val[4]=valore; val[5]=valore/100.0*85.0; val[6]=valore/100.0*88.0; val[7]=valore/100.0*82.0; val[8]=valore/100.0*95.0; val[9]=valore/100.0*31.0;
//                break;
//            default:
//                val[0]=valore/100.0*80.0; val[1]=valore/100.0*80.0; val[2]=valore/100.0*80.0; val[3]=valore/100.0*80.0; val[4]=valore/100.0*80.0; val[5]=valore/100.0*80.0; val[6]=valore/100.0*80.0; val[7]=valore/100.0*80.0; val[8]=valore/100.0*80.0; val[9]=valore/100.0*80.0;
//                break;
//        }
//        return val;
//    }
//
//
//
//
//    function random(limit) {
//        return limit*Math.random();
//    }
//
//    function rigaGiocatore(el, pl, amb) {
//        el="#"+el;
//        var stato=pl.stato;
//        switch (stato) {
//            case 'ammonito':
//                stato="yc";
//                break;
//            default:
//                stato="";
//                break;
//        }
//        var content = "<div class='ruolo column'>"+pl.ruoloatt+"</div><div class='nome column'>"+pl.nome+"</div><div class='val column'>"+pl.valore+"</div><div class='stato column'>"+stato+"</div>";
//        if (amb=="C") {
//            $("#boxHome").find(el).html(content);
//        } else {
//            $("#boxHost").find(el).html(content);
//        }
//    }
//
//    var partecipazione = {
//            "pt": {"dif" : 30, "cen" : 92, "att" : 98, "fin": 99},
//            "dc": {"dif" : 10, "cen" : 75, "att" : 85, "fin": 95},
//            "td": {"dif" : 20, "cen" : 35, "att" : 55, "fin": 65},
//            "ts": {"dif" : 20, "cen" : 35, "att" : 55, "fin": 65},
//            "md": {"dif" : 25, "cen" : 30, "att" : 50, "fin": 75},
//            "cc": {"dif" : 45, "cen" : 10, "att" : 40, "fin": 50},
//            "tq": {"dif" : 70, "cen" : 20, "att" : 15, "fin": 40},
//            "ad": {"dif" : 75, "cen" : 25, "att" : 20, "fin": 35},
//            "as": {"dif" : 75, "cen" : 25, "att" : 20, "fin": 35},
//            "sp": {"dif" : 85, "cen" : 40, "att" : 10, "fin": 20},
//            "pp": {"dif" : 95, "cen" : 55, "att" : 20, "fin": 10},
//}
//
//    function centrocampo(partita) {
//        var mmanager = new MatchManager();
//        var possesso=partita.possesso;
//        var opposto; var attaccante; var difendente;
//        if (possesso=="Casa") {
//            attaccante=partita.squadraCasa.players;
//            difendente=partita.squadraOspite.players;
//            opposto="Ospite";
//        } else {
//            attaccante=partita.squadraOspite.players;
//            difendente=partita.squadraCasa.players;
//            opposto="Casa";
//        }
//        var cenA=-1; var protagonistaA; var pot=0; var dado=0;
//        Object.keys(attaccante).forEach( function(key) {
//            var part=random(100);
//            if (attaccante[key].ruoloatt!="pan" && part>partecipazione[attaccante[key].ruoloatt].cen) {
//                //console.log(part);
//                pot=attaccante[key].tec*2+attaccante[key].dri/2+attaccante[key].vel/2;
//                dado=pot/3+random(pot)/3*7;
//                if (dado>cenA) {
//                    cenA=dado;
//                    protagonistaA=attaccante[key].nome;
//                }
//            }
//        });
//        var cenD=-1; var protagonistaD;
//        Object.keys(difendente).forEach( function(key) {
//            var part=random(100);
//            if (difendente[key].ruoloatt!="pan" && part>partecipazione[difendente[key].ruoloatt].cen) {
//                pot=difendente[key].fis*2+difendente[key].bal/2+difendente[key].vel/2;
//                dado=pot/3+random(pot)/3*7;
//                if (dado>cenD) {
//                    cenD=dado;
//                    protagonistaD=difendente[key].nome;
//                }
//            }
//        });
//
//        var diff=cenD-cenA;
//        if (diff<0) {
//            mmanager.azione=1;
//            mmanager.possesso=possesso;
//            mmanager.protagonista=protagonistaA;
//            mmanager.messaggio="";
//        } else if (diff>=0 && diff<2) {
//            mmanager.azione=0;
//            manager.evento="espulsione";
//            mmanager.possesso=possesso;
//            mmanager.protagonista=protagonistaA;
//            mmanager.coprotagonista=protagonistaD;
//        } else if (diff>=0 && diff<2) {
//            mmanager.azione=0;
//            manager.evento="ammonizione";
//            mmanager.possesso=possesso;
//            mmanager.protagonista=protagonistaA;
//            mmanager.coprotagonista=protagonistaD;
//        } else {
//            mmanager.azione=0;
//            mmanager.possesso=opposto;
//            mmanager.protagonista=protagonistaD;
//            mmanager.messaggio="";
//        }
//        return mmanager;
//    }
//
//    function attacco(partita) {
//        var mmanager = new MatchManager();
//        var possesso=partita.possesso;
//        var opposto; var attaccante; var difendente;
//        if (possesso=="Casa") {
//            attaccante=partita.squadraCasa.players;
//            difendente=partita.squadraOspite.players;
//            opposto="Ospite";
//        } else {
//            attaccante=partita.squadraOspite.players;
//            difendente=partita.squadraCasa.players;
//            opposto="Casa";
//        }
//        var AttA=-1; var protagonistaA; var pot=0; var dado=0;
//        Object.keys(attaccante).forEach( function(key) {
//            var part=random(100);
//            if (attaccante[key].ruoloatt!="pan" && part>partecipazione[attaccante[key].ruoloatt].cen) {
//                pot=attaccante[key].att+attaccante[key].dri+attaccante[key].tec/2+attaccante[key].vel/2;
//                dado=pot/3+random(pot)/3*7;
//                if (dado>AttA) {
//                    AttA=dado;
//                    protagonistaA=attaccante[key].nome;
//                }
//            }
//        });
//        var DifD=-1; var protagonistaD;
//        Object.keys(difendente).forEach( function(key) {
//            var part=random(100);
//            if (difendente[key].ruoloatt!="pan" && part>partecipazione[difendente[key].ruoloatt].cen) {
//                pot=difendente[key].fis+difendente[key].bal+difendente[key].fis/2+difendente[key].vel/2;
//                dado=pot/3+random(pot)/3*7;
//                if (dado>DifD) {
//                    DifD=dado;
//                    protagonistaD=difendente[key].nome;
//                }
//            }
//        });
//        var diff=DifD-AttA;
//
//        if (diff<0) {
//            mmanager.azione=2;
//            mmanager.possesso=possesso;
//            mmanager.protagonista=protagonistaA;
//            mmanager.messaggio=getMessaggio("attaccoG", protagonistaA);
//        } else if (diff>=0 && diff<5) {
//            mmanager.possesso=possesso;
//            if (diff<3) {
//                mmanager.evento="ammonizione";
//            } else if (diff==3) {
//                mmanager.evento="espulsione";
//            }
//            mmanager=calcioPunizione(mmanager, attaccante, difendente);
//        } else if (diff>=5 && diff<7) {
//            mmanager.possesso=possesso;
//            mmanager=rigore(mmanager, attaccante, difendente);
//        } else {
//            mmanager.azione=0;
//            mmanager.possesso=opposto;
//            mmanager.protagonista=protagonistaD;
//            mmanager.messaggio=getMessaggio("attaccoR", protagonistaD);
//        }
//        return mmanager;
//    }
//
//    function finalizzazione(partita) {
//        var mmanager = new MatchManager();
//        var possesso=partita.possesso;
//        var opposto; var attaccante; var difendente;
//        if (possesso=="Casa") {
//            attaccante=partita.squadraCasa.players;
//            difendente=partita.squadraOspite.players;
//            opposto="Ospite";
//        } else {
//            attaccante=partita.squadraOspite.players;
//            difendente=partita.squadraCasa.players;
//            opposto="Casa";
//        }
//        var FinA=-1; var protagonistaA; var pot=0; var dado=0;
//        Object.keys(attaccante).forEach( function(key) {
//            var part=random(100);
//            if (attaccante[key].ruoloatt!="pan" && part>partecipazione[attaccante[key].ruoloatt].cen) {
//                //console.log(part);
//                pot=attaccante[key].fin;
//                dado=pot/3*7+random(pot)/3;
//                if (dado>FinA) {
//                    FinA=dado;
//                    protagonistaA=attaccante[key].nome;
//                }
//            }
//        });
//
//        var portiere=getBestByStat(difendente, "por");
//        pot=portiere.por;
//        var parata=pot/3*7+random(pot)/3;
//
//        var diff=parata-FinA;
//        if (diff<0) {
//            mmanager.gol=true;
//            mmanager.protagonista=protagonistaA;
//            mmanager.messaggio=getMessaggio("gol", protagonistaA);
//        } else if (diff>=0 && diff<3) {
//            mmanager.possesso=possesso;
//            if (diff==0) {
//                manager.evento="ammonizione";
//            } else if (diff==1) {
//                manager.evento="espulsione";
//            }
//            mmanager=rigore(mmanager, attaccante, difendente);
//        } else {
//            mmanager.protagonista=portiere;
//            mmanager.messaggio=getMessaggio("attaccoR", portiere.nome);
//        }
//        mmanager.azione=0;
//        mmanager.possesso=opposto;
//        return mmanager;
//    }
//
//    function calcioPunizione(mman, attaccante, difendente) {
//        var mmanager = new MatchManager();
//        var tiratore=getBestByStat(attaccante, "rig");
//        var possesso=mman.possesso;
//        if (possesso=="Casa") {
//            opposto="Ospite";
//        } else {
//            opposto="Casa";
//        }
//        var scelta=random(100);
//        var AttA=-1; var protagonistaA; var pot=0; var dado=0;
//        if (scelta>(tiratore.rig-20)) {
//
//            Object.keys(attaccante).forEach( function(key) {
//
//                if (attaccante[key].ruoloatt!="pan" && part>partecipazione[attaccante[key].ruoloatt].bal) {
//                    pot=attaccante[key].bal;
//                    dado=pot/3*7+random(pot)/3;
//                    if (dado>AttA) {
//                        AttA=dado;
//                        protagonistaA=attaccante[key].nome;
//                    }
//                }
//            });
//        } else {
//            pot=tiratore.rig;
//            AttA=pot/3*7+random(pot)/3;
//        }
//
//        var protagonistaD=getBestByStat(difendente, "bal");
//        pot=protagonistaD.bal;
//        var DifD=pot/3*7+random(pot)/3;
//        var portiere=getBestByStat(difendente, "por");
//        pot=portiere.por;
//        var parata=pot/3*7+random(pot)/3;
//
//        var diff=(DifD+parata)-AttA*2;
//        if (diff<0) {
//            mmanager.gol=true;
//            if (protagonistaA==tiratore.nome) {
//                mmanager.messaggio=mman.messaggio+getMessaggio("calcioPunizioneG", protagonistaA);
//            } else {
//                mmanager.messaggio=mman.messaggio+getMessaggio("calcioPunizioneT", protagonistaA);
//            }
//        } else {
//            mmanager.messaggio=mman.messaggio+getMessaggio("calcioPunizioneR", protagonistaD.nome);
//        }
//        mmanager.azione=0;
//        mmanager.possesso=opposto;
//        mmanager.cp=true;
//        return mmanager;
//    }
//
//    function rigore(mman, tiratore, portiere) {
//        var mmanager = new MatchManager();
//        var possesso=mman.possesso;
//        if (possesso=="Casa") {
//            opposto="Ospite";
//        } else {
//            opposto="Casa";
//        }
//        var AttA=-1; var protagonistaA; var pot=0; var dado=0;
//        pot=tiratore.rig;
//        AttA=pot/3*7+random(pot)/3;
//        pot=portiere.por;
//        var parata=pot/3*7+random(pot)/3;
//        var diff=parata-AttA;
//        if (diff<0) {
//            mmanager.gol=true;
//            mmanager.messaggio=mman.messaggio+getMessaggio("RigoreG", tiratore.nome);
//        } else {
//            mmanager.messaggio=mman.messaggio+getMessaggio("RigoreRR", portiere.nome);
//        }
//        mmanager.azione=0;
//        mmanager.possesso=opposto;
//        mmanager.cp=true;
//        return mmanager;
//    }
//
//
//    function getBestByStat(players, stat) {
//        var pl=new Player('P', 'md', 100, 'md');
//        var max=-1;
//        Object.keys(players).forEach( function(key) {
//            if (players[key].ruoloatt!="pan") {
//                switch (stat) {
//                    case "rig":
//                        if (players[key].rig>max) {
//                            max=players[key].rig;
//                            pl=players[key];
//                        }
//                        break;
//                    case "por":
//                        if (players[key].ruoloatt=="pt") {
//                            max=players[key].por;
//                            pl=players[key];
//                        }
//                        break;
//                    case "tes":
//                        if (players[key].bal>max) {
//                            max=players[key].bal;
//                            pl=players[key];
//                        }
//                        break;
//                    default:
//                        if (players[key].tec>max) {
//                            max=players[key].tec;
//                            pl=players[key];
//                        }
//                        break;
//                }
//            }
//        });
//        return pl;
//
//
//    }
//
//
//
//    function getMessaggio(caso, giocatore) {
//        var dado=random(10);
//        var messaggio="";
//        switch(caso) {
//            case "attaccoG":
//                if (dado<3) {
//                    messaggio=giocatore+" mette una grande palla in mezzo. ";
//                } else if (dado>=3 & dado<7) {
//                    messaggio="Grandissima giocata di "+giocatore+". ";
//                } else {
//                    messaggio="Che classe "+giocatore+"! Ccezionale!"+" ";
//                }
//                break;
//            case "attaccoR":
//                if (dado<3) {
//                    messaggio=giocatore+" respinge il pallone. ";
//                } else if (dado>=3 & dado<7) {
//                    messaggio="Ottima copertura di "+giocatore+" ";
//                } else {
//                    messaggio=giocatore+" evita un'azione pericolosa. ";
//                }
//                break;
//            case "gol":
//                if (dado<3) {
//                    messaggio="Grandissimo gol! "+giocatore+" ha segnato! ";
//                } else if (dado>=3 & dado<7) {
//                    messaggio="Gooooool! Splendida realizzazione di "+giocatore+". ";
//                } else {
//                    messaggio="Rete di "+giocatore+"! Ccezionale! ";
//                }
//                break;
//            case "ammonizione":
//                if (dado<3) {
//                    messaggio="Brutto fallo di "+giocatore+". Ammonito! ";
//                } else if (dado>=3 & dado<7) {
//                    messaggio="Cartellino giallo per "+giocatore;
//                } else {
//                    messaggio="Intervento al limite! "+giocatore+"! Ammonito. ";
//                }
//                break;
//            case "espulsione":
//                if (dado<3) {
//                    messaggio="Brutto fallo di "+giocatore+". Espulso! ";
//                } else if (dado>=3 & dado<7) {
//                    messaggio="Cartellino rosso per "+giocatore+"! Che fallaccio. ";
//                } else {
//                    messaggio="Intervento pericoloso! "+giocatore+" Espulso!";
//                }
//                break;
//            case "calcioPunizioneG":
//                if (dado<3) {
//                    messaggio=giocatore+" batte la punizione. Ed è un gran gol! ";
//                } else if (dado>=3 & dado<7) {
//                    messaggio="Grandissimo gol di "+giocatore+" su punizione! ";
//                } else {
//                    messaggio="Gooooooool! "+giocatore+" ha tirato una punizione magistrale! ";
//                }
//                break;
//            case "calcioPunizioneT":
//                if (dado<3) {
//                    messaggio=giocatore+" stacca più in alto di tutti e insacca! Rete! ";
//                } else if (dado>=3 & dado<7) {
//                    messaggio="Grandissimo gol di "+giocatore+" sullo sviluppo del calcio di punizione! ";
//                } else {
//                    messaggio="Calcio di punizione. Gooooooool! "+giocatore+" ha seganto anticipando tutti! ";
//                }
//                break;
//            case "calcioPunizioneR":
//                if (dado<3) {
//                    messaggio=giocatore+" batte la punizione. Ma è a lato di un soffio! ";
//                } else if (dado>=3 & dado<7) {
//                    messaggio="Tentativo di "+giocatore+" su punizione, ma viene respinto! ";
//                } else {
//                    messaggio=giocatore+"Non va! Punizione respinta! ";
//                }
//                break;
//            case "RigoreG":
//                if (dado<3) {
//                    messaggio=giocatore+" sul dischetto. Ed è un gran gol! ";
//                } else if (dado>=3 & dado<7) {
//                    messaggio="Calcio di rigore! "+giocatore+" spiazza il portiere e segna! ";
//                } else {
//                    messaggio="Gooooooool! "+giocatore+" ha tirato un rigore perfetto! ";
//                }
//                break;
//            case "RigoreR":
//                if (dado<3) {
//                    messaggio=giocatore+" batte il calcio di rigore. Ma è a lato di un soffio! ";
//                } else if (dado>=3 & dado<7) {
//                    messaggio=giocatore+" sul discetto, rigore parato! ";
//                } else {
//                    messaggio="Sarà calcio di rigore! "+giocatore+" sbaglia! Pallone respinto! ";
//                }
//                break;
//            default:
//                if (dado<3) {
//                    messaggio="Giro palla a centrocampo. ";
//                } else if (dado>=3 & dado<7) {
//                    messaggio="Le due squadre si studiano in mezzo al campo. ";
//                } else {
//                    messaggio="Ritmi lenti. ";
//                }
//                break;
//        }
//        return messaggio;
//    }
//
//    function checkImg($nome, $tipo) {
//		// controlla se la url inizia con il percorso corretto
//		$nome = str_replace(" ","_", $nome);
//		$img= strcasecmp($tipo, "player")==0 ? "img/album_photo/".$nome.".jpg" : "img/album_photo/loghi/".$nome.".png";
//		if (!file_exists($img)) {
//			$img= strcasecmp($tipo, "player")==0 ? "img/no-photo-icon.jpg" : "img/empty-logo.png";
//		}
//		return $img;
//	}
//
//    function getValore($ruolo, $val) {
//		$valore=50;
//	    switch ($ruolo) {
//			case "pt":
//			    $valore=( ($val["por"]*2)+$val["dif"]+$val["bal"]+($val["por"]/2+$val["vel"])/2)/5;
//				break;
//			case "dc":
//			    $valore=( ($val["dif"]*2)+$val["bal"]+$val["fis"]+($val["vel"]/2+$val["tec"]/4+$val["rig"]/4) )/5;
//			    break;
//			case "td":
//			    $valore=( ($val["dif"]*2)+$val["vel"]+$val["tec"]+($val["fis"]/2+$val["bal"]/4+$val["dri"]/4) )/5;
//			    break;
//			case "ts":
//			    $valore=( ($val["fis"]*2)+$val["vel"]+$val["tec"]+($val["dif"]/2+$val["bal"]/4+$val["dri"]/4) )/5;
//			    break;
//			case "md":
//			    $valore=( ($val["bal"]*2)+$val["tec"]+$val["fis"]+($val["dif"]/2+$val["rig"]/4+$val["vel"]/4) )/5;
//			    break;
//			case "cc":
//			    $valore=( ($val["tec"]*2)+$val["rig"]+$val["fis"]+($val["bal"]/4+$val["fin"]/4+$val["att"]/4+$val["vel"]/4) )/5;
//			    break;
//			case "tq":
//			    $valore=( ($val["tec"]*2)+$val["dri"]+$val["att"]+($val["rig"]/2+$val["fin"]/4+$val["fis"]/4) )/5;
//			    break;
//			case "ad":
//			    $valore=( ($val["att"]*2)+$val["dif"]+$val["tec"]+($val["vel"]/4+$val["fis"]/4+$val["bal"]/4+$val["dri"]/4) )/5;
//			    break;
//			case "as":
//			    $valore=( ($val["att"]*2)+$val["dri"]+$val["tec"]+($val["vel"]/4+$val["fis"]/4+$val["fin"]/4+$val["dri"]/4) )/5;
//			    break;
//			case "sp":
//			    $valore=( ($val["att"]*2)+$val["fin"]+$val["tec"]+($val["vel"]/4+$val["dri"]/4+$val["bal"]/4+$val["dri"]/4) )/5;
//			    break;
//			case "pp":
//			    $valore=( ($val["fin"]*2)+$val["att"]+$val["dri"]+($val["vel"]/4+$val["tec"]/4+$val["bal"]/4+$val["dri"]/4) )/5;
//			    break;
//			default:
//			    $valore=( ($val["att"]*2)+$val["dif"]+$val["tec"]+($val["vel"]/4+$val["fis"]/4+$val["bal"]/4+$val["dri"]/4) )/5;
//			    break;
//        }
//        return intval($valore);
//
//    }
//
//	function getExaColor($color) {
//		$exaColor=$color;
//		switch ($color) {
//			case "bianco":
//			    $exaColor="#FFFFFF";
//			    break;
//			case "grigio":
//			    $exaColor="#999999";
//			    break;
//			case "giallo":
//			    $exaColor=" #ffcc00";
//			    break;
//			case "arancione":
//			    $exaColor="#ff6600";
//			    break;
//			case "rosso":
//			    $exaColor="#e60000";
//			    break;
//			case "rosa":
//			    $exaColor="#ff99ff";
//			    break;
//			case "lilla":
//			    $exaColor="#d24dff";
//			    break;
//			case "viola":
//			    $exaColor=" #590099";
//			    break;
//			case "blu":
//			    $exaColor="#000099";
//			    break;
//			case "azzurro":
//			    $exaColor="#0073e6";
//			    break;
//			case "verde":
//			    $exaColor="#008000";
//			    break;
//			case "marrone":
//			    $exaColor=" #732626";
//			    break;
//			case "nero":
//			    $exaColor="#000000";
//			    break;
//            default:
//			    $exaColor="#FFFFFF";
//			    break;
//		}
//		return $exaColor;
//	}
//
//	function getTextColor($squadra, $colore1, $colore2) {
//		$colort=getExaColor($colore1);
//		if (strcasecmp($colore1, "blu")==0 && strcasecmp($colore2, "nero"==0)) {
//			$colort=getExaColor("bianco");
//		} elseif (strcasecmp($colore1, "nero")==0 && strcasecmp($colore2, "blu")==0) {
//			$colort=getExaColor("bianco");
//		} elseif (strcasecmp($colore1, "verde")==0 && strcasecmp($colore2, "nero")==0) {
//			$colort=getExaColor("bianco");
//		} elseif (strcasecmp($colore1, "nero")==0 && strcasecmp($colore2, "verde")==0) {
//			$colort=getExaColor("bianco");
//		} elseif (strcasecmp($colore1, "rosso")==0 && strcasecmp($colore2, "blu")==0) {
//			$colort=getExaColor("bianco");
//		} elseif (strcasecmp($colore1, "blu")==0 && strcasecmp($colore2, "rosso")==0) {
//			$colort=getExaColor("bianco");
//		} elseif (strcasecmp($colore1, "rosso")==0 && strcasecmp($colore2, "nero")==0) {
//			$colort=getExaColor("bianco");
//		} elseif (strcasecmp($colore1, "nero")==0 && strcasecmp($colore2, "rosso")==0) {
//			$colort=getExaColor("bianco");
//		} elseif (strcasecmp($colore1, "verde")==0 && strcasecmp($colore2, "rosso")==0) {
//			$colort=getExaColor("bianco");
//		} elseif (strcasecmp($colore1, "rosso")==0 && strcasecmp($colore2, "azzurro")==0) {
//			$colort=getExaColor("bianco");
//		} elseif (strcasecmp($colore1, "azzurro")==0 && strcasecmp($colore2, "rosso")==0) {
//			$colort=getExaColor("bianco");
//		} elseif (strcasecmp($colore1, "marrone")==0 && strcasecmp($colore2, "nero")==0) {
//			$colort=getExaColor("bianco");
//        }
//
//		//casi speciali
//		if (strcasecmp($squadra, "Inter")==0 | strcasecmp($squadra, "Real Madrid")==0 | strcasecmp($squadra, "Barcelona")==0) {
//			$colort=getExaColor("giallo");
//        } elseif (strcasecmp($squadra, "Verona")==0 | strcasecmp($squadra, "Francia")==0 | strcasecmp($squadra, "Chelsea")==0) {
//			$colort=getExaColor("blu");
//		}
//
//        return $colort;
//    }
//
//	class player {
//		var $nome; var $ruolo; var $valore; var $ruoloAtt; var $Ordine;
//
//
//		function player($nome, $r, $valore, $ruoloAtt) {
//			$this->nome=$nome;
//			$this->ruolo=$r;
//			$this->ruoloAtt=$ruoloAtt;
//			$this->valore= intval($valore);
//		}
//
//
//		function setRuoloAtt($ratt) {
//			$this->ruoloAtt=$ratt;
//		}
//
//		function setOrdine($Ord) {
//			$this->Ordine=$Ord;
//		}
//
//		function setValore($valore) {
//			$this->valore= is_int($valore) ? $valore : "50";
//		}
//
//		function getNome() {
//			return $this->nome;
//		}
//
//		function getRuolo() {
//			return $this->ruolo;
//		}
//
//		function getRuoloAtt() {
//			return $this->ruoloAtt;
//		}
//
//		function getOrdine() {
//			return $this->Ordine;
//		}
//
//		function getCValore() {
//			return $this->valore;
//		}
//
//	}
//
//    function schieraFormazione($modulo, $rosa) {
//		$ndc; $ncc; $ntq; $nad; $nas; $nsp; $npp;
//		$schierata=array();
//
//		$acpt=true;
//
//
//
//		switch ($modulo) {
//			case "352":
//		        $ndc=3; $ncc=2; $ntq=0; $nad=0; $nas=0; $nsp=1; $npp=1;
//			    break;
//			case "343":
//		        $ndc=3; $ncc=1; $ntq=0; $nad=1; $nas=1; $nsp=0; $npp=1;
//			    break;
//			case "3412":
//		        $ndc=3; $ncc=1; $ntq=1; $nad=0; $nas=0; $nsp=1; $npp=1;
//			    break;
//			case "442":
//		        $ndc=2; $ncc=1; $ntq=0; $nad=1; $nas=1; $nsp=1; $npp=1;
//			    break;
//			case "4231":
//		        $ndc=2; $ncc=1; $ntq=1; $nad=1; $nas=1; $nsp=0; $npp=1;
//			    break;
//			case "433":
//		        $ndc=2; $ncc=2; $ntq=0; $nad=1; $nas=1; $nsp=0; $npp=1;
//			    break;
//			case "424":
//		        $ndc=2; $ncc=1; $ntq=0; $nad=1; $nas=1; $nsp=0; $npp=2;
//			    break;
//			default:
//		        $ndc=2; $ncc=1; $ntq=0; $nad=1; $nas=1; $nsp=1; $npp=1;
//			    break;
//		}
//
//		//pt
//		$titolare=getBestPlayer($rosa, "pt", "Player Pt");
//		$nomet = $titolare->nome;
//		unset($rosa["$nomet"]);
//		$schierata["$nomet"]=$titolare;
//
//		//dc
//		for ($i=0; $i<$ndc; $i++) {
//			$titolare=getBestPlayer($rosa, "dc", "Player Dc$i");
//			$nomet = $titolare->nome;
//			unset($rosa["$nomet"]);
//			$schierata["$nomet"]=$titolare;
//		}
//
//		//td
//		$titolare=getBestPlayer($rosa, "td",  "Player Td");
//		$nomet = $titolare->nome;
//		unset($rosa["$nomet"]);
//		$schierata["$nomet"]=$titolare;
//
//		//ts
//		$titolare=getBestPlayer($rosa, "ts",  "Player Ts");
//		$nomet = $titolare->nome;
//		unset($rosa["$nomet"]);
//		$schierata["$nomet"]=$titolare;
//
//		//md
//		$titolare=getBestPlayer($rosa, "md",  "Player Md");
//		$nomet = $titolare->nome;
//		unset($rosa["$nomet"]);
//		$schierata["$nomet"]=$titolare;
//
//		//cc
//		for ($i=0; $i<$ncc; $i++) {
//			$titolare=getBestPlayer($rosa, "cc",  "Player Cc$i");
//			$nomet = $titolare->nome;
//			unset($rosa["$nomet"]);
//			$schierata["$nomet"]=$titolare;
//		}
//
//		//tq
//		for ($i=0; $i<$ntq; $i++) {
//			$titolare=getBestPlayer($rosa, "tq",  "Player Tq$i");
//			$nomet = $titolare->nome;
//			unset($rosa["$nomet"]);
//			$schierata["$nomet"]=$titolare;
//		}
//
//		//ad
//		for ($i=0; $i<$nad; $i++) {
//			$titolare=getBestPlayer($rosa, "ad",  "Player Ad$i");
//			$nomet = $titolare->nome;
//			unset($rosa["$nomet"]);
//			$schierata["$nomet"]=$titolare;
//		}
//
//		//as
//		for ($i=0; $i<$nas; $i++) {
//			$titolare=getBestPlayer($rosa, "as",  "Player As$i");
//			$nomet = $titolare->nome;
//			unset($rosa["$nomet"]);
//			$schierata["$nomet"]=$titolare;
//		}
//
//		//sp
//		for ($i=0; $i<$nsp; $i++) {
//			$titolare=getBestPlayer($rosa, "sp",  "Player Sp$i");
//			$nomet = $titolare->nome;
//			unset($rosa["$nomet"]);
//			$schierata["$nomet"]=$titolare;
//		}
//
//		//pp
//		for ($i=0; $i<$npp; $i++) {
//			$titolare=getBestPlayer($rosa, "pp",  "Player Pp$i");
//			$nomet = $titolare->nome;
//			unset($rosa["$nomet"]);
//			$schierata["$nomet"]=$titolare;
//		}
//
//		foreach ($rosa as $pl) {
//			$valore=$pl->getCValore();
//			$ruolo=$pl->getRuolo();
//			$pl->setOrdine(calcolaOrdine("$ruolo", $valore));
//			array_push($schierata, $pl);
//		}
//
//		return $schierata;
//    }
//
//	function getBestPlayer($ar, $ruolo, $alt) {
//		$player= new player("$alt", "$ruolo", 50, "$ruolo");
// 		$mval=1;
//		foreach ($ar as $pl) {
//			if (strcasecmp(($pl->ruolo), "$ruolo")==0) {
//			    $vcur=$pl->getCValore();
//				if ($vcur>$mval) {
//					$mval=$vcur;
//					$player=$pl;
//				}
//			}
//		}
//		$player->setRuoloAtt("$ruolo");
//		$player->setOrdine(calcolaOrdine("$ruolo",1000+$mval));
//		return $player;
//	}
//
//	function PlayerCmp($a, $b) {
//		$OrdA = $a->Ordine;
//		$OrdB = $b->Ordine;
//		if ($OrdA == $OrdB) {
//			return 0;
//		} elseif ($OrdA > $OrdB) {
//			return -1;
//		} else {
//			return 1;
//		}
//	}
//
//	function getQuery($target, $tipo, $par) {
//		$query="";
//		$con=connectDB();
//		if (strcasecmp($target, "giocatore")==0 & strcasecmp($tipo, "leggenda")==0) {
//			$query="select * from calciatore where leggenda='$par' order by ruolo, valeg desc";
//		} elseif (strcasecmp($target, "giocatore")==0 & strcasecmp($tipo, "leggenda")!=0) {
//			$query="select * from calciatore where squadra='$par' order by ruolo, val desc";
//		} elseif (strcasecmp($target, "allenatore")==0 & strcasecmp($tipo, "leggenda")==0) {
//			$query="select nome, modulo from allenatore where leggenda='$par'";
//		} elseif (strcasecmp($target, "nazionale")==0 & strcasecmp($tipo, "leggenda")!=0) {
//			$query="select * from calciatore where nazione='$par' and nazionale=1 and sesso='m' order by ruolo, val desc";
//		} elseif (strcasecmp($target, "nazionale")==0 & strcasecmp($tipo, "leggenda")==0) {
//			$query="select * from calciatore where nazione='$par' and nazionaleg=1 and sesso='m' order by ruolo, valeg desc";
//		} elseif (strcasecmp($target, "nazionaleFemminile")==0 & strcasecmp($tipo, "leggenda")!=0) {
//			$query="select * from calciatore where nazione='$par' and nazionale=1 and sesso='f' order by ruolo, val desc";
//		} elseif (strcasecmp($target, "nazionaleFemminile")==0 & strcasecmp($tipo, "leggenda")==0) {
//			$query="select * from calciatore where nazione='$par' and nazionaleg=1 and sesso='f' order by ruolo, valeg desc";
//		} else {
//			$query="select * from squadra join allenatore on squadra.allenatore=allenatore.nome  where squadra.nome='$par'";
//		}
//		$queryres=mysqli_query($con, $query);
//
//
//		return $queryres;
//
//	}
//
//	<script src="js/partita.js"></script>
//		<script>
//			var squadraCasa = new Squadra("$squadraCasa", "$moduloC");
//			var squadraOspite = new Squadra("$squadraOspite", "$moduloO");
//			var partita = new Partita(squadraCasa, squadraOspite);
// 			var timer;
//			var minuto=0;
//FINE;
//
//
//		for($i=0; $i<$countCasa; $i++) {
//			$nome=$schieramentoCasa[$i]->getNome();
//			$ruolo=$schieramentoCasa[$i]->getRuolo();
//			$ruoloAtt=$schieramentoCasa[$i]->getRuoloAtt();
//			$valore=$schieramentoCasa[$i]->getCValore();
//			echo "var player = new Player('$nome', '$ruolo', '$i', '$ruoloAtt');
//			player.valore=$valore;
//			var val=getValore('$ruolo', $valore);
//			player.att=val[0]; player.dif=val[1]; player.tec=val[2]; player.dri=val[3]; player.fin=val[4]; player.bal=val[5]; player.fis=val[6]; player.vel=val[7]; player.rig=val[8]; player.por=val[9];
//			partita.squadraCasa.addPlayer(player);
//			";
//		}
//
//		for($i=0; $i<$countOspite; $i++) {
//			$nome=$schieramentoOspite[$i]->getNome();
//			$ruolo=$schieramentoOspite[$i]->getRuolo();
//			$ruoloAtt=$schieramentoOspite[$i]->getRuoloAtt();
//			$valore=$schieramentoOspite[$i]->getCValore();
//			echo "var player = new Player('$nome', '$ruolo', '$i', '$ruoloAtt');
//			player.valore=$valore;
//			var val=getValore('$ruolo', $valore);
//			player.att=val[0]; player.dif=val[1]; player.tec=val[2]; player.dri=val[3]; player.fin=val[4]; player.bal=val[5]; player.fis=val[6]; player.vel=val[7]; player.rig=val[8]; player.por=val[9];
//			partita.squadraOspite.addPlayer(player);
//			";
//		}
//
//	    echo <<<FINE
//			//console.log(squadraCasa);
//		    $(document).ready(function() {
//				var prepartita=true;
//				var risC=0;
//				var risO=0;
//				var marcatoriC="";
//				var marcatoriO="";
//                var onChange="disattivo";
//				var gamb="C";
//				var plout= new Player('Pl', 'md', 100, 'md');
//				var plin= new Player('P', 'md', 100, 'md');
//				var posin;
//				var posou;
//				$(".playerRow").click( function() {
//					var id=$(this).prop("id");
//					var amb = id.substring(6,7);
//					if (onChange=="attivo") {
//						$("this").css("background-color", "#ffcc00")
//						onChange="transazione";
//						gamb=amb;
//						var nome=$(this).find(".nome").text();
//						plout= (amb=="C") ? partita.squadraCasa.players[nome] : partita.squadraOspite.players[nome];
//						posin=id;
//					} else if (onChange=="transazione" & amb==gamb) {
//						onChange="attivo";
//						var nome=$(this).find(".nome").text();
//						ruoloapo=plout.ruoloatt;
//						orindepo=plout.ordine;
//						if (amb=="C") {
//							plin=partita.squadraCasa.players[nome];
//							partita.squadraCasa.players[plout.nome].cambiaPosizione(plin.ruoloatt, plin.ordine);
//							partita.squadraCasa.players[plin.nome].cambiaPosizione(ruoloapo, orindepo);
//						} else {
//							plin=partita.squadraOspite.players[nome];
//							partita.squadraOspite.players[plout.nome].cambiaPosizione(plin.ruoloatt, plin.ordine);
//							partita.squadraOspite.players[plin.nome].cambiaPosizione(ruoloapo, orindepo);
//						}
//						posou=id;
//						rigaGiocatore(posin, plin, amb);
//						rigaGiocatore(posou, plout, amb);
//					}
//
//				});
//				$("#cambiC").click(function() {
//					onChange="attivo";
//					$("#boxHome .nascosta").css("display", "block");
//					$("#cambiC").css("display", "none");
//					$("#boxHome .riprendi").css("display", "block");
//					stoppa();
//				});
//				$("#cambiO").click(function() {
//					onChange="attivo";
//					$("#boxHost .nascosta").css("display", "block");
//					$("#cambiO").css("display", "none");
//					$("#boxHost .riprendi").css("display", "block");
//					stoppa();
//				});
//				$(".riprendi").click(function() {
//					onChange="disattivo";
//					$("#boxHome .nascosta").css("display", "none");
//					$("#boxHost .nascosta").css("display", "none");
//					$("#cambiC").css("display", "block");
//					$("#cambiO").css("display", "block");
//					$(".riprendi").css("display", "none");
//					if (prepartita) {
//						$("#iniziap").css("display", "block");
//					}
//				});
//				$("#riavvia").click(function() {
//					onChange="disattivo";
//					$("#boxHome .nascosta").css("display", "none");
//					$("#boxHost .nascosta").css("display", "none");
//					$("#cambiC").css("display", "block");
//					$("#cambiO").css("display", "block");
//					$("#stop").css("display", "block");
//					$(".riprendi").css("display", "none");
//					$("#riavvia").css("display", "none");
//					parteTempo();
//				});
//				$("#iniziap").click(function() {
//					$("#stop").css("display", "block");
//					$("#iniziap").css("display", "none");
//					prepartita=false;
//					parteTempo();
//				});
//				$("#stop").click(function() {
//					stoppa();
//				});
//
//				function parteTempo() {
//					timer=setInterval(function() {
//						if (minuto==90) {
//							clearInterval(timer);
//						} else {
//							minuto++;
//							$("#minuti").html(minuto);
//							if (minuto==45) {
//								stoppa();
//							}
//							//
//							var i;
//							var messaggio="";
//							for (i=0; i<3; i++) {
//								var mman;
//								switch(partita.azione) {
//									case 1:
//										mman=attacco(partita);
//										break;
//									case 2:
//										mman=finalizzazione(partita);
//										break;
//									default:
//										mman=centrocampo(partita);
//										break;
//								}
//
//							    if (mman.gol) {
//									//sono invertiti: la palla è al centro!
//									if (mman.possesso=="Casa") {
//										risO++;
//										$("#golO").html(risO);
//										marcatoriO=marcatoriO+"<br>"+mman.protagonista;
//									} else {
//										risC++;
//										$("#golC").html(risC);
//										marcatoriC=marcatoriC+"<br>"+mman.protagonista;
//									}
//								}
//
//								if (mman.cp) {
//									i++
//								}
//
//								if (mman.evento=="ammonizione") {
//									mman.messaggio += ammonizione(mman.possesso, mman.coprotagonista);
//								} else if (mman.evento=="espulsione") {
//									mman.messaggio += espulsione(mman.possesso, mman.coprotagonista);
//								}
//
//
//								partita.azione=mman.azione;
//								partita.possesso=mman.possesso;
//								messaggio += mman.messaggio;
//							}
//
//							if (messaggio=="") {
//								messaggio=getMessaggio("passaggio", "");
//							}
//							messaggio = "<p class='commento'>"+minuto+"' "+messaggio+"</p>";
//							$(".telecronaca").prepend(messaggio);
//							if (minuto>3) {
//								$(".telecronaca .commento").last().css("display", "none");
//								$(".telecronaca .commento").last().removeClass("commento");
//							}
//						}
//
//
//					}, 1000);
//				}
//
//				function stoppa() {
//					$("#stop").css("display", "none");
//					if (prepartita) {
//						$("#iniziap").css("display", "none");
//					} else {
//						$("#riavvia").css("display", "block");
//					}
//					clearInterval(timer);
//				}
//
//				function ammonizione(squadra, giocatore) {
//					var msg="";
//					if (squadra=="Casa") {
//						if (partita.squadraCasa[giocatore].stato=="ammonizione") {
//							espulsione(squadra, giocatore);
//						} else {
//							partita.squadraCasa[giocatore].stato="ammonizione";
//							msg=getMessaggio("ammonizione", giocatore);
//						}
//
//					} else {
//						if (partita.squadraOspite[giocatore].stato=="ammonizione") {
//							espulsione(squadra, giocatore);
//						} else {
//							partita.squadraOspite[giocatore].stato="ammonizione";
//							msg=getMessaggio("ammonizione", giocatore);
//						}
//					}
//					return msg;
//				}
//
//				function espulsione (squadra, giocatore) {
//					if (squadra=="Casa") {
//						partita.squadraCasa[giocatore].stato="espulsione";
//						partita.squadraCasa[giocatore].ruoloatt="pan";
//					} else {
//						partita.squadraOspite[giocatore].stato="espulsione";
//						partita.squadraOspite[giocatore].ruoloatt="pan";
//					}
//					return getMessaggio("espulsione", giocatore);
//				}
//
//    */
//}