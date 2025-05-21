package com.example.viewchanger.settings

interface ISettings {
    var username: String
    var password: String
    var hostIP: String

    fun isValid() : Boolean
    fun clearSettings()
}