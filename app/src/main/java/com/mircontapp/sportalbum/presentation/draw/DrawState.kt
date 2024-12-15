package com.mircontapp.sportalbum.presentation.draw

import com.mircontapp.sportalbum.domain.models.DrawModel

data class DrawState(val drawPhase: DrawPhase = DrawPhase.INSERT, val drawModel: DrawModel = DrawModel() )

enum class DrawPhase { INSERT, RECAP, DRAWN}