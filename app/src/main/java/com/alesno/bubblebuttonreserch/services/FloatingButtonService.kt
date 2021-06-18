package com.alesno.bubblebuttonreserch.services

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import android.widget.ImageView
import com.alesno.bubblebuttonreserch.R


class FloatingButtonService : Service() {

    private var windowManager: WindowManager? = null
    private var chatHead: ImageView? = null
    private var params: WindowManager.LayoutParams? = null

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as? WindowManager

        chatHead = ImageView(this)
        chatHead?.setImageResource(R.drawable.ic_snowflake)

        val layoutFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            WindowManager.LayoutParams.TYPE_PHONE;
        }

        params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        params?.gravity = Gravity.TOP and Gravity.LEFT
        params?.x = 0
        params?.y = 100

        windowManager?.addView(chatHead, params)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}