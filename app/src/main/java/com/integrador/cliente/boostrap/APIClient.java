package com.integrador.cliente.boostrap;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

//    public static final String ENDPOINT = "http://192.168.107.118:3001/";
    public static final String ENDPOINT = "http://192.168.15.7:3001/";

    public static Retrofit getClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

}
