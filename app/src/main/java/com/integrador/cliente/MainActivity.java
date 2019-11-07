package com.integrador.cliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.integrador.cliente.adapters.RecyclerViewAdapter;
import com.integrador.cliente.boostrap.APIClient;
import com.integrador.cliente.model.Produto;
import com.integrador.cliente.resource.ProdutoResource;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    private ProgressDialog pDialog;
    PullRefreshLayout refreshProdutoCli;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusdeconectividade();

        pDialog = new ProgressDialog(MainActivity.this);


        Atualizar();

        refreshProdutoCli = (PullRefreshLayout) findViewById(R.id.refreshProdutoCli);

        refreshProdutoCli.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                statusdeconectividade();
                Atualizar();

            }
        });

        refreshProdutoCli.setRefreshing(false);


    }

    protected void onResume() {
        super.onResume();
        Atualizar();
    }


    private void Atualizar() {
        pDialog.setMessage("Carregando...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Retrofit retrofit = APIClient.getClient();

        ProdutoResource produtoResource = retrofit.create(ProdutoResource.class);

        Call<List<Produto>> get = produtoResource.get();

        get.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                //Dismiss Dialog
                pDialog.dismiss();
                //Se deu certo executa este método
                RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
                List<Produto> produtos = response.body();
                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getApplicationContext(), produtos);
                myrv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                myrv.setAdapter(myAdapter);

                myAdapter.notifyDataSetChanged();
                refreshProdutoCli.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }


        });


    }

    private void statusdeconectividade() {

        boolean wifiConnected;
        boolean mobileConnected;
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) { //connected with either mobile or wifi
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if (wifiConnected) { //wifi connected

            } else if (mobileConnected) { //Conectado com a conexão de dados móveis

            }
        } else {

            mensagemalerta();

        }
    }

    private void mensagemalerta() {
        // Creating alert Dialog with one Button
        AlertDialog alertDialog = new AlertDialog.Builder(
                MainActivity.this).create();
        // Setting Dialog Title
        alertDialog.setTitle("Parece que não há internet!");
        // Setting Dialog Message
        alertDialog.setMessage("Verifique a sua conexão para continuar navegando.");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.tick);
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog
                // closed
                //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }


}
