package com.bedu.clasekotlinavanzado.Transiciones

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Scene
import com.bedu.clasekotlinavanzado.R
import com.bedu.clasekotlinavanzado.databinding.ActivityTransitionSetBinding

class TransitionSetActivity : AppCompatActivity() {

    private lateinit var sceneOne: Scene
    private lateinit var sceneThree: Scene
    private lateinit var currentScene: Scene

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityTransitionSetBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sceneOne = Scene.getSceneForLayout(binding.container, R.layout.scene_one, this)
        sceneThree = Scene.getSceneForLayout(binding.container, R.layout.scene_three, this)
        currentScene = sceneOne
    }
}