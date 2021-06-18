package com.alesno.bubblebuttonreserch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alesno.bubblebuttonreserch.R
import com.alesno.bubblebuttonreserch.databinding.ActivityBubblesBinding
import com.alesno.bubblebuttonreserch.viewBindings

class BubbleActivity : AppCompatActivity(R.layout.activity_bubbles) {

    private val mBinding by viewBindings(ActivityBubblesBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}