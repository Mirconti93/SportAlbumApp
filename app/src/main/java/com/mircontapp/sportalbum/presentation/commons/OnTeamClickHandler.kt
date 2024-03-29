package com.mircontapp.sportalbum.presentation.commons

import com.mircontapp.sportalbum.domain.models.TeamModel

interface OnTeamClickHandler {
    fun onTeamClick(teamModel: TeamModel)
}