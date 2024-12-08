package com.mircontapp.sportalbum.data.datasource

import android.content.Context
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.mircontapp.sportalbum.domain.datasource.MediaDataSource
import com.mircontapp.sportalbum.domain.models.MediaModel


class FirebaseDataSource(context: Context) : MediaDataSource {
    private lateinit var database: DatabaseReference

    private final val MEDIA_TABLE = "media"

// ...
    init {
        FirebaseApp.initializeApp(context)
        database = Firebase.database.reference
    }


    override suspend fun fetchMedias(): List<MediaModel>? {
        database.child(MEDIA_TABLE).get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value.toString()}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        return null
    }


}