package com.bedu.clasekotlinavanzado.Transiciones

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bedu.clasekotlinavanzado.databinding.ActivitySharedTransitionBinding


class SharedTransitionActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySharedTransitionBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}
