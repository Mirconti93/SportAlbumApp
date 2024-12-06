package com.mircontapp.sportalbum.presentation.dashboard.edit_team

import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel

sealed class EditTeamAction {
    object Load: EditTeamAction()
    data class ShowEdit(val team: TeamModel) : EditTeamAction()
    data class SaveEdit(val team: TeamModel) : EditTeamAction()
}