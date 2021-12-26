package com.example.managestorephone.History;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.managestorephone.Customer;
import com.example.managestorephone.Order.Order;
import com.example.managestorephone.Order.OrderAdapter;
import com.example.managestorephone.R;
import com.example.managestorephone.ui.history.ViewDetailsHistoryOrder;
import com.example.managestorephone.ui.sell.SellFragment;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HistoryOrderAdapter extends RecyclerView.Adapter<HistoryOrderAdapter.ViewHolder>{

    Context context;
    List<HistoryOrder> historyOrders;

    public String dongia_format;

    public HistoryOrderAdapter(List<HistoryOrder> getHistoryOrderAdapter,Context context) {
        super();
        this.context = context;
        this.historyOrders = getHistoryOrderAdapter;
    }
    @NonNull
    @Override
    public HistoryOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item,parent,false);
        HistoryOrderAdapter.ViewHolder viewHolder = new HistoryOrderAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryOrder historyOrder = historyOrders.get(position);
        holder.HoTen.setText(". ".concat(historyOrder.getHoTen())+" - "+historyOrder.getSDT());
        holder.MaHD.setText("HD000".concat(String.valueOf(historyOrder.getMaHD())));
        holder.NgayBan.setText(String.valueOf(historyOrder.getNgayBan()));
        dongia_format = NumberFormat.getNumberInstance(Locale.US).format(historyOrder.getDonGia());
        holder.DonGia.setText(dongia_format + "đ");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewDetailsHistoryOrder.class);
                intent.putExtra("detailOrder", historyOrders.get(position));
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return historyOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView DonGia,MaHD,NgayBan,HoTen;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            DonGia = (TextView) itemView.findViewById(R.id.DonGia);
            MaHD = (TextView) itemView.findViewById(R.id.MaHD);
            NgayBan = (TextView) itemView.findViewById(R.id.NgayBan);
            HoTen = (TextView) itemView.findViewById(R.id.HoTen);


        }
    }
    public void filterList(List<HistoryOrder> filteredList) {
        historyOrders = filteredList;
        notifyDataSetChanged();
    }

};




