package com.integrador.cliente.resource;


import com.integrador.cliente.model.Carrinho;
import com.integrador.cliente.model.Pedido;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CarrinhoResource {


    @GET("/carrinho")
    Call<List<Carrinho>> get();


    @GET("/carrinho/{id}")
    Call<List<Carrinho>> get(Integer id);

    @POST("/carrinho")
    Call<Carrinho> post(@Body Carrinho carrinho);

    @PUT("/carrinho/{id}")
    Call<Carrinho> put(@Path("id") int id, @Body Carrinho carrinho);

    @DELETE("/carrinho/{id}")
    Call<Void> delete(@Path("id") int id);
}