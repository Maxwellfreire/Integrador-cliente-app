package com.integrador.cliente.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.integrador.cliente.Activitys.CarrinhoActivity;
import com.integrador.cliente.MainActivity;
import com.integrador.cliente.R;
import com.integrador.cliente.boostrap.APIClient;
import com.integrador.cliente.model.Carrinho;
import com.integrador.cliente.model.Produto;
import com.integrador.cliente.resource.CarrinhoResource;
import com.integrador.cliente.resource.ProdutoResource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static androidx.core.content.ContextCompat.startActivity;


/**
 * Created by bruno on 07/04/18.
 */

public class APIAdapterCarrinho extends BaseAdapter {


    Context context;
    List<Carrinho> colecao;
    private Carrinho Items;

    public APIAdapterCarrinho(final Context applicationContext,
                              final List<Carrinho> colecao) {
        this.context = applicationContext;
        this.colecao = colecao;

    }

    @Override
    public int getCount() {
        return this.colecao != null ? this.colecao.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return this.colecao.get(i);
    }

    private Carrinho parsetItem(int i) {
        return this.colecao.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Carrinho Item = (Carrinho) getItem(i);


        if (view == null) {
            view = LayoutInflater.from(context).
                    inflate(R.layout.item_carrinho,
                            viewGroup, false);
        }

        // pega o objeto corrente da lista
        final Carrinho carrinho = parsetItem(i);

        final TextView campoQuantidade, campoNOMEitem, campoValorItem, campoSUBItem;

        view = LayoutInflater.from(context).inflate(R.layout.item_carrinho, null);

        campoQuantidade = view.findViewById(R.id.getQuantiItem);
        campoNOMEitem = view.findViewById(R.id.getNomeItem);
        campoValorItem = view.findViewById(R.id.getValorItem);
        campoSUBItem = view.findViewById(R.id.getSUBItem);


        campoQuantidade.setText(Integer.toString(carrinho.getQtd()));


        Retrofit retrofit = APIClient.getClient();

        ProdutoResource produtoResource = retrofit.create(ProdutoResource.class);

        Call<List<Produto>> get = produtoResource.get();

        get.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {


                Produto p = new Produto();


                List<Produto> produtos = response.body();

                for (Produto produto : produtos) {
                    if (produto.getIdProduto() == carrinho.getNr_produto()) {
                        p = produto;
                    }
                }


                campoNOMEitem.setText(p.getNameProduto());


            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }


        });


        campoValorItem.setText(carrinho.getValor_unidade());

        campoSUBItem.setText(carrinho.getSub_total());

        final View finalView = view;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(finalView.getRootView().getContext());
                alertDialogBuilder.setIcon(R.drawable.tick);
                alertDialogBuilder.setTitle("Confirmar exclusão...");
                alertDialogBuilder.setMessage("Tem certeza que você deseja excluir este item?");
                alertDialogBuilder.setPositiveButton("NÂO",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                arg0.cancel();

                            }
                        });

                alertDialogBuilder.setNegativeButton("SIM",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                final Integer pedido = Item.getNr_pedido();
                                final Integer numeroproduto = Item.getNr_produto();


                                Retrofit retrofit = APIClient.getClient();
                                CarrinhoResource carrinhoResource = retrofit.create(CarrinhoResource.class);
                                Call<Void> call = carrinhoResource.delete(pedido, numeroproduto);

                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {


                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });


                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });


        return view;
    }

}
