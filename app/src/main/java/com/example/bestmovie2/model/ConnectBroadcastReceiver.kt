package com.example.bestmovie2.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ConnectBroadcastReceiver  : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        StringBuilder().apply {
            append("СООБЩЕНИЕ ОТ СИСТЕМЫ\n")
            append("Action: ${intent.action}")
            toString().also {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }
}
