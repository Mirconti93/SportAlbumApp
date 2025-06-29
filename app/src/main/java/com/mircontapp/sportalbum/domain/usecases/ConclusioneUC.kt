import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.commons.ext.genericAction
import com.mircontapp.sportalbum.commons.ext.getTelecronaca
import com.mircontapp.sportalbum.commons.ext.parata
import com.mircontapp.sportalbum.commons.ext.respinta
import com.mircontapp.sportalbum.commons.ext.setEspulso
import com.mircontapp.sportalbum.commons.ext.tiro
import com.mircontapp.sportalbum.domain.models.ActionModel
import com.mircontapp.sportalbum.domain.models.CommentModel
import com.mircontapp.sportalbum.domain.models.MarcatoreModel
import com.mircontapp.sportalbum.domain.models.MatchModel

class ConclusioneUC {


    operator fun invoke(matchModel: MatchModel, ): MatchModel {

        val actionAttack = matchModel.genericAction(faseAction = { player -> player.tiro()})

        val defenders = if (matchModel.possesso == Enums.TeamPosition.HOME) matchModel.playersAway else matchModel.playersHome
        var difPower = 1.0
        var part = matchModel.genericAction(faseAction = { player -> player.respinta()})
        val portiere = defenders.find { playerMatchModel -> playerMatchModel.roleMatch == Enums.RoleLineUp.PTC} ?: defenders.get(0)
        val actionDefense = ActionModel(portiere.name, portiere.parata(difPower))

        var diff = actionDefense.score - actionAttack.score
        if (diff < 0) {
            val portiere = defenders.find { playerMatchModel -> playerMatchModel.roleMatch == Enums.RoleLineUp.PTC} ?: defenders.get(0)
            val parata = portiere.parata(actionDefense.score)
            diff = parata - actionAttack.score
        }

        var messaggio = "" //segna l'attaccante
        if (diff < 0) {
            matchModel.fase = Enums.Fase.CENTROCAMPO
            matchModel.evento = Enums.Evento.GOAL
            if (matchModel.possesso === Enums.TeamPosition.HOME) {
                matchModel.homeScore += 1
            } else {
                matchModel.awayScore += 1
            }
            matchModel.protagonista = actionAttack.player
            matchModel.coprotagonista = actionDefense.player
            matchModel.marcatori.add(MarcatoreModel(actionAttack.player, matchModel.minute, matchModel.possesso))
            messaggio = Enums.Evento.GOAL.getTelecronaca(diff, actionDefense.player)
            matchModel.possesso = if (matchModel.possesso === Enums.TeamPosition.HOME) Enums.TeamPosition.AWAY else Enums.TeamPosition.HOME

            //è rigore
        } else if (diff >= 0 && diff < 0.25) {
            matchModel.fase = Enums.Fase.RIGORE
            if (diff >= 0.2 && diff < 0.25) {
                messaggio = Enums.Evento.ESPULSIONE.getTelecronaca(diff, actionDefense.player)
                matchModel.setEspulso(actionDefense.player)
            }
            matchModel.protagonista = actionAttack.player
            matchModel.coprotagonista = actionDefense.player
            messaggio = messaggio + " " + Enums.Evento.RIGORE.getTelecronaca(diff, actionAttack.player)
            //vince la squadra difendente
        } else {
            matchModel.fase = Enums.Fase.CENTROCAMPO
            matchModel.evento = Enums.Evento.NONE
            matchModel.protagonista = actionDefense.player
            matchModel.coprotagonista = actionAttack.player
            messaggio = Enums.Evento.PARATA.getTelecronaca(diff, actionDefense.player)
            matchModel.possesso = (if (matchModel.possesso === Enums.TeamPosition.HOME) Enums.TeamPosition.AWAY else Enums.TeamPosition.HOME)
        }
        matchModel.comment.add(CommentModel(messaggio, matchModel.minute, matchModel.possesso))
        return matchModel
    }

}