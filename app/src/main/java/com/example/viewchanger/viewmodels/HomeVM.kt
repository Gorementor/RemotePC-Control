package com.example.viewchanger.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.viewchanger.sshclient.SSHClient
import kotlinx.coroutines.launch

class HomeVM(application: Application) : AndroidViewModel(application) {

    private val settingsViewModel = SettingsVM(application)

    private val _isValid = MutableLiveData<Boolean>()
    val isValid: LiveData<Boolean> = _isValid

    private val _output = MutableLiveData<String>()
    val output: LiveData<String> = _output

    private val _isHostOnline = MutableLiveData<Boolean>()
    val isHostOnline: LiveData<Boolean> = _isHostOnline

    private val sshClient: SSHClient by lazy {
        SSHClient(
            settings = settingsViewModel.settings
        )
    }

    init {
        validateSettings() // Check settings when ViewModel is created
    }

    fun checkConnection() {
        viewModelScope.launch {
            _isHostOnline.value = sshClient.isHostOnline()
        }
    }

    fun executeCommand(command: String) {
        viewModelScope.launch {
            _output.value = "Executing..."
            val result = sshClient.executeCommand(command)
            _output.value = result
        }
    }

    fun shutdownCommand() {
        viewModelScope.launch {
            _output.value = "Executing..."
            val result = sshClient.shutdownCommand()
            _output.value = result
        }
    }

    private fun validateSettings() {
        _isValid.value = settingsViewModel.settings.isValid()
    }
}