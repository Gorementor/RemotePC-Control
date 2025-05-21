package com.example.viewchanger.settings

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import java.net.InetAddress

class SettingsManager(context: Context) : ISettings {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)

    override var username: String
        get() = prefs.getString("username", "").toString()
        set(value) = prefs.edit().putString("username", value).apply()

    override var password: String
        get() = prefs.getString("password", "").toString()
        set(value) = prefs.edit().putString("password", value).apply()

    override var hostIP: String
        get() = prefs.getString("hostIP", "").toString()
        set(value) = prefs.edit().putString("hostIP", value).apply()

    override fun isValid() : Boolean {
        if(username.isEmpty()) {
            return false
        }

        if(password.isEmpty()) {
            return false
        }

        if(hostIP.isEmpty()) {
            return false
        }

        if(!isValidIPv4(hostIP)) {
            return false
        }

        return true
    }

    override fun clearSettings() {
        prefs.edit().clear().apply()
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