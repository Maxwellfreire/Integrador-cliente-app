package com.integrador.cliente.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.integrador.cliente.R;
import com.integrador.cliente.boostrap.APIClient;
import com.integrador.cliente.model.Carrinho;
import com.integrador.cliente.model.Produto;
import com.integrador.cliente.resource.ProdutoResource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


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


        return view;
    }

}
