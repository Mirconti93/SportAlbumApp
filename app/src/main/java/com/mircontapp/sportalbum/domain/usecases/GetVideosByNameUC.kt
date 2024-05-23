package com.mircontapp.sportalbum.domain.usecases

import com.mircontapp.sportalbum.domain.models.MediaModel
import com.mircontapp.sportalbum.domain.repository.MediaRepository
import javax.inject.Inject

class GetVideosByNameUC @Inject constructor(val repository: MediaRepository) {
    suspend fun getVideosByName(name: String) : List<String> {
        return repository.videosFromName(name)
    }
}