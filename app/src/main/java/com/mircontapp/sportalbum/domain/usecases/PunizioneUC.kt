import android.content.Context
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.ext.findTiratore
import com.mircontapp.sportalbum.commons.ext.getMatchValue
import com.mircontapp.sportalbum.commons.ext.getTelecronaca
import com.mircontapp.sportalbum.commons.ext.isPortiere
import com.mircontapp.sportalbum.commons.ext.partecipa
import com.mircontapp.sportalbum.commons.ext.punizioneDiretta
import com.mircontapp.sportalbum.domain.models.CommentModel
import com.mircontapp.sportalbum.domain.models.MarcatoreModel
import com.mircontapp.sportalbum.domain.models.MatchModel
import com.mircontapp.sportalbum.domain.models.PlayerMatchModel

class PunizioneUC {
    operator fun invoke (matchModel: MatchModel): MatchModel {
        val attackers = if (matchModel.possesso == Enums.TeamPosition.HOME) matchModel.playersHome else matchModel.playersAway
        val defenders = if (matchModel.possesso == Enums.TeamPosition.HOME) matchModel.playersAway else matchModel.playersHome

        val tiratori = ArrayList<PlayerMatchModel>().also { it.addAll(attackers) }
        val tiratore1 = tiratori.removeAt(tiratori.findTiratore())
        val tiratore2 = tiratori.removeAt(tiratori.findTiratore())
        val soglia: Double = tiratore1.rig * 2.0
        val tot: Double = soglia + tiratore2.rig
        val dado = Math.random() * tot
        val tiratore = if (dado < soglia) tiratore1 else tiratore2

        defenders.isEmpty()

        var portiere: PlayerMatchModel? = defenders.find { it.isPortiere()}
        if (portiere == null && !defenders.isEmpty()) {
            portiere = defenders.get(0)
        }

        val matchModelUpdated = if (matchModel.evento == Enums.Evento.PUNIZIONE_DIRETTA) {
            punizioneDiretta(matchModel, tiratore, portiere)
        } else {
            punizioneGiocata(matchModel, tiratore, portiere)
        }
        return matchModelUpdated
    }

    private fun punizioneDiretta(matchModel: MatchModel, tiratore: PlayerMatchModel, portiere: PlayerMatchModel?): MatchModel {
        val protagonistaA = ""
        val finA = tiratore.punizioneDiretta()

        val parata = portiere?.let {
            val pot = it.por * 0.5 + it.bal * 0.25 + it.dif * 0.25
            portiere.value * 0.75 + Math.random() * pot  * 0.25
        } ?: 0.0

        val diff = parata - finA
        val context: Context = SportAlbumApplication.instance.applicationContext
        var messaggio: String? = ""

        //gol su punizione
        messaggio = if (diff < 0) {
            matchModel.fase = Enums.Fase.CENTROCAMPO
            matchModel.evento = Enums.Evento.GOAL
            if (matchModel.possesso === Enums.TeamPosition.HOME) {
                matchModel.homeScore = matchModel.homeScore + 1
            } else {
                matchModel.awayScore = matchModel.awayScore + 1
            }
            if (tiratore != null && matchModel.marcatori != null) {
                val marcatoreModel = MarcatoreModel(tiratore.name, matchModel.minute, matchModel.possesso)
                matchModel.marcatori.add(marcatoreModel)
            }
            matchModel.protagonista = protagonistaA
            matchModel.coprotagonista = portiere?.name ?: ""
            Enums.Evento.GOAL_PUNIZIONE.getTelecronaca(diff, protagonistaA)
            //vince la squadra difendente
        } else {
            matchModel.fase = Enums.Fase.CENTROCAMPO
            matchModel.evento = Enums.Evento.NONE
            matchModel.protagonista = portiere?.name
            matchModel.coprotagonista = protagonistaA
            Enums.Evento.PARATA.getTelecronaca(diff, portiere?.name ?: "")
        }
        matchModel.comment.add(CommentModel(messaggio ?: "", matchModel.minute, matchModel.possesso))
        matchModel.possesso = if (matchModel.possesso === Enums.TeamPosition.HOME) Enums.TeamPosition.AWAY else Enums.TeamPosition.HOME
        return matchModel
    }

