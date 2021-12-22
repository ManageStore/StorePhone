package com.example.managestorephone.Order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.managestorephone.R;
import com.example.managestorephone.ui.sell.SellFragment;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder >{

    Context context;
    List<Order> orders;
    public int soluong ;
    public int tong;
    public int giaSP;
    public String tong_format;
    public String gia_format;

    public OrderAdapter(List<Order> getOrderAdapter,Context context) {
        super();
        this.context = context;
        this.orders = getOrderAdapter;
    }
    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);
        OrderAdapter.ViewHolder viewHolder = new OrderAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.TenSP.setText(order.getTenSP());
        holder.SoLuong.setText(String.valueOf(order.getSoluong()));

        holder.tong.setText(String.valueOf(order.getTong()));

        gia_format= NumberFormat.getNumberInstance(Locale.US).format(order.getGiaSP());
        holder.giaBan.setText(gia_format+"đ");

        tong_format= NumberFormat.getNumberInstance(Locale.US).format(order.getTong());
        holder.tong.setText(tong_format+"đ");



        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tong= order.getTong();
                soluong=order.getSoluong();
                giaSP=order.getGiaSP();
                soluong +=1;
                tong=giaSP*soluong;
                order.setSoluong(soluong);
                order.setTong(tong);

                holder.SoLuong.setText(String.valueOf(order.getSoluong()));

                gia_format= NumberFormat.getNumberInstance(Locale.US).format(order.getGiaSP());
                holder.giaBan.setText(gia_format+"đ");

                tong_format= NumberFormat.getNumberInstance(Locale.US).format(order.getTong());
                holder.tong.setText(tong_format+"đ");
                SellFragment.tongtien();

//                holder.giaBan.setText(String.valueOf(order.getGiaSP()));
//                holder.tong.setText(String.valueOf(order.getTong()));


            }
        });


        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soluong=order.getSoluong();
                if(soluong<=1)
                {
                   orders.remove(position);
                }
                else{
                    tong= order.getTong();
                    soluong=order.getSoluong();
                    giaSP=order.getGiaSP();
                    soluong -=1;

                }
                tong=giaSP*soluong;
                order.setSoluong(soluong);
                order.setTong(tong);
                holder.SoLuong.setText(String.valueOf(order.getSoluong()));

                gia_format= NumberFormat.getNumberInstance(Locale.US).format(order.getGiaSP());
                holder.giaBan.setText(gia_format+"đ");

                tong_format= NumberFormat.getNumberInstance(Locale.US).format(order.getTong());
                holder.tong.setText(tong_format+"đ");
                SellFragment.tongtien();

//                holder.giaBan.setText(String.valueOf(order.getGiaSP()));
//                holder.tong.setText(String.valueOf(order.getTong()));





            }


        });

    };



    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView TenSP,giaBan,SoLuong,tong;
        public ImageView plus,minus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            TenSP = (TextView) itemView.findViewById(R.id.TenSP);
            SoLuong = (TextView) itemView.findViewById(R.id.SoLuong);
            giaBan = (TextView) itemView.findViewById(R.id.giaBan);
            tong = (TextView) itemView.findViewById(R.id.giatong);
            plus = itemView.findViewById(R.id.plusBtn);
             minus = itemView.findViewById(R.id.minusBtn);

        }
    }
}
