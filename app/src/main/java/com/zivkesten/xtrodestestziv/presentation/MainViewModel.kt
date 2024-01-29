package com.zivkesten.xtrodestest.presentation

import androidx.lifecycle.ViewModel
import com.zivkesten.xtrodestest.data.repository.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: RemoteRepository
) : ViewModel() {

    var c = ""

    fun readFile(filePath: String) {
        val file: File = File(filePath) // filePath is the path to your file

        val text = StringBuilder()

        try {
            val br = BufferedReader(FileReader(file))
            var line: String?
            while (br.readLine().also { line = it } != null) {
                text.append(line)
                text.append('\n')
            }
            br.close()
        } catch (e: IOException) {
            // Handle exceptions
        }

    }
}