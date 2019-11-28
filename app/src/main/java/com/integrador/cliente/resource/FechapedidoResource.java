package com.integrador.cliente.resource;


import com.integrador.cliente.model.Fechapedido;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FechapedidoResource {


    @POST("/fechapedido/{id}")
    Call<Fechapedido> post(@Path("id") int id);


}