package com.example.myverbs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VerboAdapter(private val verbos: List<Word>) : RecyclerView.Adapter<VerboAdapter.ViewHolder>() {



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewInfinitivo: TextView = itemView.findViewById(R.id.textViewInfinitivo)
        val textViewSignificado: TextView = itemView.findViewById(R.id.textViewSignificado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_verbo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val verboPair = verbos[position]

        // Utiliza los elementos del Pair directamente
        holder.textViewInfinitivo.text = verboPair.text
        holder.textViewSignificado.text = verboPair.meanig
    }

    override fun getItemCount(): Int {
        return verbos.size
    }
}
