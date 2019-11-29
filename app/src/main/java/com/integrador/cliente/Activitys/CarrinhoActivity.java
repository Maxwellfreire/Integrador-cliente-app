package com.integrador.cliente.Activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.integrador.cliente.model.Fechapedido;
import com.integrador.cliente.model.Pedido;
import com.integrador.cliente.model.Total;
import com.integrador.cliente.resource.CarrinhoResource;
import com.integrador.cliente.resource.FechapedidoResource;
import com.integrador.cliente.resource.PedidoResource;
import com.integrador.cliente.resource.TotalResource;

import java.util.ArrayList;
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
        minhaListaCarrinho = (ListView) findViewById(R.id.minhaListaCarrinho);
        registerForContextMenu(minhaListaCarrinho);

        Atualizar();

        Intent intent = getIntent();
        int IDproduto = intent.getIntExtra("IDproduto", 0);
        int numeropedido = intent.getIntExtra("numeropedido", 0);


        refreshCarrinho = (PullRefreshLayout) findViewById(R.id.refreshCarrinho);

        refreshCarrinho.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Atualizar();

            }
        });

        refreshCarrinho.setRefreshing(false);

        minhaListaCarrinho.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                Toast.makeText(getApplicationContext(), "Click longo!" + position, Toast.LENGTH_SHORT).show();


                return false;
            }
        });


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem mDelete = menu.add("Delete Registro");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Toast.makeText(getApplicationContext(), "Registro deletado com sucesso!", Toast.LENGTH_SHORT).show();

                return false;
            }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    protected void onResume() {
        super.onResume();
        Atualizar();
    }

    private void Atualizar() {

        Intent intent = getIntent();
        final int numeropedido = intent.getIntExtra("numeropedido", 0);


        Retrofit retrofit = APIClient.getClient();

        CarrinhoResource carrinhoResource = retrofit.create(CarrinhoResource.class);

        Call<List<Carrinho>> get = carrinhoResource.get(numeropedido);

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

                    Call<List<Total>> get = totalResource.get(numeropedido);

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

                    Intent intent = getIntent();
                    final int IDproduto = intent.getIntExtra("numeropedido", 0);
                    String CPF;
                    CPF = userCPF.getText().toString();

                    Pedido cpf = new Pedido(CPF);
                    Retrofit retrofit = APIClient.getClient();
                    PedidoResource pedidoResource = retrofit.create(PedidoResource.class);
                    Call<Pedido> call = pedidoResource.post(IDproduto, cpf);

                    call.enqueue(new Callback<Pedido>() {
                        @Override
                        public void onResponse(Call<Pedido> call, Response<Pedido> response) {


                            Retrofit retrofit = APIClient.getClient();
                            FechapedidoResource fechapedidoResource = retrofit.create(FechapedidoResource.class);
                            Call<Fechapedido> calll = fechapedidoResource.post(IDproduto);

                            calll.enqueue(new Callback<Fechapedido>() {
                                @Override
                                public void onResponse(Call<Fechapedido> calll, Response<Fechapedido> response) {


                                }

                                @Override
                                public void onFailure(Call<Fechapedido> calll, Throwable t) {

                                    String CPF = null;

                                    Pedido login = new Pedido(CPF);
                                    Retrofit retrofit = APIClient.getClient();
                                    PedidoResource pedidoResource = retrofit.create(PedidoResource.class);
                                    Call<Pedido> call = pedidoResource.post(login);

                                    call.enqueue(new Callback<Pedido>() {
                                        @Override
                                        public void onResponse(Call<Pedido> call, Response<Pedido> response) {


                                        }

                                        @Override
                                        public void onFailure(Call<Pedido> call, Throwable t) {
                                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                    finish();

                                }
                            });


                        }

                        @Override
                        public void onFailure(Call<Pedido> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }
        });


        builder.setPositiveButton("NÂO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = getIntent();
                        final int numeropedido = intent.getIntExtra("numeropedido", 0);

                        Retrofit retrofit = APIClient.getClient();
                        FechapedidoResource fechapedidoResource = retrofit.create(FechapedidoResource.class);
                        Call<Fechapedido> calll = fechapedidoResource.post(numeropedido);

                        calll.enqueue(new Callback<Fechapedido>() {
                            @Override
                            public void onResponse(Call<Fechapedido> calll, Response<Fechapedido> response) {


                            }

                            @Override
                            public void onFailure(Call<Fechapedido> calll, Throwable t) {

                                String CPF = null;

                                Pedido login = new Pedido(CPF);
                                Retrofit retrofit = APIClient.getClient();
                                PedidoResource pedidoResource = retrofit.create(PedidoResource.class);
                                Call<Pedido> call = pedidoResource.post(login);

                                call.enqueue(new Callback<Pedido>() {
                                    @Override
                                    public void onResponse(Call<Pedido> call, Response<Pedido> response) {


                                    }

                                    @Override
                                    public void onFailure(Call<Pedido> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });

                                finish();

                            }
                        });
                    }
                });


        builder.show();

    }
}
