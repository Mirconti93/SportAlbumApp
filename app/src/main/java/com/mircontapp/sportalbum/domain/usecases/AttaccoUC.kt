import android.util.Log
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.ext.azione
import com.mircontapp.sportalbum.commons.ext.partecipa
import com.mircontapp.sportalbum.domain.models.CommentModel
import com.mircontapp.sportalbum.domain.models.MatchModel

class AttaccoUC {

    fun attacco(matchModel: MatchModel): MatchModel {

        val actionModel = matchModel.azione()

        var messaggio = ""
        val diff = actionModel.valoreD - actionModel.valoreA

        //vince la squadra attaccante
        if (diff < 0) {
            matchModel.fase = Enums.Fase.CONCLUSIONE
            matchModel.evento = Enums.Evento.NONE
            matchModel.protagonista = actionModel.protagonistaA
            matchModel.coprotagonista = actionModel.protagonistaD
            messaggio = if (diff < 10) {
                String.format(SportAlbumApplication.getString(R.string.telecronacaAtt1), actionModel.protagonistaA)
            } else {
                String.format(SportAlbumApplication.getString(R.string.telecronacaAtt2), actionModel.protagonistaA)
            }
            //punizione
        } else if (diff >= 0 && diff < 0.4) {
            matchModel.fase = Enums.Fase.PUNIZIONE
            if (diff >= 0.3 && diff < 0.32) {
                matchModel.evento = Enums.Evento.ESPULSIONE
                messaggio = String.format(SportAlbumApplication.getString(R.string.telecronacaEsp), actionModel.protagonistaD)
            } else if (diff >= 0.32 && diff < 0.4) {
                matchModel.evento = Enums.Evento.AMMONIZIONE
                messaggio = String.format(SportAlbumApplication.getString(R.string.telecronacaAmm), actionModel.protagonistaD)
            }
            matchModel.protagonista = actionModel.protagonistaA
            matchModel.coprotagonista = actionModel.protagonistaD

            messaggio = messaggio + " " + String.format(
                SportAlbumApplication.getString(R.string.telecronacaPun),
                actionModel.protagonistaD
            )
            //calcio di rigore
        } else if (diff >= 0.4 && diff < 0.5) {
            matchModel.fase = Enums.Fase.RIGORE
            if (diff >= 0.45 && diff < 0.47) {
                matchModel.evento = Enums.Evento.ESPULSIONE
                messaggio = String.format(SportAlbumApplication.getString(R.string.telecronacaEsp), actionModel.protagonistaD)
            } else if (diff >= 0.47 && diff < 0.5) {
                matchModel.evento = Enums.Evento.AMMONIZIONE
                messaggio = String.format(SportAlbumApplication.getString(R.string.telecronacaAmm), actionModel.protagonistaD)
            }
            matchModel.protagonista = actionModel.protagonistaA
            matchModel.coprotagonista = actionModel.protagonistaD
            messaggio = messaggio + String.format(SportAlbumApplication.getString(R.string.telecronacaRig), actionModel.protagonistaA)
            //vince la squadra difendente
        } else {
            matchModel.fase = Enums.Fase.CENTROCAMPO
            matchModel.evento = Enums.Evento.NONE
            matchModel.protagonista = actionModel.protagonistaD
            matchModel.coprotagonista = actionModel.protagonistaA

            matchModel.possesso = (if (matchModel.possesso === Enums.TeamPosition.HOME) Enums.TeamPosition.AWAY else Enums.TeamPosition.HOME)
            messaggio = if (diff < 5) {
                String.format(SportAlbumApplication.getString(R.string.telecronacaDif1), actionModel.protagonistaD)
            } else if (diff < 10) {
                String.format(SportAlbumApplication.getString(R.string.telecronacaDif2), actionModel.protagonistaD)
            } else {
                String.format(SportAlbumApplication.getString(R.string.telecronacaDif3), actionModel.protagonistaD)
            }
        }

        matchModel.comment.add(CommentModel(messaggio, matchModel.minute, matchModel.possesso))
        return matchModel
    }
}