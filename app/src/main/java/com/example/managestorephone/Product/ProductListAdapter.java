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
import com.example.managestorephone.ui.goods.ViewDetailProduct;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder>{

    Context context;
    List<product> products;
    String gia_format,gianhap_format;

    public ProductListAdapter(List<product> getProductAdapter, Context context) {
        super();
        this.context = context;
        this.products = getProductAdapter;
    }
    @NonNull
    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ViewHolder holder, int position) {

        Glide.with(context)
                .load(products.get(position)
                        .getHinhAnh())
                .into(holder.imaHinhAnh);


        holder.masp.setText(String.valueOf(products.get(position).getMaSP()));

        holder.ten.setText(products.get(position).getTenSP());
        holder.soluong.setText(String.valueOf(products.get(position).getSoluong()));

        gia_format= NumberFormat.getNumberInstance(Locale.US).format(products.get(position).getGiaban());
        holder.giaban.setText(gia_format+"đ");

        gianhap_format= NumberFormat.getNumberInstance(Locale.US).format(products.get(position).getGianhap());
        holder.gianhap.setText(gianhap_format+"đ");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewDetailProduct.class);
                intent.putExtra("detail",products.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ten,masp,soluong,giaban,gianhap;
        public ImageView imaHinhAnh;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ten = (TextView) itemView.findViewById(R.id.name);
            masp = (TextView) itemView.findViewById(R.id.masp);
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
