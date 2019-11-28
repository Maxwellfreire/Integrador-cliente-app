package com.integrador.cliente.resource;


import com.integrador.cliente.model.Pedido;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PedidoResource {


    @GET("/pedido")
    Call<List<Pedido>> get();


    @GET("/pedido/{id}")
    Call<List<Pedido>> get(Integer id);

    @POST("/pedido")
    Call<Pedido> post(@Body Pedido pedido);

    @POST("/pedido/{id}")
    Call<Pedido> post(@Path("id") int id, @Body Pedido pedido);

    @PUT("/pedido/{id}")
    Call<Pedido> put(@Path("id") int id, @Body Pedido pedido);

    @DELETE("/pedido/{id}")
    Call<Void> delete(@Path("id") int id);
}