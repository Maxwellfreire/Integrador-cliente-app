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

    private Context mContext ;
    private List<Produto> mData ;


    public RecyclerViewAdapter(Context mContext, List<Produto> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardveiw_item_book,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        String stringImagem = mData.get(position).getImagem();


        byte[] decodeSring = Base64.decode(stringImagem, Base64.DEFAULT);
        final Bitmap decoded = BitmapFactory.decodeByteArray(decodeSring, 0, decodeSring.length);

        //Primeiros requisitos que vão carregar ao abrir o aplicativo
        holder.tv_book_title.setText(mData.get(position).getNameProduto());
        holder.img_book_thumbnail.setImageBitmap(decoded);
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
                intent.putExtra("Title",mData.get(position).getNameProduto());
                intent.putExtra("Description",mData.get(position).getDesc());
                intent.putExtra("Thumbnail", dadosdaimagem);
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

        TextView tv_book_title;
        ImageView img_book_thumbnail;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_book_title = (TextView) itemView.findViewById(R.id.book_title_id) ;
            img_book_thumbnail = (ImageView) itemView.findViewById(R.id.book_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);


        }
    }


}
