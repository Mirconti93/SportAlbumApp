package com.mircontapp.sportalbum.domain.repository

import com.mircontapp.sportalbum.domain.models.TeamModel

interface TeamsRepository {
    fun getAllTeams(): List<TeamModel>
}