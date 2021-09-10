package com.bedu.clasekotlinavanzado

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class CallRecordReciever:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context,"Ya acabé", Toast.LENGTH_LONG).show()
    }
}