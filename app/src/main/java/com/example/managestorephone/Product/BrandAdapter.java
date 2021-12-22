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
import com.example.managestorephone.ui.goods.ViewDetailBrand;
import com.example.managestorephone.ui.goods.ViewDetailProduct;

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
        holder.tvmahang.setText(String.valueOf(brandList.get(position).getMahang()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewDetailBrand.class);
                intent.putExtra("detailBrand",brandList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgHang;
        TextView name,tvmahang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHang = (ImageView) itemView.findViewById(R.id.imgHang);
            name = (TextView) itemView.findViewById(R.id.name);
            tvmahang = (TextView) itemView.findViewById(R.id.tvmahang);
        }
    }
}
