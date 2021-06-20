package com.alesno.bubblebuttonreserch.app

import android.app.Application
import com.alesno.bubblebuttonreserch.di.ApplicationServiceLocator

class BubbleApp: Application() {

    override fun onCreate() {
        super.onCreate()
        ApplicationServiceLocator.init()
    }

}