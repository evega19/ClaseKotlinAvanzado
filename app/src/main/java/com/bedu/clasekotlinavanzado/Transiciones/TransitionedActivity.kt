package com.bedu.clasekotlinavanzado.Transiciones

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bedu.clasekotlinavanzado.databinding.ActivityTransitionedBinding


class TransitionedActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTransitionedBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}