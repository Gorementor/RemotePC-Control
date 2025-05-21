package com.example.viewchanger.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.viewchanger.R
import com.example.viewchanger.viewmodels.SettingsVM
import java.net.InetAddress

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var settingsVM: SettingsVM

    private val settingsName : String = "UserPrefs"
    private val userNameKey : String = "username"
    private val passwordKey : String = "password"
    private val hostIPKey : String = "hostip"

    private lateinit var btnSave : Button
    private lateinit var etUserName : EditText
    private lateinit var etPassword : EditText
    private lateinit var etHostIP : EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsVM = ViewModelProvider(this)[SettingsVM::class.java]

        // Initialize views
        btnSave = view.findViewById(R.id.btn_save)
        etUserName = view.findViewById(R.id.et_Username)
        etPassword = view.findViewById(R.id.et_Password)
        etHostIP = view.findViewById(R.id.et_HostIP)

        etUserName.setText(settingsVM.settings.username)
        etPassword.setText(settingsVM.settings.password)
        etHostIP.setText(settingsVM.settings.hostIP)

        // Handle save button click
        btnSave.setOnClickListener {
            saveSettings()
        }
    }

    private fun saveSettings() {
        val username = etUserName.text.toString()
        val password = etPassword.text.toString()
        val hostip = etHostIP.text.toString()

        if(!isValid(username, password, hostip))
        {
            return
        }

        settingsVM.settings.username = username
        settingsVM.settings.password = password
        settingsVM.settings.hostIP = hostip

        // Show a confirmation message (optional)
        Toast.makeText(requireContext(), "Settings saved!", Toast.LENGTH_SHORT).show()
    }

    private fun isValid(username : String, password : String, hostip : String) : Boolean {
        if(username.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter a username", Toast.LENGTH_SHORT).show()
            return false
        }

        if(password.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter a password", Toast.LENGTH_SHORT).show()
            return false
        }

        if(hostip.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter a host IP", Toast.LENGTH_SHORT).show()
            return false
        }

        if(!isValidIPv4(hostip)) {
            Toast.makeText(requireContext(), "Please enter a valid IPv4 format for host IP", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun isValidIPv4(ip: String): Boolean {
        return try {
            val inet = InetAddress.getByName(ip)
            inet.hostAddress == ip && ip.count { it == '.' } == 3
        } catch (e: Exception) {
            false
        }
    }
}