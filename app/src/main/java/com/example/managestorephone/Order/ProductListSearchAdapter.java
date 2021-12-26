package com.example.managestorephone.Order;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.managestorephone.MainActivity;
import com.example.managestorephone.Product.product;
import com.example.managestorephone.R;

import com.example.managestorephone.ui.sell.SellFragment;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductListSearchAdapter extends RecyclerView.Adapter<ProductListSearchAdapter.ViewHolder> {

    Context context;
    List<product> products;
    String gia_format,gianhap_format;



    public ProductListSearchAdapter(List<product> getProductAdapter, Context context){
        super();
        this.context = context;
        this.products = getProductAdapter;



    }
    @NonNull
    @Override
    public ProductListSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListSearchAdapter.ViewHolder holder, int position) {

        Glide.with(context)
                .load(products.get(position)
                        .getHinhAnh())
                .into(holder.imaHinhAnh);

        holder.mahang.setText("SP000"+products.get(position).getMaSP());
        holder.ten.setText(products.get(position).getTenSP());
        holder.soluong.setText(String.valueOf(products.get(position).getSoluong()));

        gia_format= NumberFormat.getNumberInstance(Locale.US).format(products.get(position).getGiaban());
        holder.giaban.setText(gia_format+"đ");

        gianhap_format= NumberFormat.getNumberInstance(Locale.US).format(products.get(position).getGianhap());
        holder.gianhap.setText(gianhap_format+"đ");



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product data=products.get(position);

                Bundle bundle =new Bundle();
                bundle.putSerializable("key",  data);


                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                SellFragment myFragment = new SellFragment();
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_content_main, myFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();

            }


        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ten,mahang,soluong,giaban,gianhap;
        public ImageView imaHinhAnh;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ten = (TextView) itemView.findViewById(R.id.name);
            mahang = (TextView) itemView.findViewById(R.id.masp);
            soluong = (TextView) itemView.findViewById(R.id.soluong);
            giaban = (TextView) itemView.findViewById(R.id.giaban);
            gianhap = (TextView) itemView.findViewById(R.id.gianhap);
            imaHinhAnh = (ImageView) itemView.findViewById(R.id.imageviewAnh);
        }
    }
    public void filterList(List<product> filteredList) {
        products = filteredList;
        notifyDataSetChanged();
    }
}
