package com.example.biblioteca

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.biblioteca.adapters.BookAdapter
import com.example.biblioteca.models.Livro
import com.example.biblioteca.network.RetrofitClient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var rvBooks: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var fabAddBook: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        rvBooks = view.findViewById(R.id.rvBooks)
        progressBar = view.findViewById(R.id.progressBar)
        fabAddBook = view.findViewById(R.id.fabAddBook)
        
        rvBooks.layoutManager = LinearLayoutManager(context)

        fabAddBook.setOnClickListener {
            val intent = Intent(context, AddBookActivity::class.java)
            startActivity(intent)
        }
        carregarLivros()
        
        return view
    }

    private fun carregarLivros() {
        progressBar.visibility = View.VISIBLE
        RetrofitClient.instance.getLivros().enqueue(object : Callback<List<Livro>> {
            override fun onResponse(call: Call<List<Livro>>, response: Response<List<Livro>>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val livros = response.body() ?: emptyList()
                    rvBooks.adapter = BookAdapter(livros)
                }
            }

            override fun onFailure(call: Call<List<Livro>>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(context, "Erro ao carregar livros", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
