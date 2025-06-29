import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.commons.ext.genericAction
import com.mircontapp.sportalbum.commons.ext.getTelecronaca
import com.mircontapp.sportalbum.commons.ext.palleggio
import com.mircontapp.sportalbum.commons.ext.pressing
import com.mircontapp.sportalbum.commons.ext.setAmmonito
import com.mircontapp.sportalbum.commons.ext.setEspulso
import com.mircontapp.sportalbum.domain.models.ActionModel
import com.mircontapp.sportalbum.domain.models.CommentModel
import com.mircontapp.sportalbum.domain.models.MatchModel

class CentrocampoUC {

    operator fun invoke(matchModel: MatchModel): MatchModel {
        val actionAttack = matchModel.genericAction(faseAction = { player -> player.palleggio()})

        val actionDefense = matchModel.genericAction(faseAction = { player -> player.pressing()})

        val diff = actionDefense.score - actionAttack.score
        var messaggio = ""

        //vince la squadra attaccante
        if (diff < 0) {
            matchModel.fase = Enums.Fase.ATTACCO
            matchModel.protagonista = actionDefense.player
            matchModel.coprotagonista = actionDefense.player
            messaggio = Enums.Evento.PALLEGGIO.getTelecronaca(diff, actionAttack.player)
        //calcio di punizione
        } else if (diff >= 0 && diff < 2) {
            matchModel.fase = Enums.Fase.PUNIZIONE
            if (diff >= 1.6 && diff < 1.7) {
                messaggio = Enums.Evento.ESPULSIONE.getTelecronaca(diff, actionDefense.player)
                matchModel.setEspulso(actionDefense.player)
            } else if (diff >= 1.7 && diff < 2) {
                messaggio = Enums.Evento.AMMONIZIONE.getTelecronaca(diff, actionDefense.player)
                matchModel.setAmmonito(actionDefense.player)?.let { Enums.Evento.DOPPIA_AMMONIZIONE.getTelecronaca(diff, actionDefense.player) }
            }
            matchModel.protagonista = actionAttack.player
            matchModel.coprotagonista = actionDefense.player
            messaggio = messaggio + Enums.Evento.PUNIZIONE.getTelecronaca(diff, actionDefense.player)
        //vince la squadra difendente
        } else {
            matchModel.fase = Enums.Fase.CENTROCAMPO
            matchModel.protagonista = actionAttack.player
            matchModel.coprotagonista = actionDefense.player
            matchModel.possesso = if (matchModel.possesso === Enums.TeamPosition.HOME) Enums.TeamPosition.AWAY else Enums.TeamPosition.HOME
            messaggio = Enums.Evento.PRESSING.getTelecronaca(diff, actionDefense.player)
        }

        matchModel.comment.add(CommentModel(messaggio, matchModel.minute, matchModel.possesso))
        return matchModel
    }

}