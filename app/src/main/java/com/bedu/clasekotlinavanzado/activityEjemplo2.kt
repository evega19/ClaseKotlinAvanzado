package com.bedu.clasekotlinavanzado

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bedu.clasekotlinavanzado.databinding.ActivityEjemplo2Binding
import com.google.gson.Gson
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class activityEjemplo2 : AppCompatActivity() {

    private val url = "https://swapi.dev/api/people/1/"

    private lateinit var binding: ActivityEjemplo2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEjemplo2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnJedi.setOnClickListener {
            llamarAlaFuerza()
        }

        binding.btnSith.setOnClickListener {
            llamarAlaFuerza(true)
        }
    }

    private fun llamarAlaFuerza(isSith: Boolean = false) {
        val client = OkHttpClient()

        val request = Request.Builder().url(url).build()

        val clientBuilder = client.newBuilder()

        clientBuilder.build().newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                try {

                    val jedi = Gson().fromJson(body, Jedi::class.java)

                    /*val json = JSONObject(body)
                    val name = json.getString("name")
                    val height = json.getString("height")
                    val mass = json.getString("mass")*/
                    runOnUiThread{
                        binding.tvName.text = jedi.name
                        binding.tvHeight.text = jedi.height.toString()
                        binding.tvWeight.text = jedi.mass.toString()
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }

        })

    }
}

data class Jedi(
    val name: String? = "",
    val height: Int? = 0,
    val mass: Int? = 0
)