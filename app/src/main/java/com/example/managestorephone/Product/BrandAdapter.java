package com.example.managestorephone.Product;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.managestorephone.R;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ViewHolder> {

    Context context;
    List<Brand> brandList;

    public BrandAdapter(Context context, List<Brand> brandList) {
        this.context = context;
        this.brandList = brandList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Glide.with(context)
                .load(brandList.get(position)
                        .getHinhAnhhang())
                .into(holder.imgHang);
        holder.name.setText(brandList.get(position).getTenhang());

        holder.imgHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, ViewProductActivity.class);
            }
        });
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgHang;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHang = (ImageView) itemView.findViewById(R.id.imgHang);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
