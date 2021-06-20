package com.alesno.bubblebuttonreserch.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsStorage {

    val settings: StateFlow<Settings>
        get() = mSettings.asStateFlow()

    private val mSettings = MutableStateFlow(Settings.createDefault())

    fun changeFastChatSetting() {
        val newValue = !mSettings.value.isFastChatEnable
        mSettings.value = mSettings.value.copy(isFastChatEnable = newValue)
    }

}

data class Settings(val isFastChatEnable: Boolean) {

    companion object {
        fun createDefault() = Settings(
            isFastChatEnable = false
        )
    }

}