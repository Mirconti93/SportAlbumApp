package com.mircontapp.sportalbum.presentation.album

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mircontapp.sportalbum.R

class AlbumChooseFragment : Fragment() {

    companion object {
        fun newInstance() = AlbumChooseFragment()
    }

    private lateinit var viewModel: AlbumChooseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album_choose, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AlbumChooseViewModel::class.java)
        // TODO: Use the ViewModel
    }

}