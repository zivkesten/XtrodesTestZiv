package com.zivkesten.xtrodestestziv.di

import android.content.Context
import java.io.InputStream
import javax.inject.Inject

class ResourceProvider @Inject constructor(private val context: Context) {

    fun getContext(): Context = context


    fun getResourceId(resourceName: String): Int {
        return context.resources.getIdentifier(resourceName, "raw", context.packageName)
    }

    fun getResourceInputStream(resourceId: Int): InputStream {
        return context.resources.openRawResource(resourceId)
    }

}
