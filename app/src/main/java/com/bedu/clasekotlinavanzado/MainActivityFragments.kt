package com.bedu.clasekotlinavanzado

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bedu.clasekotlinavanzado.databinding.ActivityMainFragmentsBinding

class MainActivityFragments : AppCompatActivity() {

    private val binding by lazy{ActivityMainFragmentsBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val manager =  supportFragmentManager

        binding.addButton.setOnClickListener {
            val fragment = Fragment1()
            manager.beginTransaction().apply {
                add(R.id.container, fragment, "FragBedu")
                commit()
            }
        }

        binding.removeButton.setOnClickListener {
            val fragment = Fragment2()
            var fragmentTag = manager.findFragmentByTag("fragBedu")

        }


    }
}