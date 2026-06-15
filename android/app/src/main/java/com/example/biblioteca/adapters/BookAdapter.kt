package com.example.biblioteca.adapters

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.biblioteca.BookDetailActivity
import com.example.biblioteca.R
import com.example.biblioteca.models.Livro

class BookAdapter(private val livros: List<Livro>) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvAuthor: TextView = view.findViewById(R.id.tvAuthor)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)
        val ivCover: ImageView = view.findViewById(R.id.ivCover)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val livro = livros[position]
        holder.tvTitle.text = livro.titulo
        holder.tvAuthor.text = livro.autor
        
        if (livro.disponivel) {
            holder.tvStatus.text = "Disponível"
            holder.tvStatus.setTextColor(Color.parseColor("#4CAF50"))
        } else {
            holder.tvStatus.text = "Emprestado"
            holder.tvStatus.setTextColor(Color.parseColor("#F44336"))
        }

        Glide.with(holder.itemView.context)
            .load(livro.imagem_url)
            .centerCrop()
            .into(holder.ivCover)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, BookDetailActivity::class.java)
            intent.putExtra("BOOK_ID", livro.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = livros.size
}
