package com.example.biblioteca

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.biblioteca.models.Emprestimo
import com.example.biblioteca.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReturnBookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_return_book)

        val topAppBar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBarReturn)
        topAppBar.setNavigationOnClickListener {
            finish()
        }

        val emprestimoId = intent.getIntExtra("EMPRESTIMO_ID", -1)
        val bookTitle = intent.getStringExtra("BOOK_TITLE") ?: "Este livro"

        if (emprestimoId == -1) {
            Toast.makeText(this, "Erro ao carregar empréstimo", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val tvMessage = findViewById<TextView>(R.id.tvReturnMessage)
        tvMessage.text = "Deseja devolver o livro:\n$bookTitle?"

        val btnConfirm = findViewById<Button>(R.id.btnConfirmReturn)
        btnConfirm.setOnClickListener {
            btnConfirm.isEnabled = false
            RetrofitClient.instance.devolverLivro(emprestimoId).enqueue(object : Callback<Emprestimo> {
                override fun onResponse(call: Call<Emprestimo>, response: Response<Emprestimo>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@ReturnBookActivity, "Livro devolvido com sucesso!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@ReturnBookActivity, MainActivity::class.java)
                        // Clear the backstack so we go back cleanly to the main page
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    } else {
                        btnConfirm.isEnabled = true
                        Toast.makeText(this@ReturnBookActivity, "Erro ao devolver o livro.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Emprestimo>, t: Throwable) {
                    btnConfirm.isEnabled = true
                    Toast.makeText(this@ReturnBookActivity, "Erro de conexão.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
