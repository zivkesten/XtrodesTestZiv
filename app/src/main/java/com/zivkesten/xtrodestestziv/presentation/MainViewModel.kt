package com.zivkesten.xtrodestestziv.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zivkesten.xtrodestestziv.di.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider

) : ViewModel() {

    fun readFile(resourceName: String) {
        viewModelScope.launch {
            val fileContents = withContext(Dispatchers.IO) {
                readFromRaw(resourceName)
            }
            Log.d("Zivi", "fileContents $fileContents")
            // Update LiveData or handle file contents as needed
        }
    }

    private fun readFromRaw(resourceName: String): String {
        val resourceId = resourceProvider.getContext().resources.getIdentifier(resourceName, "raw", resourceProvider.getContext().packageName)
        return resourceProvider.getContext().resources.openRawResource(resourceId).bufferedReader().use { it.readText() }
    }
}