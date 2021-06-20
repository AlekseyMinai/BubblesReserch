package com.alesno.bubblebuttonreserch.services

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import com.alesno.bubblebuttonreserch.BubbleNotificationManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BubbleButtonService : Service() {

    override fun onCreate() {
        super.onCreate()
        BubbleNotificationManager.createNotificationChannel(this)
        GlobalScope.launch {
            delay(3000)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                startForeground(
//                    1,
//                    BubbleNotificationManager.createNotification(this@BubbleButtonService)
//                )
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}