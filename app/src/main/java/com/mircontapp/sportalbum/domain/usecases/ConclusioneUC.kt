import android.content.Context
import android.util.Log
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.ext.azione
import com.mircontapp.sportalbum.commons.ext.parata
import com.mircontapp.sportalbum.commons.ext.partecipa
import com.mircontapp.sportalbum.domain.models.CommentModel
import com.mircontapp.sportalbum.domain.models.MarcatoreModel
import com.mircontapp.sportalbum.domain.models.MatchModel
import com.mircontapp.sportalbum.domain.models.PlayerMatchModel

class ConclusioneUC() {
    fun conclusione(matchModel: MatchModel): MatchModel {
        var protagonistaA = ""

        val attackers = if (matchModel.possesso == Enums.TeamPosition.HOME) matchModel.playersHome else matchModel.playersAway
        val defenders = if (matchModel.possesso == Enums.TeamPosition.HOME) matchModel.playersAway else matchModel.playersHome


        var finA = -1.0
        var pot = 0.0
        var dado = 0.0
        var goleador : PlayerMatchModel? = null
        for (attacker in attackers) {
            if (attacker.partecipa(attacker.roleMatch.getPartfin())) {

                pot = attacker.fin / 2.0 + attacker.att / 4.0 + attacker.vel / 4.0
                val fixed = if (matchModel.isLegend) attacker.valueleg?.toDouble() ?: 0.0 else attacker.value?.toDouble() ?: 0.0
                dado = fixed * 0.25 + Math.random() * pot  * 0.75
                Log.i("BUPIAZIONE:", "fin att "+ attacker.name + " " +  dado)

                if (dado > finA) {
                    Log.i("GOLEADOR:", "goleador:"+ goleador?.name + " " +  dado)
                    finA = dado
                    protagonistaA = attacker.name
                    goleador = attacker
                }
            }
        }
        var difPower = 1.0
        var part = 0.0
        for (defender in defenders) {
            val partecipa = Math.random() * 100.0
            if (defender.role !== Enums.Role.PT && partecipa > defender.roleMatch.partDif && !defender.isEspulso) {
                pot = defender.dif / 4.0 + defender.bal / 4.0 +defender.fis / 4.0 + defender.vel / 4.0
                dado = pot * 0.25 + Math.random() * pot  * 0.75

                difPower += dado
                part++
            }
        }
        if (part > 0) {
            difPower = difPower / part / 2.0
        }
        Log.i("BUPIAZIONE:", "fin dif " +  difPower)

        val portiere = defenders.find { playerMatchModel -> playerMatchModel.roleMatch == Enums.RoleLineUp.PTC} ?: defenders.get(0)
        val parata = portiere.parata(difPower)
        Log.i("BUPIAZIONE:", "fin por "+ portiere.name + " " +  parata)

        val context: Context = SportAlbumApplication.instance.applicationContext
        var messaggio = ""
        Log.i("TENTATIVO:", "parata:"+ parata + " finA:" +  finA)
        val diff = parata - finA

        //segna l'attaccante
        if (diff < 0) {
            matchModel.fase = Enums.Fase.CENTROCAMPO
            matchModel.evento = Enums.Evento.GOAL
            if (matchModel.possesso === Enums.TeamPosition.HOME) {
                matchModel.homeScore += 1
            } else {
                matchModel.awayScore += 1
            }
            matchModel.protagonista = protagonistaA
            matchModel.coprotagonista = portiere.name
            matchModel.marcatori.add(MarcatoreModel(protagonistaA, matchModel.minute, matchModel.possesso))
            messaggio = if (diff < 0 && diff >= -4) {
                String.format(
                    context.getString(R.string.telecronacaGol1),
                    goleador?.name
                )
            } else if (diff < -4 && diff >= -8) {
                String.format(
                    context.getString(R.string.telecronacaGol2),
                    goleador?.name
                )
            } else {
                String.format(
                    context.getString(R.string.telecronacaGol3),
                    goleador?.name
                )
            }

            matchModel.possesso = if (matchModel.possesso === Enums.TeamPosition.HOME) Enums.TeamPosition.AWAY else Enums.TeamPosition.HOME

            //è rigore
        } else if (diff >= 0 && diff < 0.25) {
            matchModel.fase = Enums.Fase.RIGORE
            if (diff >= 0.2 && diff < 0.25) {
                matchModel.evento = Enums.Evento.ESPULSIONE
                messaggio = String.format(context.getString(R.string.telecronacaEsp), portiere)
            }
            matchModel.protagonista = protagonistaA
            matchModel.coprotagonista = portiere.name
            messaggio = messaggio + " " + String.format(
                context.getString(R.string.telecronacaRig),
                goleador?.name
            )
            //vince la squadra difendente
        } else {
            matchModel.fase = Enums.Fase.CENTROCAMPO
            matchModel.evento = Enums.Evento.NONE
            matchModel.protagonista = portiere.name
            matchModel.coprotagonista = protagonistaA
            messaggio = if (diff >= 1 && diff < 2) {
                String.format(
                    context.getString(R.string.telecronacaPal),
                    goleador?.name
                )
            } else if (diff >= 2 && diff < 3) {
                String.format(
                    context.getString(R.string.telecronacaTra),
                    goleador?.name
                )
            } else if (diff >= 3 && diff < 5) {
                String.format(
                    context.getString(R.string.telecronacaPar1),
                    goleador?.name,
                    portiere?.name
                )
            } else if (diff >= 5 && diff < 7) {
                String.format(
                    context.getString(R.string.telecronacaPar2),
                    goleador?.name,
                    portiere?.name
                )
            } else if (diff >= 7 && diff < 9) {
                String.format(
                    context.getString(R.string.telecronacaOut1),
                    goleador?.name
                )
            } else {
                String.format(
                    context.getString(R.string.telecronacaOut2),
                    goleador?.name
                )
            }
            matchModel.possesso = (if (matchModel.possesso === Enums.TeamPosition.HOME) Enums.TeamPosition.AWAY else Enums.TeamPosition.HOME)
        }
        matchModel.comment.add(CommentModel(messaggio, matchModel.minute, matchModel.possesso))
        return matchModel
    }

}