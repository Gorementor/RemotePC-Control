package com.example.viewchanger.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.viewchanger.settings.ISettings
import com.example.viewchanger.settings.SettingsManager

class SettingsVM(application: Application) : AndroidViewModel(application) {
    val settings: ISettings = SettingsManager(application)
}