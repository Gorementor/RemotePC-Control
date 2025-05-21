package com.example.viewchanger.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.viewchanger.R
import com.example.viewchanger.viewmodels.HomeVM


class HomeFragment : Fragment(R.layout.fragment_home)  {
    private lateinit var homeVM: HomeVM
    private lateinit var btnShutdown: Button
    private lateinit var tvResult : TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeVM = ViewModelProvider(this)[HomeVM::class.java]

        // Initialize views
        btnShutdown = view.findViewById(R.id.btn_Shutdown)
        tvResult = view.findViewById(R.id.tv_Result)

        // Observe SSH command output and update UI
        homeVM.output.observe(viewLifecycleOwner) { output ->
            tvResult.text = output
        }

        // Observe isValid and enable/disable button
        homeVM.isValid.observe(viewLifecycleOwner) { isValid ->
            btnShutdown.isEnabled = isValid
        }

        // Call checkConnection to check if the host is online when the fragment is created
        homeVM.checkConnection()

        // Observe isHostOnline and enable/disable button
        homeVM.isHostOnline.observe(viewLifecycleOwner) { isOnline ->
            if (isOnline) {
                tvResult.text = "Host is online"
            }
            else {
                tvResult.text = "Host is offline"
            }
        }

               // Handle save button click
        btnShutdown.setOnClickListener {
            sendShutdown()
            //homeVM.executeCommand("cd")
            homeVM.shutdownCommand()
        }

    }

    private fun sendShutdown() {

        Toast.makeText(requireContext(), "Button pushed!", Toast.LENGTH_SHORT).show()
    }
}

