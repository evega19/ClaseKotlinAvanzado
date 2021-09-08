package com.bedu.clasekotlinavanzado.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bedu.clasekotlinavanzado.R
import com.bedu.clasekotlinavanzado.data.Contact
import com.bedu.clasekotlinavanzado.data.Game
import com.bedu.clasekotlinavanzado.databinding.ItemGameBinding

class RecyclerAdapter2(val games: List<Game>):
    RecyclerView.Adapter<RecyclerAdapter2.ViewHolder>() {

    open class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val binding = ItemGameBinding.bind(view)

        fun bind(game: Game){
            binding.tvTitulo.text = game.name
            binding.tvClasificacion.text = game.classification
            binding.tvCategoria.text = game.category
            binding.rbCalificacion.numStars = game.puntuation
            binding.imgPortada.setImageResource(game.photo)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = games.size

    override fun onBindViewHolder(holder: RecyclerAdapter2.ViewHolder, position: Int) {
        holder.bind(games[position])
    }
}