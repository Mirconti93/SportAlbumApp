package com.mircontapp.sportalbum.presentation.bupi

import androidx.lifecycle.ViewModel
import com.mircontapp.sportalbum.domain.repository.BupiPlayersRepository
import com.mircontapp.sportalbum.domain.repository.BupiTeamsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BupiViewModel @Inject constructor(
    val bupiPlayersRepository: BupiPlayersRepository,
    val bupiTeamsRepository: BupiTeamsRepository
) : ViewModel() {

}