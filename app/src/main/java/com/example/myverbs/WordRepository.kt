package com.example.myverbs

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WordRepository(application: Application)  {

    private val wordDao: WordDao? = MyVerbsAppDataBase.getInstance(application)?.wordDao()

    suspend fun insertarWord(word: Word) {
        procesarInsertWord(word)
    }

    private suspend fun procesarInsertWord(word: Word) {
        withContext(Dispatchers.Default) {
            wordDao?.insert(word)
        }
    }

    fun getWords(): LiveData<List<Word>>? {
        return wordDao?.getWord()
    }

    suspend fun actualizarStatusPalabra(wordId: Int, nuevoStatus: String) {
        withContext(Dispatchers.Default) {
            wordDao?.updateStatus(wordId, nuevoStatus)
        }
    }

}