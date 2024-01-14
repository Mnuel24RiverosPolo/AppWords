package com.example.myverbs

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WordViewHolder(inflater: LayoutInflater, parent: ViewGroup):
    RecyclerView .ViewHolder(inflater.inflate(R.layout.item_verbo, parent, false)) {


        private var textViewInfinitivo: TextView? = null
        private var textViewSignificado: TextView? = null
        private var textViewStatus: TextView? = null


        init {
            textViewInfinitivo = itemView.findViewById(R.id.textViewInfinitivo)
            textViewSignificado = itemView.findViewById(R.id.textViewSignificado)
            textViewStatus = itemView.findViewById(R.id.textViewStatus)


        }

        fun bind(word: Word) {

            textViewInfinitivo?.text = word.text
            textViewSignificado?.text =  word.meanig
            textViewStatus?.text = word.status





        }
}