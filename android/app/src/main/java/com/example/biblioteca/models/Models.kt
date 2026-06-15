package com.example.biblioteca.models

data class Livro(
    val id: Int,
    val titulo: String,
    val autor: String,
    val descricao: String,
    val imagem_url: String,
    val disponivel: Boolean
)

data class Emprestimo(
    val id: Int,
    val livro_id: Int,
    val usuario_id: String,
    val data_emprestimo: String,
    val data_devolucao: String,
    val devolvido: Boolean,
    val livro: Livro?
)

data class EmprestimoRequest(
    val livro_id: Int,
    val usuario_id: String,
    val data_emprestimo: String,
    val data_devolucao: String
)
