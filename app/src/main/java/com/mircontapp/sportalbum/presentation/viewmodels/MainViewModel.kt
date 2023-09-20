package com.mircontapp.sportalbum.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mircontapp.sportalbum.presentation.navigation.NavigationItem

class MainViewModel: ViewModel() {

    val routeSelected: MutableLiveData<NavigationItem> = MutableLiveData()


}