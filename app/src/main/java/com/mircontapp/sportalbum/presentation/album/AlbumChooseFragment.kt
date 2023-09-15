package com.mircontapp.sportalbum.presentation.album

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mircontapp.sportalbum.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumChooseFragment : Fragment() {

    private val albumChooseVM: AlbumChooseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album_choose, container, false)
    }


}