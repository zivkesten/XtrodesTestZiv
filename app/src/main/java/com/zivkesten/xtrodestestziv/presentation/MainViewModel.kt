package com.zivkesten.xtrodestestziv.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zivkesten.xtrodestestziv.FileParser
import com.zivkesten.xtrodestestziv.di.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider

) : ViewModel() {

    var contents = mutableStateOf("WELCOME")

    private val fileParser = FileParser()

    private var job: Job? = null


    fun click() {
        Log.d("Zivi", "click")
        readFile("xtr2_20")
        readFile("xtr2_21")
    }

    private fun readFile(resourceName: String) {
        if (job != null) {
            job?.cancelChildren()
            job = null
        }
        job = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                readFromRawByteByByte(resourceName)
            }
        }
    }

    private fun readFromRawByteByByte(resourceName: String) {
        try {
            val resourceId = resourceProvider.getResourceId(resourceName)
            val inputStream = resourceProvider.getResourceInputStream(resourceId)
            val records = fileParser.findAndParsePackets(inputStream) // return the contents for UI processing
            Log.d("Zivi", "records: ${records.size}")

        } catch (ex: IOException) {
            Log.e("Zivi", "Error: ${ex.message}")
        }
    }
}
