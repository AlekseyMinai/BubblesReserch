package com.alesno.bubblebuttonreserch

import androidx.fragment.app.Fragment
import com.alesno.bubblebuttonreserch.databinding.ActivityMainBinding

class BindingFragment : Fragment(R.layout.activity_main) {

    private val mBinding by viewBindings { ActivityMainBinding.bind(it) }

    fun foo() = with(mBinding) {

    }

}