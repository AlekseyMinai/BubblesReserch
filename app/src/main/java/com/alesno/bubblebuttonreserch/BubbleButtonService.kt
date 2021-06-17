package com.alesno.bubblebuttonreserch

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder

class BubbleButtonService: Service() {

    override fun onCreate() {
        super.onCreate()
        BubbleNotificationManager.createNotificationChannel(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(1, BubbleNotificationManager.createNotification(this))
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}