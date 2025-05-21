package com.example.viewchanger.sshclient

interface ISSHClient {
    suspend fun isHostOnline(): Boolean
    suspend fun executeCommand(command: String): String
    suspend fun shutdownCommand() : String
}