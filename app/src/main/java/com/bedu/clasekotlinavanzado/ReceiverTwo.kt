package com.bedu.clasekotlinavanzado

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ReceiverTwo : BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {
        Toast.makeText(context, "Evento 2 recibido", Toast.LENGTH_SHORT).show()
    }
}