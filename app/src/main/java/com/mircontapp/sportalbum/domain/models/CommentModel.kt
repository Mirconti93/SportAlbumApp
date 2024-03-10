package com.mircontapp.sportalbum.domain.models

import com.mirco.sportalbum.utils.Enums

data class CommentModel(
    val text: String,
    val minute: Int,
    val possesso: Enums.Possesso
)
