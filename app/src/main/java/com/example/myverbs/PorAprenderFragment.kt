package com.example.myverbs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

class PorAprenderFragment : Fragment() {
    /*companion object {
        private const val ARG_VERBOS_POR_APRENDER = "verbosPorAprender"

        fun newInstance(verbosPorAprender: List<Word>): PorAprenderFragment {
            val fragment = PorAprenderFragment()
            val args = Bundle()
            args.putSerializable(ARG_VERBOS_POR_APRENDER, verbosPorAprender as Serializable)
            fragment.arguments = args
            return fragment
        }
    }*/

    private lateinit var recyclerView: RecyclerView
    private lateinit var verboAdapter: WordAdapter

    private lateinit var wordViewModel: WordWiewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val rootView = inflater.inflate(R.layout.fragment_por_aprender, container, false)

        recyclerView = rootView.findViewById(R.id.recyclerViewVerbos)
        verboAdapter = WordAdapter()

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = verboAdapter

        wordViewModel = ViewModelProvider(this).get(WordWiewModel::class.java)

        // Observar los cambios en la lista de verbos Pofr Aprender
        wordViewModel.words?.observe(viewLifecycleOwner, Observer { words ->
            val verbosConocidos = words.filter { it.status == "PorAprender" }
            verboAdapter.setWords(verbosConocidos)
        })

        return rootView
    }

    fun actualizarLista(verbos: List<Word>) {
        // Asegúrate de que verboAdapter esté inicializado antes de usarlo
        if (::verboAdapter.isInitialized) {
            verboAdapter.setWords(verbos)
        }
    }


}