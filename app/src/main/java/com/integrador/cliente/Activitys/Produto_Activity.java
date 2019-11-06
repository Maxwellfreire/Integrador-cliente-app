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

    private TextView tvtitle, tvdescription, tvcategory;
    private ImageView img;
    private Bitmap imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        tvtitle = (TextView) findViewById(R.id.txttitle);
        tvdescription = (TextView) findViewById(R.id.txtDesc);
        tvcategory = (TextView) findViewById(R.id.txtCat);
        img = (ImageView) findViewById(R.id.bookthumbnail);


        // Recieve data
        Intent intent = getIntent();
        String Title = intent.getExtras().getString("Title");
        String Description = intent.getExtras().getString("Description");


        // Setting values

        tvtitle.setText(Title);
        tvdescription.setText(Description);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            byte[] dadosdaimagem = bundle.getByteArray("Thumbnail");
            imagem = BitmapFactory.decodeByteArray(dadosdaimagem, 0, dadosdaimagem.length);

            img.setImageBitmap(imagem);


        }


    }
}
