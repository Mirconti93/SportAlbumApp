import android.content.Context
import android.util.Log
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.MatchHelper
import com.mircontapp.sportalbum.commons.PlayerHelper
import com.mircontapp.sportalbum.domain.models.CommentModel
import com.mircontapp.sportalbum.domain.models.MarcatoreModel
import com.mircontapp.sportalbum.domain.models.MatchModel
import com.mircontapp.sportalbum.domain.models.PlayerMatchModel
import com.mircontapp.sportalbum.domain.models.TiratoreModel

class AttaccoUC() {

    fun attacco(matchModel: MatchModel): MatchModel {
        var protagonistaA = ""
        var attA = -1.0
        var pot = 0.0
        var dado = 0.0

        val attackers = if (matchModel.possesso == Enums.Possesso.HOME) matchModel.playersHome else matchModel.playersAway
        val defenders = if (matchModel.possesso == Enums.Possesso.HOME) matchModel.playersAway else matchModel.playersHome

        for (attacker in attackers) {
            if (MatchHelper.partecipa(attacker, attacker.roleMatch.getPartAtt())) {
                pot = attacker.att / 4.0 + attacker.dri / 4.0 + attacker.tec / 4.0 + attacker.vel / 4.0
                val fixed = if (matchModel.isLegend) attacker.valueleg?.toDouble() ?: 0.0 else attacker.value?.toDouble() ?: 0.0
                dado = fixed * 0.25 + Math.random() * pot * 0.75
                Log.i("BUPIAZIONE:", "att att "+ attacker.name + " " +  dado)

                if (dado > attA) {
                    attA = dado
                    protagonistaA = attacker.name
                }
            }
        }

        var protagonistaD = ""
        var difD = -1.0
        for (defender in defenders) {
            if (MatchHelper.partecipa(defender, defender.roleMatch.getPartDif())) {
                pot = defender.dif / 4.0 + defender.bal / 4.0 + defender.fis / 4.0 + defender.vel / 4.0
                val fixed = if (matchModel.isLegend) defender.valueleg?.toDouble() ?: 0.0 else defender.value?.toDouble() ?: 0.0
                dado = fixed * 0.25 + pot * 0.25 + Math.random() * pot * 0.5
                Log.i("BUPIAZIONE:", "att dif "+ defender.name + " " +  dado)

                if (dado > difD) {
                    difD = dado
                    protagonistaD = defender.name
                }
            }
        }

        val context =  SportAlbumApplication.instance.getBaseContext()
        var messaggio = ""
        val diff = difD - attA

        //vince la squadra attaccante
        if (diff < 0) {
            matchModel.fase = Enums.Fase.CONCLUSIONE
            matchModel.evento = Enums.Evento.NONE
            matchModel.protagonista = protagonistaA
            matchModel.coprotagonista = protagonistaD
            messaggio = if (diff < 10) {
                String.format(context.getString(R.string.telecronacaAtt1), protagonistaA)
            } else {
                String.format(context.getString(R.string.telecronacaAtt2), protagonistaA)
            }
            //punizione
        } else if (diff >= 0 && diff < 0.4) {
            matchModel.fase = Enums.Fase.PUNIZIONE
            if (diff >= 0.3 && diff < 0.32) {
                matchModel.evento = Enums.Evento.ESPULSIONE
                messaggio = String.format(context.getString(R.string.telecronacaEsp), protagonistaD)
            } else if (diff >= 0.32 && diff < 0.4) {
                matchModel.evento = Enums.Evento.AMMONIZIONE
                messaggio = String.format(context.getString(R.string.telecronacaAmm), protagonistaD)
            }
            matchModel.protagonista = protagonistaA
            matchModel.coprotagonista = protagonistaD

            messaggio = messaggio + " " + String.format(
                context.getString(R.string.telecronacaPun),
                protagonistaD
            )
            //calcio di rigore
        } else if (diff >= 0.4 && diff < 0.5) {
            matchModel.fase = Enums.Fase.RIGORE
            if (diff >= 0.45 && diff < 0.47) {
                matchModel.evento = Enums.Evento.ESPULSIONE
                messaggio = String.format(context.getString(R.string.telecronacaEsp), protagonistaD)
            } else if (diff >= 0.47 && diff < 0.5) {
                matchModel.evento = Enums.Evento.AMMONIZIONE
                messaggio = String.format(context.getString(R.string.telecronacaAmm), protagonistaD)
            }
            matchModel.protagonista = protagonistaA
            matchModel.coprotagonista = protagonistaD
            messaggio = messaggio + String.format(context.getString(R.string.telecronacaRig), protagonistaA)
            //vince la squadra difendente
        } else {
            matchModel.fase = Enums.Fase.CENTROCAMPO
            matchModel.evento = Enums.Evento.NONE
            matchModel.protagonista = protagonistaD
            matchModel.coprotagonista = protagonistaA

            matchModel.possesso = (if (matchModel.possesso === Enums.Possesso.HOME) Enums.Possesso.AWAY else Enums.Possesso.HOME)
            messaggio = if (diff < 5) {
                String.format(context.getString(R.string.telecronacaDif1), protagonistaD)
            } else if (diff < 10) {
                String.format(context.getString(R.string.telecronacaDif2), protagonistaD)
            } else {
                String.format(context.getString(R.string.telecronacaDif3), protagonistaD)
            }
        }

        matchModel.comment.add(CommentModel(messaggio, matchModel.minute, matchModel.possesso))
        return matchModel
    }
}