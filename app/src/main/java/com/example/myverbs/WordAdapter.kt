package com.example.myverbs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class WordAdapter(): RecyclerView.Adapter<WordViewHolder>() {
    private var wordList = emptyList<Word>()

    fun setWords(list: List<Word>) {
        this.wordList = list
        this.notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        return WordViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word: Word = wordList[position]
        holder.bind(word)
    }

    override fun getItemCount(): Int {
        return wordList.size
    }
}