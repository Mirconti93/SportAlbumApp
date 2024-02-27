package com.mircontapp.sportalbum.domain.models

data class MatchModel(
    val home: String,
    val away: String,
    val homeScore: Int,
    val awayScore: Int,
)