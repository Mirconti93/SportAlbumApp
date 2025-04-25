import android.util.Log
import com.mirco.sportalbum.utils.Enums
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.commons.ext.partecipa
import com.mircontapp.sportalbum.domain.models.ActionModel
import com.mircontapp.sportalbum.domain.models.CommentModel
import com.mircontapp.sportalbum.domain.models.MatchModel
import com.mircontapp.sportalbum.domain.models.PlayerMatchModel

abstract class ActionUC {

    operator fun invoke(matchModel: MatchModel): MatchModel {
        val actionAttack = attackingAction(matchModel)
        val actionDefense = defendingAction(matchModel)
        return handleMatchAfterAction(matchModel, actionAttack, actionDefense)
    }

    abstract fun attackingAction(matchModel: MatchModel): ActionModel
    abstract fun defendingAction(matchModel: MatchModel): ActionModel
    abstract fun handleMatchAfterAction(matchModel: MatchModel, actionAttack: ActionModel, actionDefense: ActionModel): MatchModel

}

