package com.mircontapp.sportalbum.domain.usecases

import com.mircontapp.sportalbum.domain.models.DrawModel

class DoDrawUC(val drawModel: DrawModel) {

    operator fun invoke(): DrawModel {
        return DrawModel(list = drawModel.list.shuffled())
    }

}