package com.example.managestorephone.History;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.managestorephone.R;
import com.example.managestorephone.ui.goods.ViewDetailProduct;
import com.example.managestorephone.ui.history.ViewDetailsHistoryOrder;


import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HistoryOrderProductAdapter extends RecyclerView.Adapter<HistoryOrderProductAdapter.ViewHolder>{

    Context context;
    List<HistoryOrderProduct> historyOrderProducts;

    public String dongia_format;

    public HistoryOrderProductAdapter(List<HistoryOrderProduct> getHistoryOrderProductAdapter,Context context) {
        super();
        this.context = context;
        this.historyOrderProducts = getHistoryOrderProductAdapter;
    }
    @NonNull
    @Override
    public HistoryOrderProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_order_product_item,parent,false);
        HistoryOrderProductAdapter.ViewHolder viewHolder = new HistoryOrderProductAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryOrderProduct historyOrderProduct = historyOrderProducts.get(position);
        holder.TenSP.setText(historyOrderProduct.getTenSP());
        holder.MaSP.setText("SP000".concat(String.valueOf(historyOrderProduct.getMaSP())));
        holder.SoLuong.setText("x".concat(String.valueOf(historyOrderProduct.getSoLuong())));

        dongia_format = NumberFormat.getNumberInstance(Locale.US).format(historyOrderProduct.getGiaTong());
        holder.GiaTong.setText(dongia_format + "đ");


    }
    @Override
    public int getItemCount() {
        return historyOrderProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView GiaTong,TenSP,MaSP,SoLuong;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            GiaTong = (TextView) itemView.findViewById(R.id.giatong);
            TenSP = (TextView) itemView.findViewById(R.id.TenSP);
            SoLuong = (TextView) itemView.findViewById(R.id.SoLuong);
            MaSP = (TextView) itemView.findViewById(R.id.MaSP);


        }
    }

};




