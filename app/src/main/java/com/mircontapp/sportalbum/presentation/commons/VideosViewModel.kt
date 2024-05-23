package com.mircontapp.sportalbum.presentation.commons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mircontapp.sportalbum.domain.datasource.MediaDataSource
import com.mircontapp.sportalbum.domain.usecases.GetVideosByNameUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class VideosViewModel @Inject constructor(val getVideosByNameUC: GetVideosByNameUC) : ViewModel() {
    val links : StateFlow<List<String>> get() =  _links
    private val _links: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())

    fun getVideosByName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = getVideosByNameUC.getVideosByName(name = name)
            withContext(Dispatchers.Main) {
                _links.value = list
            }
        }
    }
}