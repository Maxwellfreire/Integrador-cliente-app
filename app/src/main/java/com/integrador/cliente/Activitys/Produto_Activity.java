package com.integrador.cliente.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.integrador.cliente.R;

public class Produto_Activity extends AppCompatActivity {

    private TextView setnomeProduto, setdescProduto, setprecoProduto;
    private ImageView setimagemProduto;
    private Bitmap imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        setnomeProduto = (TextView) findViewById(R.id.setnomeProduto);
        setprecoProduto = (TextView) findViewById(R.id.setprecoProduto);
        setdescProduto = (TextView) findViewById(R.id.setdescProduto);
        setimagemProduto = (ImageView) findViewById(R.id.setimagemProduto);


        // Recieve data
        Intent intent = getIntent();
        String nomeproduto = intent.getExtras().getString("nomeproduto");
        int precoproduto = intent.getIntExtra("precoproduto", 0);
        String descproduto = intent.getExtras().getString("descproduto");




        // Setting values

        setnomeProduto.setText(nomeproduto);
        setprecoProduto.setText(String.valueOf(precoproduto));
        setdescProduto.setText(descproduto);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            byte[] dadosdaimagem = bundle.getByteArray("imagemproduto");
            imagem = BitmapFactory.decodeByteArray(dadosdaimagem, 0, dadosdaimagem.length);

            setimagemProduto.setImageBitmap(imagem);


        }


    }
}
