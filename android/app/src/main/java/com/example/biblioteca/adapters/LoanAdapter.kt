package com.example.biblioteca.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.biblioteca.R
import com.example.biblioteca.ReturnBookActivity
import com.example.biblioteca.models.Emprestimo

class LoanAdapter(private val loans: List<Emprestimo>) : RecyclerView.Adapter<LoanAdapter.LoanViewHolder>() {

    // Filtra para exibir apenas os empréstimos ativos (não devolvidos)
    private val activeLoans = loans.filter { !it.devolvido }

    class LoanViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivCover: ImageView = view.findViewById(R.id.ivLoanCover)
        val tvTitle: TextView = view.findViewById(R.id.tvLoanTitle)
        val tvAuthor: TextView = view.findViewById(R.id.tvLoanAuthor)
        val tvDeadline: TextView = view.findViewById(R.id.tvLoanDeadline)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loan, parent, false)
        return LoanViewHolder(view)
    }

    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {
        val loan = activeLoans[position]
        val libro = loan.livro
        
        holder.tvTitle.text = libro?.titulo ?: "Livro Desconhecido"
        holder.tvAuthor.text = libro?.autor ?: ""
        holder.tvDeadline.text = "Prazo: ${loan.data_devolucao}"

        Glide.with(holder.itemView.context)
            .load(libro?.imagem_url)
            .centerCrop()
            .into(holder.ivCover)

        // Abre a tela de devolução ao clicar no livro
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ReturnBookActivity::class.java)
            intent.putExtra("EMPRESTIMO_ID", loan.id)
            intent.putExtra("BOOK_TITLE", libro?.titulo ?: "Desconhecido")
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = activeLoans.size
}
