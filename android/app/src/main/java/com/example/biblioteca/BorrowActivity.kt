package com.example.biblioteca

import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.biblioteca.models.EmprestimoRequest
import com.example.biblioteca.models.Emprestimo
import com.example.biblioteca.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BorrowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_borrow)

        val topAppBar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBarBorrow)
        topAppBar.setNavigationOnClickListener {
            finish()
        }

        val bookId = intent.getIntExtra("BOOK_ID", -1)
        val bookTitle = intent.getStringExtra("BOOK_TITLE") ?: ""

        val tvTitle = findViewById<TextView>(R.id.tvBorrowBookTitle)
        val spinnerDuration = findViewById<Spinner>(R.id.spinnerDuration)
        val cbTerms = findViewById<CheckBox>(R.id.cbTerms)
        val btnConfirm = findViewById<Button>(R.id.btnConfirmBorrow)

        tvTitle.text = "Emprestar: $bookTitle"

        val durations = arrayOf("7 dias", "14 dias", "30 dias")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, durations)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDuration.adapter = adapter

        btnConfirm.setOnClickListener {
            if (!cbTerms.isChecked) {
                Toast.makeText(this, "Aceite os termos para continuar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
            val userId = sharedPref.getString("USER_ID", "Usuário") ?: "Usuário"

            val durationText = spinnerDuration.selectedItem.toString()
            val days = durationText.split(" ")[0].toInt()
            
            val request = EmprestimoRequest(
                livro_id = bookId,
                usuario_id = userId,
                data_emprestimo = "Hoje",
                data_devolucao = "Daqui a $days dias"
            )

            RetrofitClient.instance.realizarEmprestimo(request).enqueue(object : Callback<Emprestimo> {
                override fun onResponse(call: Call<Emprestimo>, response: Response<Emprestimo>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@BorrowActivity, "Empréstimo realizado com sucesso!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@BorrowActivity, "Erro no empréstimo", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Emprestimo>, t: Throwable) {
                    Toast.makeText(this@BorrowActivity, "Falha na rede", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
