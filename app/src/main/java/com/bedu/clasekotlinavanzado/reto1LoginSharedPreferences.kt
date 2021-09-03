package com.bedu.clasekotlinavanzado

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bedu.clasekotlinavanzado.databinding.ActivityReto1LoginSharedPreferencesBinding

class reto1LoginSharedPreferences : AppCompatActivity() {

    companion object{
        val PREFS_NAME = "org.bedu.login"
        val EMAIL = "email"
        val IS_LOGGED = "is_logged"
    }

    private val binding by lazy{ ActivityReto1LoginSharedPreferencesBinding.inflate(layoutInflater)}
    private val sharedPreferences by lazy{ getSharedPreferences(reto1LoginSharedPreferences.PREFS_NAME, Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            Login()
        }

    }

    private fun Login() {
        val email = binding.etMail.text.toString()
        val pass = binding.etMail.text.toString()
        if(email.isNotEmpty() && pass.isNotEmpty()){
            sharedPreferences.edit().putString(EMAIL, email).putBoolean(IS_LOGGED,true).apply()
            goToLogged()
        }else{
            Toast.makeText(this, "Ingrese ambos",Toast.LENGTH_LONG).show()
        }
    }

    private fun goToLogged(){
        val i = Intent(this, reto1HomeSharedPreferences::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
    }

    private fun isLogged():Boolean{
        return sharedPreferences.getBoolean(IS_LOGGED,false)
    }

    override fun onStart() {
        super.onStart()
        if(isLogged()){
            goToLogged()
        }
    }
}