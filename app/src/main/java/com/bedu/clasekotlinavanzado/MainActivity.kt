package com.bedu.clasekotlinavanzado

import android.animation.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import com.bedu.clasekotlinavanzado.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // A través de binding ya no tenemos tenemos que declarar las variables
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root //Con esto hacemos referencia al constraint layout
        setContentView(view)

        binding.btnBarrel.setOnClickListener { barrelRol() }
        binding.btnSin.setOnClickListener { esquivar() }
        binding.btnAlpha.setOnClickListener { blink() }
        binding.btnTiny.setOnClickListener { shrink() }
        binding.btnStart.setOnClickListener { start() }
        binding.btnPivot.setOnClickListener { pivot() }

    }

    private fun barrelRol(){
        //Nuestra animación será el doble giro, por eso es 720, en lugar de 360
        val valueAnimator = ValueAnimator.ofFloat(0f, 720f)

        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float //obteniendo el valor actual
            //Con esto declaramos la rotación a nuestra nave
            binding.arwing.rotationY = value //asignando la posición de rotación
        }

        valueAnimator.duration = 1000 // milisegundos
        valueAnimator.interpolator = AccelerateDecelerateInterpolator()
        valueAnimator.start() //Iniciamos nuestra animación

    }

    private fun esquivar(){
        AnimatorInflater.loadAnimator(this, R.animator.esquivar).apply {
            setTarget(binding.arwing)
            start()
        }
    }

    private fun blink(){
        AnimatorInflater.loadAnimator(this, R.animator.blinking).apply {
            setTarget(binding.arwing)
            // Se puede agregar un listener para cada ejemplo usado
            addListener(object: Animator.AnimatorListener{
                override fun onAnimationStart(animation: Animator?) {
                    Toast.makeText(applicationContext, "iniciando blinking", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAnimationEnd(animation: Animator?) {
                    Toast.makeText(applicationContext, "terminando blinking", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAnimationCancel(animation: Animator?) {
                    Toast.makeText(applicationContext, "blinking cancelado", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAnimationRepeat(animation: Animator?) {
                    Toast.makeText(applicationContext, "repitiendo parpadeo", Toast.LENGTH_SHORT)
                        .show()
                }

            })
            start()
        }
    }

    private fun shrink(){
        AnimatorInflater.loadAnimator(this, R.animator.shrink).apply {
            setTarget(binding.arwing)
            start()
        }
    }

    private fun start(){

        binding.arwing.animate().apply {
            translationX(0F)
            translationY(0F)
            duration = 1000
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }

    }

    private fun pivot(){
        val initPivotX = PropertyValuesHolder.ofFloat("pivotX",0F)
        val initPivotY = PropertyValuesHolder.ofFloat("pivotY",0F)
        //El pivote no se ve por lo que colocaremos transparencia
        val transparent = PropertyValuesHolder.ofFloat("alpha",0.6F)
        val animationPivotInit = ObjectAnimator.ofPropertyValuesHolder(binding.arwing, initPivotX, initPivotY, transparent)
        animationPivotInit.duration = 500

        val pivotCenterX = binding.arwing.width.toFloat() / 2
        val pivotCenterY = binding.arwing.height.toFloat() / 2

        val centerPivotX = PropertyValuesHolder.ofFloat("pivotX",pivotCenterX)
        val centerPivotY = PropertyValuesHolder.ofFloat("pivotY",pivotCenterY)
        //El pivote no se ve por lo que colocaremos transparencia
        val opacy = PropertyValuesHolder.ofFloat("alpha",1F)
        val animationPivotCenter = ObjectAnimator.ofPropertyValuesHolder(binding.arwing, centerPivotX, centerPivotY, opacy)
        animationPivotCenter.duration = 500
        animationPivotCenter.startDelay = 4000

        AnimatorSet().apply {
            playSequentially(animationPivotInit, animationPivotCenter)
            start()
        }

    }
}