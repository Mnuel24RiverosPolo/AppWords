package com.example.myverbs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WordWiewModel(application: Application): AndroidViewModel(application)  {
    private  val  repository = WordRepository(application)
    //val allWords: LiveData<List<Word>>? = repository.getWords()
    private var currentIndex: Int = 0

    val words = repository.getWords();

    fun guardarWords(word: Word){
        viewModelScope.launch {
            repository.insertarWord(word)
        }
    }

    fun actualizarWord(word: Word) {
        viewModelScope.launch {
            // repository
        }
    }

    fun actualizarStatusPalabra(wordId: Int, nuevoStatus: String) {
        viewModelScope.launch {
            repository.actualizarStatusPalabra(wordId, nuevoStatus)
        }
    }

    fun getCurrentWord(): Word? {
        return if (words?.value != null && currentIndex >= 0 && currentIndex < words?.value!!.size) {
            words?.value!![currentIndex]
        } else {
            null
        }
    }





}