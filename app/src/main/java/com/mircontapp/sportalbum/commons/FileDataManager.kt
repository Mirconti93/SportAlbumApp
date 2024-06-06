package com.mircontapp.sportalbum.commons

import android.content.Context
import com.mircontapp.sportalbum.domain.models.PlayerModel
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter


class FileDataManager() {
    companion object {
        fun writePlayers(context: Context, name: String?, players: List<PlayerModel?>) {
            val list: MutableList<String> = ArrayList()
            players.forEach {
                list.add("${it?.name}_${it?.role}_${it?.gender}_${it?.team}_${it?.country}_${it?.birthyear}_${it?.value}_${it?.valueleg}_${it?.teamLegend}_${it?.national}_${it?.nationalLegend}_${it?.roleLineUp}_${it?.style}")
            }
            writeFileOnInternalStorage(context, name, list)
        }

        /*** write a file on local storage  */
        private fun writeFileOnInternalStorage(
            mContext: Context,
            name: String?,
            list: List<String?>
        ): Boolean {
            val dir = File(mContext.getExternalFilesDir(null), "album_data")
            if (!dir.exists()) {
                dir.mkdir()
            }
            return try {
                val file = File(dir, name)
                if (!file.exists()) {
                    file.createNewFile()
                }
                val writer = FileWriter(file)
                val bw = BufferedWriter(writer)
                for (row in list) {
                    try {
                        bw.newLine()
                        bw.write(row)
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
                bw.flush()
                bw.close()
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}