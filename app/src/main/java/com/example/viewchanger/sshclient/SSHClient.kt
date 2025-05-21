package com.example.viewchanger.sshclient

import com.example.viewchanger.settings.ISettings
import com.jcraft.jsch.JSch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream

class SSHClient(
    private val port: Int = 22,
    private val settings : ISettings
) : ISSHClient
{
    override suspend fun isHostOnline(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val process = Runtime.getRuntime().exec("ping -c 1 ${settings.hostIP}") // Linux/Mac
                // Use "ping -n 1 $host" on Windows
                process.waitFor() == 0
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    override suspend fun executeCommand(command: String): String {
        return withContext(Dispatchers.IO) {  // Run on background thread
            val jsch = JSch()
            val session = jsch.getSession(settings.username, settings.hostIP, port)
            session.setPassword(settings.password)

            // Disable Strict Host Key Checking (for testing, not recommended for production)
            session.setConfig("StrictHostKeyChecking", "no")

            try {
                session.connect(5000) // Timeout: 5 seconds
                val channel = session.openChannel("exec") as com.jcraft.jsch.ChannelExec
                channel.setCommand(command)

                val inputStream: InputStream = channel.inputStream
                channel.connect()

                val output = inputStream.bufferedReader().use { it.readText() }
                channel.disconnect()
                session.disconnect()
                output.trim()
            } catch (e: Exception) {
                e.printStackTrace()
                "Error: ${e.message}"
            }
        }
    }

    override suspend fun shutdownCommand(): String {
        return executeCommand("shutdown /s /t 0")
    }
}
