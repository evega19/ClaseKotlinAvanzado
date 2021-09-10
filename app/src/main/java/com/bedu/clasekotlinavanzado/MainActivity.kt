package com.bedu.clasekotlinavanzado

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bedu.clasekotlinavanzado.databinding.ActivityMainBinding

//TODO: https://github.com/beduExpert/Kotlin-Avanzado-Santander-2021/tree/main/Sesion-05

class MainActivity : AppCompatActivity() {

    private val binding by lazy{ ActivityMainBinding.inflate(layoutInflater) }

    //Un service no quiere decir que sea asyncrono\

    //Broadcast Receivers es como si sintonizara una radio y cuando este lo recibe lo escucha

    private val recieverTwo = ReceiverTwo()

    companion object{
        const val KEYNAME ="NAME"
        const val KEYEMAIL = "EMAIL"

        //Hay muchas apps enviando señales
        const val ACTION_NAME = "org.debu.actions.SALUDO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            emit2()
        }

    }

    private fun emit(){
        // Esta es nuestra información extra a enviar
        val bundle = Bundle().apply {
            putString(KEYNAME,"Pedro")
            putString(KEYEMAIL,"pedro@bedu.org")
        }


        // Creamos un Intent con nuestro bundle como extra y detonamos el evento con sendBroadcast()
        Intent(this,ReceiverOne::class.java).apply {
            putExtras(bundle)
        }.let(::sendBroadcast)

        /*
            val intent = Intent(this,ReceiverOne::class.java)
            intent.putExtras(bundle)
            sendBroadcast(intent)
         */

        /*
        //Con esta forma lo estamos diciendo de manera implícita con un cierto nombre, con cierta llame
        //Hay varias llaves que usa android para dar funciones a todos
        Intent("org.debu.actions.SALUDO").apply { putExtras(bundle)}.let(::sendBroadcast)
        */

    }

    override fun onStart() {
        super.onStart()
        IntentFilter().apply {
            addAction(ACTION_NAME)
        }.also { filter -> registerReceiver(recieverTwo,filter) }
    }

    override fun onStop() {
        super.onStop()
        //Vamos a quitar el registro
        unregisterReceiver(recieverTwo)
    }

    private fun emit2(){

        val bundle = Bundle().apply {
            putString(KEYNAME,"Pedro")
            putString(KEYEMAIL,"pedro@bedu.org")
        }

        Intent(ACTION_NAME).apply {
            putExtras(bundle)
        }.let(::sendBroadcast)


    }
}