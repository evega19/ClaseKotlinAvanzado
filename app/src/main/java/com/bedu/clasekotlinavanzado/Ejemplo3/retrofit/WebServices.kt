package com.bedu.clasekotlinavanzado.Ejemplo3.retrofit


import com.bedu.clasekotlinavanzado.Ejemplo3.models.Pokemon
import com.bedu.clasekotlinavanzado.Ejemplo3.models.Type
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WebServices {

    // Definimos el endpoint pokemont/{pokemon}, siendo este último una variable a ingresar por el usuario (en este caso, desde el edit text)

    //Colocamos entre corchetes nuestra variable
    @GET("pokemon/{pokemon}")
    fun getPokemon(@Path("pokemon")pokemon:String): Call<Pokemon>
    //la clase Pokemon dentro de Call indica que el json de respuesta va a   ser transformado es un objeto de esa clase.

    @GET("type/{id}")
    fun getType(@Path("id")id:Int):Call<List<Type>>




}

