import android.util.Log
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.ext.azione
import com.mircontapp.sportalbum.commons.ext.partecipa
import com.mircontapp.sportalbum.domain.models.CommentModel
import com.mircontapp.sportalbum.domain.models.MatchModel

class CentrocampoUC() {

    fun centrocampo(matchModel: MatchModel): MatchModel {
        val azione = matchModel.azione()

        val context =  SportAlbumApplication.instance.getBaseContext()
        var messaggio = ""
        val diff = azione.valoreD - azione.valoreA
        val protagonistaA = azione.protagonistaA
        val protagonistaD = azione.protagonistaD

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
                if (matchModel.possesso === Enums.TeamPosition.HOME) Enums.TeamPosition.AWAY else Enums.TeamPosition.HOME
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