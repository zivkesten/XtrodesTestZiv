package com.zivkesten.xtrodestestziv.di

import android.content.Context
import javax.inject.Inject

class ResourceProvider @Inject constructor(private val context: Context) {

    fun getContext(): Context = context

}
