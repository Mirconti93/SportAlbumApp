package com.mircontapp.sportalbum.presentation.commons

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
@HiltViewModel
class VideosViewModel @Inject constructor() : ViewModel() {
    val links : StateFlow<List<String>> get() =  _links
    private val _links: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
}