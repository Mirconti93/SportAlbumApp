package com.mircontapp.sportalbum.domain.repository

import com.mircontapp.sportalbum.domain.models.MediaModel

interface MediaRepository {
    suspend fun fetchMedia(): List<MediaModel>
    suspend fun videosFromName(name: String): List<String>

}