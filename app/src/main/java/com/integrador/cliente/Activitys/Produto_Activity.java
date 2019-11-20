package com.integrador.cliente.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.integrador.cliente.R;

import java.io.ByteArrayOutputStream;

public class Produto_Activity extends AppCompatActivity {

    private TextView setnomeProduto, setdescProduto, setprecoProduto;
    private ImageView setimagemProduto;
    private Bitmap imagem;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        setnomeProduto = (TextView) findViewById(R.id.setnomeProduto);
        setprecoProduto = (TextView) findViewById(R.id.setprecoProduto);
        setdescProduto = (TextView) findViewById(R.id.setdescProduto);
        setimagemProduto = (ImageView) findViewById(R.id.setimagemProduto);



        Intent intent = getIntent();
        String nomeproduto = intent.getExtras().getString("nomeproduto");
        String precoproduto = intent.getExtras().getString("precoproduto");
//        int precoproduto = intent.getIntExtra("precoproduto", 0);
        String descproduto = intent.getExtras().getString("descproduto");






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
}
