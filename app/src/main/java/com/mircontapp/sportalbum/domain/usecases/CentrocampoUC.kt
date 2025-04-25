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

class CentrocampoUC : ActionUC() {

    override fun attackingAction(matchModel: MatchModel): ActionModel {
        val attackers = if (matchModel.possesso == Enums.TeamPosition.HOME) matchModel.playersHome else matchModel.playersAway
        return matchModel.genericAction(attackers,
            faseAction = { player -> player.palleggio()})
    }

    override fun defendingAction(matchModel: MatchModel): ActionModel {
        val attackers = if (matchModel.possesso == Enums.TeamPosition.HOME) matchModel.playersHome else matchModel.playersAway
        return matchModel.genericAction(attackers,
            faseAction = { player -> player.pressing()})
    }

    override fun handleMatchAfterAction(matchModel: MatchModel, actionAttack: ActionModel, actionDefense: ActionModel): MatchModel {
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