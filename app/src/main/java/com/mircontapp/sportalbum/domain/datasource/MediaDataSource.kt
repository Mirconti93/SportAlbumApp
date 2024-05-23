package com.mircontapp.sportalbum.domain.datasource

import com.mircontapp.sportalbum.domain.models.MediaModel
import com.mircontapp.sportalbum.domain.models.PlayerModel
import com.mircontapp.sportalbum.domain.models.TeamModel

interface MediaDataSource {
    abstract suspend fun fetchMedias(): List<MediaModel>?

}