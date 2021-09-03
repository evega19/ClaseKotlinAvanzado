package com.bedu.clasekotlinavanzado

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bedu.clasekotlinavanzado.databinding.ActivityEjemplo1sharepreferencesBinding

class ejemplo1SharedPreferences : AppCompatActivity() {

    companion object{
        //Llaves para identificar los valores para nuestro xml
        val PREFS_NAME = "com.bedu.clasekotlinavanzado"
        val STRING = "STRING"
        val NUMBER = "NUMBER"
        val BOOLEAN = "BOOLEAN"
    }

    private val binding by lazy{ ActivityEjemplo1sharepreferencesBinding.inflate(layoutInflater)}

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //Tipo de archivo, tipo de seguridad (acceso de usuarios)
        preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        setValues()
        binding.button.setOnClickListener {
            saveValues()
        }
    }

    private fun saveValues(){
        val string = binding.etString.text.toString()
        val number = binding.etNumber.text.toString().toFloat()
        val checked = binding.switch1.isChecked

        preferences.edit()
            .putString(STRING,string)
            .putBoolean(BOOLEAN,checked)
            .putFloat(NUMBER,number)
            .apply()

        //.appy() es de forma syncrona, y con .commit() es de forma asyncrona
    }

    private fun setValues(){
        //Obtendremos los valores del archivo y podremos en nuestras vistas

        val string = preferences.getString(STRING,"")
        val boolean = preferences.getBoolean(BOOLEAN,false)
        val number = preferences.getFloat(NUMBER,0f)

        with(binding){
            //Scope Functions
            etString.setText(string)
            switch1.isChecked = boolean
            etNumber.setText(number.toString())
        }
    }
}