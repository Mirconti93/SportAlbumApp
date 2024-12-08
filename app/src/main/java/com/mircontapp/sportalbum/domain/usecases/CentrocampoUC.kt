import android.util.Log
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.ext.partecipa
import com.mircontapp.sportalbum.domain.models.CommentModel
import com.mircontapp.sportalbum.domain.models.MatchModel

class CentrocampoUC() {

    fun centrocampo(matchModel: MatchModel): MatchModel {
        var protagonistaA = ""
        var cenA = -1.0
        var pot = 0.0
        var fixed = 0.0
        var dado = 0.0

        val attackers =
            if (matchModel.possesso == Enums.Possesso.HOME) matchModel.playersHome else matchModel.playersAway
        val defenders =
            if (matchModel.possesso == Enums.Possesso.HOME) matchModel.playersAway else matchModel.playersHome

        //centrocampo azione offensiva
        for (attacker in attackers) {
            if (attacker.partecipa(attacker.roleMatch.getPartCen())) {
                pot = attacker.tec / 2.0 + attacker.dri / 4.0 + attacker.vel / 4.0
                fixed = if (matchModel.isLegend) attacker.valueleg?.toDouble()
                    ?: 0.0 else attacker.value?.toDouble() ?: 0.0
                dado = fixed / 2.0 + Math.random() * pot / 2.0
                Log.i("BUPIAZIONE:", "cen att " + attacker.name + " " + dado)
                if (dado > cenA) {
                    cenA = dado
                    protagonistaA = attacker.name
                }
            }
        }

        //centrocampo azione difensiva
        var protagonistaD = ""
        var cenD = -1.0
        for (defender in defenders) {
            if (defender.partecipa(defender.roleLineUp.getPartCen())) {
                pot = defender.dif / 2.0 + defender.bal / 4.0 + defender.vel / 4.0
                fixed = if (matchModel.isLegend) defender.valueleg?.toDouble()
                    ?: 0.0 else defender.value?.toDouble() ?: 0.0
                dado = fixed / 2.0 + Math.random() * pot / 2.0
                Log.i("BUPIAZIONE:", "cen dif " + defender.name + " " + dado)
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
            matchModel.coprotagonista = protagonistaD
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
            matchModel.protagonista = protagonistaA
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
            matchModel.coprotagonista = protagonistaA
            matchModel.possesso =
                if (matchModel.possesso === Enums.Possesso.HOME) Enums.Possesso.AWAY else Enums.Possesso.HOME
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