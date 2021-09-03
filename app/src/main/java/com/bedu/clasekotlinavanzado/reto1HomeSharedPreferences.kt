package com.bedu.clasekotlinavanzado

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bedu.clasekotlinavanzado.databinding.ActivityReto1HomeSharedPreferencesBinding

class reto1HomeSharedPreferences : AppCompatActivity() {

    private val binding by lazy{ ActivityReto1HomeSharedPreferencesBinding.inflate(layoutInflater) }

    private val sharedPreferences by lazy{ getSharedPreferences(reto1LoginSharedPreferences.PREFS_NAME, Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvEmail.text = sharedPreferences.getString(reto1LoginSharedPreferences.EMAIL, "")
        binding.tvName.text = "Bienvenido"

        binding.btnClose.setOnClickListener {
            sharedPreferences.edit().clear().apply()
            goToLogin()
        }

    }

    private fun goToLogin(){
        val i = Intent(this, reto1LoginSharedPreferences::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
    }
}