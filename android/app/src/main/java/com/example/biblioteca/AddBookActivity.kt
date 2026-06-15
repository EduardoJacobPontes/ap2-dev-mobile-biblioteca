package com.example.biblioteca

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.biblioteca.models.Livro
import com.example.biblioteca.network.RetrofitClient
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddBookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Emprestar meu Livro"

        val etTitle = findViewById<TextInputEditText>(R.id.etAddTitle)
        val etAuthor = findViewById<TextInputEditText>(R.id.etAddAuthor)
        val etDescription = findViewById<TextInputEditText>(R.id.etAddDescription)
        val etImageUrl = findViewById<TextInputEditText>(R.id.etAddImageUrl)
        val btnSubmit = findViewById<Button>(R.id.btnSubmitBook)

        btnSubmit.setOnClickListener {
            val titulo = etTitle.text.toString()
            val autor = etAuthor.text.toString()
            val descricao = etDescription.text.toString()
            val imagemUrl = etImageUrl.text.toString()

            if (titulo.isEmpty() || autor.isEmpty()) {
                Toast.makeText(this, "Título e Autor são obrigatórios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val novoLivro = Livro(
                id = 0, // O ID será gerado pelo servidor
                titulo = titulo,
                autor = autor,
                descricao = descricao,
                imagem_url = imagemUrl,
                disponivel = true
            )

            RetrofitClient.instance.adicionarLivro(novoLivro).enqueue(object : Callback<Livro> {
                override fun onResponse(call: Call<Livro>, response: Response<Livro>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@AddBookActivity, "Livro disponibilizado com sucesso!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@AddBookActivity, "Erro ao adicionar livro", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Livro>, t: Throwable) {
                    Toast.makeText(this@AddBookActivity, "Erro de rede: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
