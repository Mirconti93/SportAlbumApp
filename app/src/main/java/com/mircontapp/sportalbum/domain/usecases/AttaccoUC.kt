import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.commons.ext.attacco
import com.mircontapp.sportalbum.commons.ext.difesa
import com.mircontapp.sportalbum.commons.ext.genericAction
import com.mircontapp.sportalbum.commons.ext.getTelecronaca
import com.mircontapp.sportalbum.commons.ext.setAmmonito
import com.mircontapp.sportalbum.commons.ext.setEspulso
import com.mircontapp.sportalbum.domain.models.ActionModel
import com.mircontapp.sportalbum.domain.models.CommentModel
import com.mircontapp.sportalbum.domain.models.MatchModel

class AttaccoUC : ActionUC() {

    override fun attackingAction(matchModel: MatchModel): ActionModel {
        val attackers = if (matchModel.possesso == Enums.TeamPosition.HOME) matchModel.playersHome else matchModel.playersAway
        return matchModel.genericAction(attackers,
            faseAction = { player -> player.attacco()})
    }

    override fun defendingAction(matchModel: MatchModel): ActionModel {
        val attackers = if (matchModel.possesso == Enums.TeamPosition.HOME) matchModel.playersHome else matchModel.playersAway
        return matchModel.genericAction(attackers,
            faseAction = { player -> player.difesa()})
    }

    override fun handleMatchAfterAction(
        matchModel: MatchModel,
        actionAttack: ActionModel,
        actionDefense: ActionModel
    ): MatchModel {
        var messaggio = ""
        val diff = actionDefense.score - actionAttack.score

        //vince la squadra attaccante
        if (diff < 0) {
            matchModel.fase = Enums.Fase.CONCLUSIONE
            matchModel.evento = Enums.Evento.NONE
            matchModel.protagonista = actionAttack.player
            matchModel.coprotagonista = actionDefense.player
            messaggio = Enums.Evento.ATTACCO.getTelecronaca(diff, actionAttack.player)
            //punizione
        } else if (diff >= 0 && diff < 0.4) {
            matchModel.fase = Enums.Fase.PUNIZIONE
            if (diff >= 0.3 && diff < 0.32) {
                messaggio = Enums.Evento.ESPULSIONE.getTelecronaca(diff, actionDefense.player)
                matchModel.setEspulso(actionDefense.player)
            } else if (diff >= 0.32 && diff < 0.4) {
                messaggio = Enums.Evento.AMMONIZIONE.getTelecronaca(diff, actionDefense.player)
                matchModel.setAmmonito(actionDefense.player)?.let { Enums.Evento.DOPPIA_AMMONIZIONE.getTelecronaca(diff, actionDefense.player) }
            }
            matchModel.protagonista = actionDefense.player
            matchModel.coprotagonista = actionDefense.player

            messaggio = messaggio + Enums.Evento.PUNIZIONE.getTelecronaca(diff, actionDefense.player)
            //calcio di rigore
        } else if (diff >= 0.4 && diff < 0.5) {
            matchModel.fase = Enums.Fase.RIGORE
            if (diff >= 0.45 && diff < 0.47) {
                messaggio = Enums.Evento.ESPULSIONE.getTelecronaca(diff, actionDefense.player)
                matchModel.setEspulso(actionDefense.player)
            } else if (diff >= 0.47 && diff < 0.5) {
                messaggio = Enums.Evento.AMMONIZIONE.getTelecronaca(diff, actionDefense.player)
                matchModel.setAmmonito(actionDefense.player)?.let { Enums.Evento.DOPPIA_AMMONIZIONE.getTelecronaca(diff, actionDefense.player) }
            }
            matchModel.protagonista = actionAttack.player
            matchModel.coprotagonista = actionDefense.player
            messaggio = messaggio + Enums.Evento.RIGORE.getTelecronaca(diff, actionAttack.player)
            //vince la squadra difendente
        } else {
            matchModel.fase = Enums.Fase.CENTROCAMPO
            matchModel.evento = Enums.Evento.NONE
            matchModel.protagonista = actionAttack.player
            matchModel.coprotagonista = actionDefense.player

            matchModel.possesso = (if (matchModel.possesso === Enums.TeamPosition.HOME) Enums.TeamPosition.AWAY else Enums.TeamPosition.HOME)
            messaggio = Enums.Evento.RECUPERO.getTelecronaca(diff, actionDefense.player)
        }

        matchModel.comment.add(CommentModel(messaggio, matchModel.minute, matchModel.possesso))
        return matchModel
    }

}