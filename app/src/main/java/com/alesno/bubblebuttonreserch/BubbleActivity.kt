package com.alesno.bubblebuttonreserch

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alesno.bubblebuttonreserch.databinding.ActivityBubblesBinding

class BubbleActivity : AppCompatActivity(R.layout.activity_bubbles) {

    private val mBinding by viewBindings(ActivityBubblesBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
           // enterPictureInPictureMode()
        }
    }

}