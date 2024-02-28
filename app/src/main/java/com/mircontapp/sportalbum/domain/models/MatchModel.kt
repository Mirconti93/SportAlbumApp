package com.mircontapp.sportalbum.domain.models

import com.mirco.sportalbum.utils.Enums

data class MatchModel(
    val home: String,
    val away: String,
    val homeScore: Int,
    val awayScore: Int,
    val minute: Int,
    var possesso: Enums.Possesso,
    var fase: Enums.Fase,
    var evento: Enums.Evento,
    val isLegend: Boolean,
    val playersHome: MutableList<PlayerMatchModel>,
    val playersAway: MutableList<PlayerMatchModel>,
    val comment: MutableList<String>,
    var protagonista: String?,
    var coprotagonista: String?
)
