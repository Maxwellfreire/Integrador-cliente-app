package com.integrador.cliente.Activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.google.android.material.snackbar.Snackbar;
import com.integrador.cliente.R;
import com.integrador.cliente.adapters.APIAdapterCarrinho;
import com.integrador.cliente.boostrap.APIClient;
import com.integrador.cliente.model.Carrinho;
import com.integrador.cliente.model.Pedido;
import com.integrador.cliente.model.Total;
import com.integrador.cliente.resource.CarrinhoResource;
import com.integrador.cliente.resource.PedidoResource;
import com.integrador.cliente.resource.TotalResource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CarrinhoActivity extends AppCompatActivity {

    ListView minhaListaCarrinho;
    PullRefreshLayout refreshCarrinho;
    TextView TotalItens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        Atualizar();

        Intent intent = getIntent();
        int IDproduto = intent.getIntExtra("numeropedido", 0);


        refreshCarrinho = (PullRefreshLayout) findViewById(R.id.refreshCarrinho);

        refreshCarrinho.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Atualizar();

            }
        });

        refreshCarrinho.setRefreshing(false);


    }

    protected void onResume() {
        super.onResume();
        Atualizar();
    }

    private void Atualizar() {

        Intent intent = getIntent();
        final int IDproduto = intent.getIntExtra("numeropedido", 0);


        Retrofit retrofit = APIClient.getClient();

        CarrinhoResource carrinhoResource = retrofit.create(CarrinhoResource.class);

        Call<List<Carrinho>> get = carrinhoResource.get(IDproduto);

        get.enqueue(new Callback<List<Carrinho>>() {
            @Override
            public void onResponse(Call<List<Carrinho>> call, Response<List<Carrinho>> response) {

                minhaListaCarrinho = findViewById(R.id.minhaListaCarrinho);
                List<Carrinho> carrinhos = response.body();
                APIAdapterCarrinho apiAdapterCarrinho = new APIAdapterCarrinho(getApplicationContext(), carrinhos);
                minhaListaCarrinho.setAdapter(apiAdapterCarrinho);

                apiAdapterCarrinho.notifyDataSetChanged();
                refreshCarrinho.setRefreshing(false);

                if (carrinhos != null) {


                    Retrofit retrofit = APIClient.getClient();

                    TotalResource totalResource = retrofit.create(TotalResource.class);

                    Call<List<Total>> get = totalResource.get(IDproduto);

                    get.enqueue(new Callback<List<Total>>() {
                        @Override
                        public void onResponse(Call<List<Total>> call, Response<List<Total>> response) {

                            Total t = new Total();

                            List<Total> totals = response.body();

                            for (Total total : totals) {

                                t = total;

                            }

                            TotalItens = findViewById(R.id.TotalItens);

                            TotalItens.setText(t.getValor_total());


                        }

                        @Override
                        public void onFailure(Call<List<Total>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                        }


                    });
                }


            }

            @Override
            public void onFailure(Call<List<Carrinho>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }


        });


    }

    public void Finalizar(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(CarrinhoActivity.this);

        builder.setTitle("Deseja informa o CPF ?");
        LayoutInflater inflater = getLayoutInflater();
        final View dialoglayout = inflater.inflate(R.layout.custom_aleartdialog, null);

        builder.setView(dialoglayout);

        final EditText userCPF = (EditText) dialoglayout.findViewById(R.id.editCPF);


        builder.setNegativeButton("SIM", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (userCPF.getText().toString().isEmpty()) {

                    Toast.makeText(getApplicationContext(), "CPF está vazio!", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getApplicationContext(), "OK !!", Toast.LENGTH_SHORT).show();
                }


            }
        });


        builder.setPositiveButton("NÂO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });


        builder.show();

    }
}
