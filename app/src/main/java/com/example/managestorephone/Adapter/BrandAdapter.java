package com.example.managestorephone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.managestorephone.R;
import com.example.managestorephone.models.Brand;
import com.example.managestorephone.models.product;

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
    public BrandAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BrandAdapter.ViewHolder holder, int position) {


        Glide.with(context)
                .load(brandList.get(position)
                        .getHinhAnhhang())
                .into(holder.imgHang);
        holder.name.setText(brandList.get(position).getTenhang());
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
