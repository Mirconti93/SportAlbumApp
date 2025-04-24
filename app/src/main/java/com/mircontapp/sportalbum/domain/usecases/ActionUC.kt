import android.util.Log
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.ext.partecipa
import com.mircontapp.sportalbum.domain.models.ActionModel
import com.mircontapp.sportalbum.domain.models.CommentModel
import com.mircontapp.sportalbum.domain.models.MatchModel

abstract class ActionUC() {

    operator fun invoke(matchModel: MatchModel): MatchModel {
        var protagonistaA = ""
        var attA = -1.0
        var pot = 0.0
        var dado = 0.0

        val attackers = if (matchModel.possesso == Enums.TeamPosition.HOME) matchModel.playersHome else matchModel.playersAway
        val defenders = if (matchModel.possesso == Enums.TeamPosition.HOME) matchModel.playersAway else matchModel.playersHome

        for (attacker in attackers) {
            if (attacker.partecipa(attacker.roleMatch.getPartAtt())) {
                val action = attackingAction(matchModel)
                Log.i("BUPIAZIONE:", "${matchModel.fase} ${attacker.name} $action")
                if (dado > attA) {
                    attA = dado
                    protagonistaA = attacker.name
                }
            }
        }

        var protagonistaD = ""
        var difD = -1.0
        for (defender in defenders) {
            if (defender.partecipa(defender.roleMatch.getPartDif())) {
                val action = defendingAction(matchModel)
                Log.i("BUPIAZIONE:", "${matchModel.fase} ${defender.name} $action")

                if (dado > difD) {
                    difD = dado
                    protagonistaD = defender.name
                }
            }
        }

        val actionModel = ActionModel(protagonistaA, attA, protagonistaD, difD)

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

            matchModel.possesso = (if (matchModel.possesso === Enums.TeamPosition.HOME) Enums.TeamPosition.AWAY else Enums.TeamPosition.HOME)
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

    abstract fun attackingAction(matchModel: MatchModel): Double
    abstract fun defendingAction(matchModel: MatchModel): Double
    abstract fun matchChangedAfterAction(actionModel: ActionModel): MatchModel

}