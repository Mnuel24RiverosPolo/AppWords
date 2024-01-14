package com.example.myverbs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_all_words.*
import kotlinx.android.synthetic.main.dialog_word.view.*

class AllWordsActivity : AppCompatActivity() {

    private lateinit var wordViewModel: WordWiewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_words)

        title = "Words"

        wordViewModel = run {
            ViewModelProviders.of(this).get(WordWiewModel::class.java)
        }
        floatingRegistrarWords.setOnClickListener{
            registrarWord()
        }

        floatingGoPractice.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        val adapter = WordAdapter()
        recyclerViewWords.adapter = adapter
        recyclerViewWords.layoutManager = LinearLayoutManager(this)

        wordViewModel.words?.observe(this, Observer{
            if (it.isNotEmpty()) {

                it?.let {
                    adapter.setWords(it)
                }
            }
        })


    }

    private fun registrarWord() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_word, null)
        val titleAlert = "Registrar Word"
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle(titleAlert)
        val mAlertDialog = mBuilder.show()
        mDialogView.btnRegistrar.setOnClickListener {
            val textWord = mDialogView.edtWordText.text.toString()
            val meaningWord = mDialogView.edtWordMeaning.text.toString()

            val word = Word( textWord, meaningWord, "a")
            wordViewModel.guardarWords(word)
            mAlertDialog.dismiss()
        }
    }
}