package com.example.myverbs

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordDao {
    @Insert
    fun insert(word: Word)

    @Update
    fun update(word: Word)

    @Delete
    fun delete(word: Word)

    @Query("SELECT * FROM word_table ORDER BY  text_word")
    fun getWord(): LiveData<List<Word>>

    @Query("UPDATE word_table SET status_word = :nuevoStatus WHERE word_id = :wordId")
    fun updateStatus(wordId: Int, nuevoStatus: String)





}