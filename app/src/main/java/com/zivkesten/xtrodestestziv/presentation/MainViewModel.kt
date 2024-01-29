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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider

) : ViewModel() {

    var contents = mutableStateOf("WELCOM")

    val fileParser = FileParser()

    var job: Job? = null

    var theFirstByte: Int = -1
    var sequenceNumber: Int = -1
    var messageTypeReport: Int = -1
    var flagsMultiPacket: Int = -1
    var lengthOfPacket: Int = -1
    var commandStream: Int = -1
    var lengthOfPayload: Int = -1
    var numberOfRecordes: Int = -1
    var recoredType: Int = -1

    init {
        Log.d("Zivi", "init")

    }

    fun click() {
        Log.d("Zivi", "click")
        readFile("xtr2_20")
    }

    fun readFile(resourceName: String) {
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

    private suspend fun readFromRawByteByByte(resourceName: String) {
        try {
            val resourceId = resourceProvider.getResourceId(resourceName)
            val inputStream = resourceProvider.getResourceInputStream(resourceId)
            fileParser.findAndParsePackets(inputStream) // return the contents for UI processing

//            Log.d("Zivi", "First byte: ${fileParser.theFirstByte}")
//            Log.d("Zivi", "Sequence number: ${fileParser.sequenceNumber}")
//            Log.d("Zivi", "Message type report: ${fileParser.messageTypeReport}")

        } catch (ex: IOException) {
            Log.e("Zivi", "Error: ${ex.message}")
        }
    }
}
