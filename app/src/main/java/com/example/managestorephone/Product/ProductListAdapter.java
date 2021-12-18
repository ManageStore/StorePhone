package com.example.managestorephone.Product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.managestorephone.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder>{

    Context context;
    List<product> products;
    ImageLoader imageLoader;
    String gia_format;

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
        product Product = products.get(position);

        imageLoader = ImageAdapter.getInstance(context).getImageLoader();

        imageLoader.get(Product.getHinhAnh(),
                ImageLoader.getImageListener(
                        holder.VollyImageView,
                        R.mipmap.ic_launcher,
                        android.R.drawable.ic_dialog_alert
                )
        );
        holder.VollyImageView.setImageUrl(Product.getHinhAnh(),imageLoader);
        holder.mahang.setText(Product.getMaSP());
        holder.ten.setText(Product.getTenSP());
        holder.soluong.setText(String.valueOf(Product.getSoluong()));
        holder.giaban.setText(String.valueOf(Product.getGiaban()));

        gia_format= NumberFormat.getNumberInstance(Locale.US).format(Product.getGiaban());
        holder.giaban.setText(gia_format+"đ");
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ten,mahang,soluong,giaban;
        public NetworkImageView VollyImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ten = (TextView) itemView.findViewById(R.id.name);
            mahang = (TextView) itemView.findViewById(R.id.masp);
            soluong = (TextView) itemView.findViewById(R.id.soluong);
            giaban = (TextView) itemView.findViewById(R.id.giaban);
            VollyImageView = (NetworkImageView) itemView.findViewById(R.id.imageviewAnh);
        }
    }
    public void filterList(List<product> filteredList) {
        products = filteredList;
        notifyDataSetChanged();
    }
}
