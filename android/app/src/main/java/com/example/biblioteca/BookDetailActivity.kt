package com.example.biblioteca

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.biblioteca.models.Livro
import com.example.biblioteca.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookDetailActivity : AppCompatActivity() {

    private var currentBook: Livro? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        val topAppBar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        topAppBar.setNavigationOnClickListener {
            finish()
        }

        val bookId = intent.getIntExtra("BOOK_ID", -1)
        if (bookId == -1) {
            finish()
            return
        }

        val ivCover = findViewById<ImageView>(R.id.ivDetailCover)
        val tvTitle = findViewById<TextView>(R.id.tvDetailTitle)
        val tvAuthor = findViewById<TextView>(R.id.tvDetailAuthor)
        val tvDescription = findViewById<TextView>(R.id.tvDetailDescription)
        val btnBorrow = findViewById<Button>(R.id.btnBorrow)
        val btnShare = findViewById<Button>(R.id.btnShare)

        RetrofitClient.instance.getLivro(bookId).enqueue(object : Callback<Livro> {
            override fun onResponse(call: Call<Livro>, response: Response<Livro>) {
                if (response.isSuccessful) {
                    currentBook = response.body()
                    currentBook?.let { livro ->
                        tvTitle.text = livro.titulo
                        tvAuthor.text = livro.autor
                        tvDescription.text = livro.descricao
                        
                        Glide.with(this@BookDetailActivity)
                            .load(livro.imagem_url)
                            .fitCenter()
                            .into(ivCover)

                        btnBorrow.isEnabled = livro.disponivel
                        if (!livro.disponivel) {
                            btnBorrow.text = "Indisponível"
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Livro>, t: Throwable) {
                Toast.makeText(this@BookDetailActivity, "Erro ao carregar detalhes", Toast.LENGTH_SHORT).show()
            }
        })

        btnBorrow.setOnClickListener {
            val intent = Intent(this, BorrowActivity::class.java)
            intent.putExtra("BOOK_ID", bookId)
            currentBook?.let { livro -> intent.putExtra("BOOK_TITLE", livro.titulo) }
            startActivity(intent)
        }

        btnShare.setOnClickListener {
            currentBook?.let { livro ->
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Veja este livro!")
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Estou lendo ${livro.titulo} de ${livro.autor}. Recomendo muito!")
                startActivity(Intent.createChooser(shareIntent, "Compartilhar livro via"))
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
