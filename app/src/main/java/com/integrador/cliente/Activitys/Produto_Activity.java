package com.integrador.cliente.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.integrador.cliente.R;
import com.integrador.cliente.boostrap.APIClient;
import com.integrador.cliente.model.Carrinho;
import com.integrador.cliente.model.Pedido;
import com.integrador.cliente.resource.CarrinhoResource;
import com.integrador.cliente.resource.PedidoResource;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Produto_Activity extends AppCompatActivity {

    private TextView setnomeProduto, setdescProduto, setprecoProduto;
    private ImageView setimagemProduto;
    private Bitmap imagem;

    Spinner setQuantidadeItem;

    String[] SpinnerQTD = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11",
            "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"};

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        setQuantidadeItem = (Spinner) findViewById(R.id.setQuantidadeItem);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinnerQTD);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setQuantidadeItem.setAdapter(arrayAdapter);

        setnomeProduto = (TextView) findViewById(R.id.setnomeProduto);
        setprecoProduto = (TextView) findViewById(R.id.setprecoProduto);
        setdescProduto = (TextView) findViewById(R.id.setdescProduto);
        setimagemProduto = (ImageView) findViewById(R.id.setimagemProduto);


        Intent intent = getIntent();
        int IDproduto = intent.getIntExtra("IDproduto", 0);
        String nomeproduto = intent.getExtras().getString("nomeproduto");
        String precoproduto = intent.getExtras().getString("precoproduto");
        String descproduto = intent.getExtras().getString("descproduto");

//        Toast.makeText(getApplicationContext(), "" + IDproduto, Toast.LENGTH_LONG).show();


        setnomeProduto.setText(nomeproduto);
        setprecoProduto.setText(precoproduto);
        setdescProduto.setText(descproduto);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            byte[] dadosdaimagem = bundle.getByteArray("imagemproduto");
            imagem = BitmapFactory.decodeByteArray(dadosdaimagem, 0, dadosdaimagem.length);

            setimagemProduto.setImageBitmap(imagem);


        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        final byte[] dadosdaimagem = baos.toByteArray();

        setimagemProduto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view1) {
                Intent i = new Intent(Produto_Activity.this, VisualActivity.class);
                i.putExtra("fotoescolhida", dadosdaimagem);
                startActivity(i);
            }
        });


    }

    public void addcarrinho(final View view) {

        Retrofit retrofitt = APIClient.getClient();

        PedidoResource pedidoResource = retrofitt.create(PedidoResource.class);

        Call<List<Pedido>> gett = pedidoResource.get();

        gett.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {

                Intent intent = getIntent();
                int IDproduto = intent.getIntExtra("IDproduto", 0);
                String precoproduto = intent.getExtras().getString("precoproduto");

                Pedido tp = new Pedido();


                List<Pedido> pedidos = response.body();

                for (Pedido tipoprodutot : pedidos) {

                    tp = tipoprodutot;
                }

//                Toast.makeText(getApplicationContext(), " " + tp.getNum_pedido() + " "+IDproduto, Toast.LENGTH_SHORT).show();

                if (tp != null) {

                    String QuantidadeItem = String.valueOf(setQuantidadeItem.getSelectedItem());

                    int numerodopedido = tp.getNum_pedido();
                    int numerodoproduto = IDproduto;
                    int quantidade = Integer.parseInt(QuantidadeItem);

                    String str = precoproduto;
                    int i = Integer.parseInt(str);


                    Carrinho carrinho = new Carrinho(numerodopedido, numerodoproduto, quantidade, String.valueOf(i));
                    Retrofit retrofit = APIClient.getClient();
                    CarrinhoResource carrinhoResource = retrofit.create(CarrinhoResource.class);
                    Call<Carrinho> calll = carrinhoResource.post(carrinho);

                    calll.enqueue(new Callback<Carrinho>() {
                        @Override
                        public void onResponse(Call<Carrinho> calll, Response<Carrinho> response) {


                            Snackbar snackbar = Snackbar
                                    .make(view, "Adicionado ao carrinho", Snackbar.LENGTH_LONG);
                            snackbar.show();


                        }

                        @Override
                        public void onFailure(Call<Carrinho> calll, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });


                }


            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }


        });


    }

    public void Carrinho(View view) {


        Retrofit retrofitt = APIClient.getClient();

        PedidoResource pedidoResource = retrofitt.create(PedidoResource.class);

        Call<List<Pedido>> gett = pedidoResource.get();

        gett.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {


                Pedido tp = new Pedido();


                List<Pedido> pedidos = response.body();

                for (Pedido tipoprodutot : pedidos) {

                    tp = tipoprodutot;
                }


                if (tp != null) {
                    Intent intent = getIntent();
                    int IDproduto = intent.getIntExtra("IDproduto", 0);


                    Intent intentt = new Intent(getApplicationContext(), CarrinhoActivity.class);
                    intentt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    // passing data to the book activity
                    intentt.putExtra("IDproduto", IDproduto);
                    intentt.putExtra("numeropedido", tp.getNum_pedido());

                    // start the activity
                    getApplicationContext().startActivity(intentt);


                }


            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }


        });


    }
}
