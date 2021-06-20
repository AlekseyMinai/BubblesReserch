package com.alesno.bubblebuttonreserch.di

import com.alesno.bubblebuttonreserch.domain.SettingsStorage

// TODO: 6/20/2021 Переписать лучше на koin
object ApplicationServiceLocator {

    lateinit var settingsStorage: SettingsStorage

    fun init() {
        settingsStorage = SettingsStorage()
    }

}