    private fun punizioneGiocata(matchModel: MatchModel, tiratore: PlayerMatchModel, portiere: PlayerMatchModel?): MatchModel {
        val attackers = if (matchModel.possesso == Enums.TeamPosition.HOME) matchModel.playersHome else matchModel.playersAway
        val defenders = if (matchModel.possesso == Enums.TeamPosition.HOME) matchModel.playersAway else matchModel.playersHome


        var protagonistaA = ""
        var goleador: PlayerMatchModel? = null
        var finA = -1.0
        var pot = 0.0
        var dado = 0.0
        pot = tiratore.rig * 0.75 + tiratore.tec * 0.25
        var fix = tiratore.getMatchValue(matchModel.isLegend)
        finA = fix / 2 + Math.random() * pot / 2
        var protagonistaD = ""
        var difD = -1.0
        for (defender in defenders) {
            if (defender.partecipa(defender.roleLineUp.getPartCen())) {
                pot = defender.bal * 0.5 + defender.dif * 0.25 + defender.fis * 0.25
                fix = tiratore.getMatchValue(matchModel.isLegend)
                dado = fix * 0.75 + Math.random() * 0.25
                if (dado > difD) {
                    difD = dado
                    protagonistaD = defender.name
                }
            }
        }
        var diff = difD - finA
        val context: Context = SportAlbumApplication.instance.applicationContext
        var messaggio: String? = ""
        if (diff < 0) {
            finA = -1.0
            for (attacker in attackers) {
                if (attacker.partecipa(attacker.roleLineUp.getPartfin())) {
                    pot = attacker.bal * 0.5 + attacker.att * 0.25 + attacker.fin * 0.25
                    fix = tiratore.getMatchValue(matchModel.isLegend)
                    dado = fix * 0.5 + Math.random() * 0.5
                    if (dado > finA) {
                        finA = dado
                        protagonistaA = attacker.name
                        goleador = attacker
                    }
                }
            }
            portiere?.let { portiere->
                pot =portiere.por * 0.5 + portiere.bal * 0.25 + portiere.dif * 0.25
            }
            fix = tiratore.getMatchValue(matchModel.isLegend)
            val parata = fix / 8 * 7 + Math.random() * pot / 8
            diff = parata - finA
            if (diff < 0) {
                matchModel.fase = Enums.Fase.CENTROCAMPO
                matchModel.evento = Enums.Evento.GOAL

                if (matchModel.possesso === Enums.TeamPosition.HOME) {
                    matchModel.homeScore += 1
                } else {
                    matchModel.awayScore += 1
                }
                matchModel.protagonista = protagonistaA
                matchModel.coprotagonista = portiere?.name
                matchModel.marcatori.add(MarcatoreModel(protagonistaA, matchModel.minute, matchModel.possesso))
                messaggio =  Enums.Evento.GOAL_PUNIZIONE.getTelecronaca(diff, protagonistaA)

            } else {
                matchModel.fase = Enums.Fase.CENTROCAMPO
                matchModel.evento = Enums.Evento.NONE
                matchModel.protagonista = portiere?.name
                matchModel.coprotagonista = protagonistaA
                messaggio = Enums.Evento.PARATA.getTelecronaca(diff, portiere?.name ?: "")
            }
        } else {
            matchModel.fase = Enums.Fase.CENTROCAMPO
            matchModel.evento = Enums.Evento.NONE
            matchModel.protagonista = protagonistaD
            matchModel.coprotagonista = protagonistaA
            messaggio = Enums.Evento.RESPINTA.getTelecronaca(diff, protagonistaD)
        }
        matchModel.comment.add(CommentModel(messaggio ?: "", matchModel.minute, matchModel.possesso))
        matchModel.possesso = if (matchModel.possesso === Enums.TeamPosition.HOME) Enums.TeamPosition.AWAY else Enums.TeamPosition.HOME
        return matchModel
    }

}
