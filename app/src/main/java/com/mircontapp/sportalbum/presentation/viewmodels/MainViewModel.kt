package com.mircontapp.sportalbum.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mircontapp.sportalbum.presentation.navigation.NavigationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel()  {

    val routeSelected: MutableLiveData<NavigationItem> = MutableLiveData()

}