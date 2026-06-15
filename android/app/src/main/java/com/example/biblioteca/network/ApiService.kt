package com.example.biblioteca.network

import com.example.biblioteca.models.Livro
import com.example.biblioteca.models.EmprestimoRequest
import com.example.biblioteca.models.Emprestimo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("livros")
    fun getLivros(): Call<List<Livro>>

    @GET("livros/{id}")
    fun getLivro(@Path("id") id: Int): Call<Livro>

    @POST("emprestimos")
    fun realizarEmprestimo(@Body request: EmprestimoRequest): Call<Emprestimo>

    @GET("emprestimos/{usuario_id}")
    fun getEmprestimosUsuario(@Path("usuario_id") userId: String): Call<List<Emprestimo>>

    @PUT("emprestimos/{id}/devolver")
    fun devolverLivro(@Path("id") emprestimoId: Int): Call<Emprestimo>

    @POST("livros")
    fun adicionarLivro(@Body livro: Livro): Call<Livro>
}
