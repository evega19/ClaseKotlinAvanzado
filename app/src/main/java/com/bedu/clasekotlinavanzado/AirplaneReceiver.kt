package com.bedu.clasekotlinavanzado

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast


//Recibiendo señales del sistema
class AirplaneReceiver: BroadcastReceiver()  {
    override fun onReceive(context: Context?, intent: Intent?) {
        val airplaneState = intent?.let {
            if(it.getBooleanExtra("state", false)) "Prendido" else "Apagado"
        }

        Toast.makeText(context,airplaneState,Toast.LENGTH_SHORT).show()
    }
}