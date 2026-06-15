package com.example.biblioteca.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.biblioteca.R
import com.example.biblioteca.models.Emprestimo

class LoanAdapter(private val loans: List<Emprestimo>) : RecyclerView.Adapter<LoanAdapter.LoanViewHolder>() {

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
        val loan = loans[position]
        val libro = loan.livro
        
        holder.tvTitle.text = libro?.titulo ?: "Livro Desconhecido"
        holder.tvAuthor.text = libro?.autor ?: ""
        holder.tvDeadline.text = "Prazo: ${loan.data_devolucao}"

        Glide.with(holder.itemView.context)
            .load(libro?.imagem_url)
            .centerCrop()
            .into(holder.ivCover)
    }

    override fun getItemCount(): Int = loans.size
}
