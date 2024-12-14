package com.mircontapp.sportalbum.presentation.draw

import com.mircontapp.sportalbum.domain.models.DrawModel

data class DrawState(val beforeDraw: Boolean = true, val drawModel: DrawModel = DrawModel() )