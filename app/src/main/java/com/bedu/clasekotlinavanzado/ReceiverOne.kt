package com.bedu.clasekotlinavanzado

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ReceiverOne:BroadcastReceiver() {



    //Cuando yo reciba una senial
    override fun onReceive(context: Context?, intent: Intent?) {
        //El intent es el que porta la informaci[on
        val bundle = intent?.extras
        val name = bundle?.getString(MainActivity.KEYNAME)
        val email = bundle?.getString(MainActivity.KEYEMAIL)

        //Aqu[i estamos en el hilo principal por lo que puedo ejecutar cualquier cosa como si fuera el activity
        Toast.makeText(context,"$name $email", Toast.LENGTH_SHORT).show()
    }
}