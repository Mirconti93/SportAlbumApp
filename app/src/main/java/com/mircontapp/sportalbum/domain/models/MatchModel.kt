package com.mircontapp.sportalbum.domain.models

import com.mirco.sportalbum.utils.Enums

data class MatchModel(
    var home: String,
    var away: String,
    var homeScore: Int,
    var awayScore: Int,
    var minute: Int,
    var possesso: Enums.Possesso,
    var fase: Enums.Fase,
    var evento: Enums.Evento,
    var isLegend: Boolean,
    var playersHome: MutableList<PlayerMatchModel>,
    var playersAway: MutableList<PlayerMatchModel>,
    var comment: MutableList<String>,
    var protagonista: String?,
    var coprotagonista: String?
)
