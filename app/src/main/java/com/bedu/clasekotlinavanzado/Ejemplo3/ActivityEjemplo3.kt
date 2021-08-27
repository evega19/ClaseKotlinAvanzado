package com.bedu.clasekotlinavanzado.Ejemplo3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bedu.clasekotlinavanzado.databinding.ActivityEjemplo3Binding
import com.bedu.clasekotlinavanzado.Ejemplo3.models.Pokemon
import com.bedu.clasekotlinavanzado.Ejemplo3.retrofit.WebServices
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class activityEjemplo3 : AppCompatActivity() {

    private lateinit var binding: ActivityEjemplo3Binding

    private var baseUrl = "https://pokeapi.co/api/v2/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEjemplo3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener {
            searchPokemon()
        }
    }

    private fun searchPokemon() {
        val pokeName = binding.etPokemon.text.toString()

        val retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()

        //pasamos los nomrbe de los web services, son todos los servicios por ejemplo en Amazon, metodo de pago,
        // productos, por eso se hacen varias interfaces dependiendo de los servicios usados
        val pokemonServices = retrofit.create(WebServices::class.java)

        //Llamos a la petición getPokemon para obtener el pokemon
        val call = pokemonServices.getPokemon(pokeName)
        //El objeto Pokemon es lo que nos regresará el servidor
        call.enqueue(object: Callback<Pokemon>{
            // se parece mucho al OKHTTP porque Retrofit usa OKHTTP
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                if(response.code() == 200){
                    //Con esto sabemos que es una respuesta 200, es decir un ok
                    val body = response.body()
                    Log.e("Retrofit response", body.toString())
                    //Cuando responde si se ejecuta en el hilo principal por lo que no necesitamos runOnUiThread
                    //El body ya es nuesta variable parseada por lo que podríamos llamarlo como pokemon
                    val pokemon = body

                    binding.tvPokemon.text = pokemon?.name
                    binding.tvWeight.text = "Peso: ${pokemon?.weight}"
                    Picasso.get().load(pokemon?.sprites?.photoUrl).into(binding.pokemon)
                }else{
                    Toast.makeText(binding.root.context, "Este pokemon no existe", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                //Que el servidor esté caído o que no tengas internet
                Log.e("error retrofit", "Error: $t")
            }

        })
    }
}