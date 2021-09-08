package com.bedu.clasekotlinavanzado

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bedu.clasekotlinavanzado.adapter.RecyclerAdapter
import com.bedu.clasekotlinavanzado.adapter.RecyclerAdapter2
import com.bedu.clasekotlinavanzado.data.Contact
import com.bedu.clasekotlinavanzado.data.Game
import com.bedu.clasekotlinavanzado.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy{ ActivityMainBinding.inflate(layoutInflater) }

    private val recyclerView : RecyclerView by lazy { findViewById(R.id.RV) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /*recyclerView.adapter = RecyclerAdapter(listOf(
            Contact("Javier", "5511223344"),
            Contact("Juan", "5522334455"),
            Contact("Luis", "5533445566"),
            Contact("Fernanda", "5544556677"),
            Contact("Luisa", "5555667788"),
            Contact("Manuel", "5566778899"),
            Contact("Jose", "5577889900"),
            Contact("Maria", "5588990011"),
            Contact("Ana", "5599001122"),
        ))*/

        recyclerView.adapter = RecyclerAdapter2(
            listOf(
                Game("Devil may cry 5","Action game",4,"Ten","Action game",R.drawable.devil_may_cry_5)
            )
        )



    }
}