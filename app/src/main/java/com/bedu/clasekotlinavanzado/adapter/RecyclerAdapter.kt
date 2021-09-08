package com.bedu.clasekotlinavanzado.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bedu.clasekotlinavanzado.R
import com.bedu.clasekotlinavanzado.data.Contact
import com.bedu.clasekotlinavanzado.databinding.ItemContactBinding

class RecyclerAdapter(val contacts: List<Contact>):
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    open class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val binding = ItemContactBinding.bind(view)

        fun bind(contact: Contact){
            binding.tvNombre.text = contact.name
            binding.tvPhone.text = contact.phone
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contacts[position]
        holder.bind(contact)
    }

    override fun getItemCount(): Int { return contacts.size }

}