package com.integrador.cliente.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.integrador.cliente.Activitys.Produto_Activity;
import com.integrador.cliente.R;
import com.integrador.cliente.model.Produto;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Produto> mData;


    public RecyclerViewAdapter(Context mContext, List<Produto> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardveiw_item_produto, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        String stringImagem = mData.get(position).getImagem();


        byte[] decodeSring = Base64.decode(stringImagem, Base64.DEFAULT);
        final Bitmap decoded = BitmapFactory.decodeByteArray(decodeSring, 0, decodeSring.length);

        //Primeiros requisitos que vão carregar ao abrir o aplicativo
        holder.nomeProduto.setText(mData.get(position).getNameProduto());
        holder.precoProduto.setText(mData.get(position).getPreco());
        holder.imagemProduto.setImageBitmap(decoded);
        //........................

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        decoded.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        final byte[] dadosdaimagem = baos.toByteArray();


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, Produto_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // passing data to the book activity
                intent.putExtra("IDproduto", mData.get(position).getIdProduto());
                intent.putExtra("nomeproduto", mData.get(position).getNameProduto());
                intent.putExtra("precoproduto", mData.get(position).getPreco());
                intent.putExtra("descproduto", mData.get(position).getDesc());
                intent.putExtra("imagemproduto", dadosdaimagem);
                // start the activity
                mContext.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nomeProduto, precoProduto;
        ImageView imagemProduto;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            nomeProduto = (TextView) itemView.findViewById(R.id.nomeProduto);
            precoProduto = (TextView) itemView.findViewById(R.id.precoProduto);
            imagemProduto = (ImageView) itemView.findViewById(R.id.imagemProduto);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);


        }
    }


}
