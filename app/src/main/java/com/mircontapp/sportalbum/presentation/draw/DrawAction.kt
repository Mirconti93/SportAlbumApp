package com.mircontapp.sportalbum.presentation.draw

import com.mircontapp.sportalbum.domain.models.DrawModel

sealed class DrawAction {
    data class AddTeam(val name: String): DrawAction()
    data class Draw(val drawModel: DrawModel) : DrawAction()
}