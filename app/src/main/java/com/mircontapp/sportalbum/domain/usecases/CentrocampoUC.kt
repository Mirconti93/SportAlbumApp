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
        val actionModel = matchModel.azione()

        var messaggio = ""
        val diff = actionModel.valoreD - actionModel.valoreA

        //vince la squadra attaccante
        if (diff < 0) {
            matchModel.fase = Enums.Fase.ATTACCO
            matchModel.evento = Enums.Evento.NONE
            matchModel.protagonista = actionModel.protagonistaA
            matchModel.coprotagonista = actionModel.protagonistaD
            messaggio = if (diff > -8) {
                String.format(SportAlbumApplication.getString(R.string.telecronacaCen1), actionModel.protagonistaA)
            } else if (diff > -15) {
                String.format(SportAlbumApplication.getString(R.string.telecronacaCen2), actionModel.protagonistaA)
            } else {
                String.format(SportAlbumApplication.getString(R.string.telecronacaCen3), actionModel.protagonistaA)
            }
            //calcio di punizione
        } else if (diff >= 0 && diff < 2) {
            matchModel.fase = Enums.Fase.PUNIZIONE
            if (diff >= 1.6 && diff < 1.7) {
                matchModel.evento = Enums.Evento.ESPULSIONE
                messaggio = String.format(SportAlbumApplication.getString(R.string.telecronacaEsp), actionModel.protagonistaD)
            } else if (diff >= 1.7 && diff < 2) {
                matchModel.evento = Enums.Evento.AMMONIZIONE
                messaggio = String.format(SportAlbumApplication.getString(R.string.telecronacaAmm), actionModel.protagonistaD)
            }
            matchModel.protagonista = actionModel.protagonistaA
            matchModel.coprotagonista = actionModel.protagonistaD
            messaggio = messaggio + " " + String.format(
                SportAlbumApplication.getString(R.string.telecronacaPun),
                actionModel.protagonistaD
            )
            //vince la squadra difendente
        } else {
            matchModel.fase = Enums.Fase.CENTROCAMPO
            matchModel.evento = Enums.Evento.RECUPERO
            matchModel.protagonista = actionModel.protagonistaD
            matchModel.coprotagonista = actionModel.protagonistaA
            matchModel.possesso =
                if (matchModel.possesso === Enums.TeamPosition.HOME) Enums.TeamPosition.AWAY else Enums.TeamPosition.HOME
            messaggio = if (diff > 15) {
                String.format(SportAlbumApplication.getString(R.string.telecronacaBal3), actionModel.protagonistaD)
            } else if (diff > 8) {
                String.format(SportAlbumApplication.getString(R.string.telecronacaBal2), actionModel.protagonistaD)
            } else {
                String.format(SportAlbumApplication.getString(R.string.telecronacaBal1), actionModel.protagonistaD)
            }
        }

        matchModel.comment.add(CommentModel(messaggio, matchModel.minute, matchModel.possesso))
        return matchModel
    }

}