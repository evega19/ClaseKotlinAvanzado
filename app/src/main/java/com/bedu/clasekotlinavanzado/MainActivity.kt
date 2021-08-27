package com.bedu.clasekotlinavanzado

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bedu.clasekotlinavanzado.databinding.ActivityMainBinding
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import kotlin.random.Random


//TODO: https://github.com/beduExpert/Kotlin-Avanzado-Santander-2021/blob/main/Sesion-02/Readme.md

class MainActivity : AppCompatActivity() {

    private val baseUrl = "https://swapi.dev/api/planets/"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Llamada asincrona
        binding.btnRequest.setOnClickListener {
            llamadaAsincrona()
        }

        binding.btnSincrono.setOnClickListener {
            /*
            * Vamos a crear un hilo nuevo para evitar que la aplicación no responda ARM
            *
            * No es usual trabajarlas de manera asincronas,ya que Gestionamos de manera manual nuestra respuesta,
            * no estamos en el try tomando por hecho que el servidor responde.
            *
            * */
            Thread{
                llamadaSincrona()
            }.start()
        }


    }

    private fun llamadaAsincrona(){
        val okHttpClient = OkHttpClient()
        //obteniendo la url completa
        val planetNumber = Random.nextInt(1,61)//son 61 planetas
        Log.e("Planeta Asincrona", planetNumber.toString())
        val url = "$baseUrl$planetNumber/"
        val request = Request.Builder().url(url).build()
        okHttpClient.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                try {
                    val json = JSONObject(body)
                    val phrase = "El planeta elegido se llama"
                    val planet = json.getString("name")

                    // vamos a llamar cosas desde el UI con lo siguiente ya que esto se ejecuta en otro hilo
                    runOnUiThread { binding.textView.text ="$phrase $planet" }
                }catch (e: JSONException){ }
            }

        })
    }

    private fun llamadaSincrona(){
        val okHttpClient = OkHttpClient()
        //obteniendo la url completa
        val planetNumber = Random.nextInt(1,61)//son 61 planetas
        Log.e("Planeta Sincrono", planetNumber.toString())
        val url = "$baseUrl$planetNumber/"
        val request = Request.Builder().url(url).build()

        try{
            val response = okHttpClient.newCall(request).execute()
            val body = response.body?.string()

            val json = JSONObject(body)
            val phrase = getString(R.string.choosen_planet)
            val planet = json.getString("name")

            // vamos a llamar cosas desde el UI con lo siguiente ya que esto se ejecuta en otro hilo
            runOnUiThread { binding.textView.text ="$phrase $planet" }

        }catch (e: JSONException){ }

    }
}