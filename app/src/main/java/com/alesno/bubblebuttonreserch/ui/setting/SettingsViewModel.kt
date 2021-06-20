package com.alesno.bubblebuttonreserch.ui.setting

import androidx.lifecycle.ViewModel
import com.alesno.bubblebuttonreserch.domain.Settings
import com.alesno.bubblebuttonreserch.domain.SettingsStorage
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel(
    private val settingsStorage: SettingsStorage
) : ViewModel() {

    val settingsState: StateFlow<Settings>
        get() = settingsStorage.settings

    fun onFastChatClicked() {
        settingsStorage.changeFastChatSetting()
    }

}