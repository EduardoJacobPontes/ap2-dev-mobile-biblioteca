package com.example.biblioteca

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.biblioteca.adapters.LoanAdapter
import com.example.biblioteca.models.Emprestimo
import com.example.biblioteca.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private lateinit var rvMyBooks: RecyclerView
    private lateinit var tvProfileName: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        rvMyBooks = view.findViewById(R.id.rvMyBooks)
        tvProfileName = view.findViewById(R.id.tvProfileName)
        
        val sharedPref = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val userId = sharedPref.getString("USER_ID", "Usuário") ?: "Usuário"
        tvProfileName.text = "Olá, $userId"
        
        rvMyBooks.layoutManager = LinearLayoutManager(context)
        carregarMeusEmprestimos(userId)
        
        return view
    }

    private fun carregarMeusEmprestimos(userId: String) {
        RetrofitClient.instance.getEmprestimosUsuario(userId).enqueue(object : Callback<List<Emprestimo>> {
            override fun onResponse(call: Call<List<Emprestimo>>, response: Response<List<Emprestimo>>) {
                if (response.isSuccessful) {
                    val emprestimos = response.body() ?: emptyList()
                    rvMyBooks.adapter = LoanAdapter(emprestimos)
                }
            }

            override fun onFailure(call: Call<List<Emprestimo>>, t: Throwable) {
                Toast.makeText(context, "Erro ao carregar empréstimos", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
