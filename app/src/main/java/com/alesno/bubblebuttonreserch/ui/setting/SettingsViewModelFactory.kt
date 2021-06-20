package com.alesno.bubblebuttonreserch.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alesno.bubblebuttonreserch.di.ApplicationServiceLocator
import com.alesno.bubblebuttonreserch.domain.SettingsStorage

class SettingsViewModelFactory(
    private val settingsStorage: SettingsStorage
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SettingsViewModel(settingsStorage) as T
    }

    companion object {

        fun create() = SettingsViewModelFactory(ApplicationServiceLocator.settingsStorage)

    }

}