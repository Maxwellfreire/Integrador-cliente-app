package com.integrador.cliente.resource;


import com.integrador.cliente.model.Carrinho;
import com.integrador.cliente.model.Total;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TotalResource {


    @GET("/total")
    Call<List<Total>> get();


    @GET("/total/{id}")
    Call<List<Total>> get(@Path("id") int id);


    @POST("/total")
    Call<Total> post(@Body Total total);

    @PUT("/total/{id}")
    Call<Total> put(@Path("id") int id, @Body Total total);

    @DELETE("/total/{id}")
    Call<Void> delete(@Path("id") int id);


}