package com.example.myverbs
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*

import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_conocidos.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    lateinit var viewFlipper: ViewFlipper

    lateinit var cardView: CardView
   lateinit var cardViewBack: CardView
    lateinit var textFront: TextView
    lateinit var textBack: TextView
   // lateinit var btnAvanze: Button



    private var initialX: Float = 0.toFloat()
    private var isFlipped: Boolean = false

    private val verbosConocidos = mutableListOf<Word>()
    private val verbosPorAprender = mutableListOf<Word>()
    private lateinit var viewModel: WordWiewModel


    private lateinit var editTextAnswer: EditText
    private  lateinit var  titleAnswer: TextView
    private lateinit var  textInfoCard: TextView
    private  lateinit var  btnRightArrow: ImageButton

    private lateinit var wordViewModel: WordWiewModel
    //private var currentIndex: Int = 0
    private lateinit var verbos: List<Word>

    /*private var verbos: List<Word> = listOf(
        Word("Jump", "Saltar"),
        Word("Run", "Correr"),
        Word("Larn", "Aprender"),


     )*/

    private var currentIndex: Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(WordWiewModel::class.java)

        viewFlipper = findViewById(R.id.viewFlipper)
       // viewFlipper.visibility = View.GONE
        cardView = findViewById(R.id.cardView)
        cardViewBack = findViewById(R.id.cardViewBack)
        textFront = findViewById(R.id.textFront)
        textBack = findViewById(R.id.textBack)


        editTextAnswer = findViewById(R.id.editTextAnswer)
        titleAnswer = findViewById(R.id.titleAnswer)
        textInfoCard = findViewById(R.id.textInfoCard)
        btnRightArrow = findViewById(R.id.btnRightArrow)


        viewModel.words?.observe(this, Observer { words ->
            words?.let {
                // Actualiza la lista de verbos en tu actividad
                verbos = it
                val verboIngles = verbos[currentIndex].text
                val significado = verbos[currentIndex].meanig

                // Mostrar el verbo en el lado frontal de la tarjeta
                textFront.text = verboIngles

                // Mostrar el significado en el lado posterior de la tarjeta
                textBack.text = significado

                btnRightArrow.setOnClickListener{
                    resetCard()
                }
                editTextAnswer.setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        // Verificar la respuesta y animar cuando se presiona "Done" en el teclado
                        checkAnswerAndAnimate()

                        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(editTextAnswer.windowToken, 0)

                        editTextAnswer.visibility = View.GONE

                        return@setOnEditorActionListener true
                    }
                    return@setOnEditorActionListener false
                }
                val btnNextLayout: Button = findViewById(R.id.btnAvanze)
                btnNextLayout.setOnClickListener {

                    // Intent para ir a otro layout (reemplaza YourNextActivity::class.java con la actividad que desees)
                    val intent = Intent(this, ReportActivity::class.java)

                    startActivity(intent)
                }

            }

        })

    }
    private fun checkAnswerAndAnimate() {
        val userAnswer = editTextAnswer.text.toString().trim()
        val correctAnswer = textBack.text.toString().trim()

        if (userAnswer.equals(correctAnswer, ignoreCase = true)) {
            // Animation for correct answer
            flipCardAnimation()
            titleAnswer.visibility = View.VISIBLE
            titleAnswer.text = "Correcto !!"
            textInfoCard.text = "Ya conoces esta Palabra"

            textBack.background.setColorFilter(Color.parseColor("#81DA84"), PorterDuff.Mode.SRC_IN)
            // Add to the list of known verbs
            val verboActualId = verbos[currentIndex].wordId
           viewModel.actualizarStatusPalabra(verboActualId, "Conocido")
            println("Verbos Conocidos: $verboActualId")

        } else {
            flipCardAnimation()
            titleAnswer.visibility = View.VISIBLE
            titleAnswer.text = "Incorrecto !!"
            textInfoCard.text = "Lo intentaras Otra vez"

             textBack.background.setColorFilter(Color.parseColor("#F1A4AB"), PorterDuff.Mode.SRC_IN)

            // Agregar a la lista de verbos por aprender
            val verboActual = verbos[currentIndex].wordId
            viewModel.actualizarStatusPalabra(verboActual, "PorAprender")
            println("Verbos por Aprender: $verboActual")


            // Handle incorrect answer if needed
            // You can add your logic here
        }

        // Clear the EditText for the next input
        editTextAnswer.text.clear()

        // Continue with the next card
      // showNextCard()
    }




    private fun resetCard() {
        // Volver a mostrar la tarjeta frontal
        viewFlipper.displayedChild = 0
        isFlipped = true

        // Puedes actualizar el contenido de la tarjeta si es necesario
        showNextCard()

    }

    private fun showNextCard() {
        if (currentIndex < verbos.size-1) {
            // Obtener el siguiente par de verbos y significados
            val verboIngles = verbos[currentIndex+1].text
            val significado = verbos[currentIndex+1].meanig

            // Mostrar el verbo en el lado frontal de la tarjeta
            textFront.text = verboIngles

            // Mostrar el significado en el lado posterior de la tarjeta
            textBack.text = significado

            // Incrementar el índice para la siguiente tarjeta
            currentIndex++

        } else {
            // Si has llegado al final de la lista, puedes reiniciar desde el principio
            currentIndex = 0
            showNextCard()
        }
        editTextAnswer.visibility = View.VISIBLE
        titleAnswer.visibility = View.GONE

    }

    private fun flipCardAnimation() {
        val oa1 = ObjectAnimator.ofFloat(cardView, "scaleX", 1f, 0f)
        val oa2 = ObjectAnimator.ofFloat(cardViewBack, "scaleX",  0f, 1f )
        val oa3 = ObjectAnimator.ofFloat(cardView, "scaleX",   1f)

        oa1.interpolator = AccelerateDecelerateInterpolator()
        oa2.interpolator = DecelerateInterpolator()

        oa1.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)

                if (viewFlipper.displayedChild == 0) {
                    textFront.text = "Nueva descripción para el lado frontal"
                    viewFlipper.showNext()
                    isFlipped =  true// Cambia a la vista posterior
                    oa3.start()
                } else   {
                    textBack.text = "Nuevo significado para el lado posterior"
                    viewFlipper.showPrevious() // Cambia a la vista frontal

                    isFlipped = true
                }

                oa2.start()

            }
        })

        oa1.duration = 1000
        oa2.duration = 2000

        oa1.start()


    }



    companion object {
        private const val SWIPE_THRESHOLD = 100
    }


}