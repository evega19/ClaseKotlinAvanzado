package com.bedu.clasekotlinavanzado.Transiciones

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Scene
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.animation.AccelerateInterpolator
import com.bedu.clasekotlinavanzado.R
import com.bedu.clasekotlinavanzado.databinding.ActivityTransitionBinding

class TransitionActivity : AppCompatActivity() {

    private lateinit var sceneOne: Scene
    private lateinit var sceneTwo: Scene
    private lateinit var currentScene: Scene

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityTransitionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        sceneOne = Scene.getSceneForLayout(binding.container, R.layout.scene_one, this)
        sceneTwo = Scene.getSceneForLayout(binding.container,R.layout.scene_two, this)
        currentScene = sceneOne

//        val transition = TransitionInflater.from(this).inflateTransition(R.transition.bounds)

        val transition = ChangeBounds().apply {
            interpolator = AccelerateInterpolator()
            duration = 500
        }

        binding.btnTransition.setOnClickListener {
            currentScene = if(currentScene == sceneOne){
                TransitionManager.go(sceneTwo, transition)
                sceneTwo
            } else{
                TransitionManager.go(sceneOne,transition)
                sceneOne
            }
        }

    }
}