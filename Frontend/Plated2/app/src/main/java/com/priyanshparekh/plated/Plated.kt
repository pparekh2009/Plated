package com.priyanshparekh.plated

import android.app.Application
import com.priyanshparekh.core.utils.SharedPrefManager

class Plated: Application() {

    override fun onCreate() {
        super.onCreate()

        SharedPrefManager.init(this)
    }

}