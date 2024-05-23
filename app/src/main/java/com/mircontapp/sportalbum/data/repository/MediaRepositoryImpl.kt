package com.mircontapp.sportalbum.data.repository

import com.mircontapp.sportalbum.domain.datasource.MediaDataSource
import com.mircontapp.sportalbum.domain.models.MediaModel
import com.mircontapp.sportalbum.domain.repository.MediaRepository

class MediaRepositoryImpl(val mediaDataSource: MediaDataSource) : MediaRepository {
    override suspend fun fetchMedia(): List<MediaModel> {
        return mediaDataSource.fetchMedias() ?: emptyList()
    }

    override suspend fun videosFromName(name: String): List<String> {
        return fetchMedia().find { it.name == name}.let {
            it?.links ?: emptyList()
        }
    }

}