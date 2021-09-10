package com.bedu.clasekotlinavanzado

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReceiverTwo : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        ToasCourrutine(intent?.extras,context!!).run {
            execute()
        }
    }

    private inner class ToasCourrutine(private val bundle: Bundle?, private val context: Context): ViewModel(){
        //Aprovecharemos su scoope para ejecutar courutinas

        fun execute() = viewModelScope.launch {
            doInBackGround()
            onPostExecute()
        }


        private suspend fun doInBackGround():String = withContext(Dispatchers.IO){
            val name = bundle?.getString("NAME")
            val email = bundle?.getString("EMAIL")


            Log.d("Broadcast", """NAME: $name EMAIL: $email Thread: ${Thread.currentThread()} """.trimMargin())
            delay(2000) // simulate async work
            return@withContext "Resultado"
        }

        private fun onPostExecute(){
            val name = bundle?.getString("NAME")
            val email = bundle?.getString("EMAIL")

            Toast.makeText(context,"$name $email", Toast.LENGTH_SHORT).show()
        }

    }


}