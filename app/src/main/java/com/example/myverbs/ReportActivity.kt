package com.example.myverbs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
//import com.example.myverbs.ConocidosFragment.Companion.newInstance



class ReportActivity : AppCompatActivity() {


    private lateinit var wordViewModel: WordWiewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        val conocidosFragment = ConocidosFragment()
        val porAprenderFragment = PorAprenderFragment()

        val fragmentContainer: FrameLayout = findViewById(R.id.fragmentContainer)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        wordViewModel = ViewModelProvider(this).get(WordWiewModel::class.java)


            // Cargar el fragment inicial
            loadFragment(conocidosFragment)

            // Manejar los clics en el BottomNavigationView
            bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_known_verbs -> {
                        loadFragment(conocidosFragment)
                        true
                    }
                    R.id.action_unknown_verbs -> {
                        loadFragment(porAprenderFragment)

                        true
                    }
                    else -> false
                }
            }

        // Observar cambios en los verbos conocidos
        wordViewModel.words?.observe(this, Observer { words ->
            val verbosConocidos = words.filter { it.status == "Conocido" }
            conocidosFragment.actualizarLista(verbosConocidos)
        })

        // Observar cambios en los verbos por aprender
        wordViewModel.words?.observe(this, Observer { words ->
            val verbosPorAprender = words.filter { it.status == "PorAprender" }
            porAprenderFragment.actualizarLista(verbosPorAprender)
        })


    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }





}
