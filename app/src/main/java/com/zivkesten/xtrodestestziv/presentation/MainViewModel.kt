package com.zivkesten.xtrodestestziv.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    var job: Job? = null

    var theFirstByte: Int = -1
    var sequenceNumber: Int = -1
    private var messageTypeReport: Int = -1
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
        Log.d("Zivi", "readFromRawByteByByte")
        val resourceId = resourceProvider.getResourceId(resourceName)
        val inputStream = resourceProvider.getResourceInputStream(resourceId)
        Log.d("Zivi", "resourceId $resourceId")

        Log.d("Zivi", "inputStream ${inputStream.read()}")
        try {


            inputStream.use { stream ->
                val firstByte = stream.read()
                if (firstByte != -1) {
                    theFirstByte = firstByte
                }
                val secondByte = stream.read()
                if (secondByte != -1) {
                    sequenceNumber = secondByte
                }

                val third = stream.read()
                if (third != -1) {
                    messageTypeReport = third
                }
            }
            Log.d("Zivi", "First byte: $theFirstByte")
            Log.d("Zivi", "sequenceNumber: $sequenceNumber")
            Log.d("Zivi", "messageTypeReport: $messageTypeReport")

        } catch (Ex: IOException) {
            Log.e("Zivi", "error ${Ex.message}")
        }
    }
}