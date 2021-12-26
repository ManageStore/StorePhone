package com.example.managestorephone.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.managestorephone.History.HistoryOrderProduct;
import com.example.managestorephone.History.HistoryOrderProductAdapter;
import com.example.managestorephone.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DoashAdapter extends RecyclerView.Adapter<DoashAdapter.ViewHolder>{
    Context context;
    List<Doash> doashes;

    public String dongia_format;

    public DoashAdapter(List<Doash> getDoashAdapter,Context context) {
        super();
        this.context = context;
        this.doashes = getDoashAdapter;
    }
    @NonNull
    @Override
    public DoashAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_order_product_item,parent,false);
        DoashAdapter.ViewHolder viewHolder = new DoashAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Doash doash = doashes.get(position);
        Glide.with(context)
                .load(doashes.get(position)
                        .getHinhAnh())
                .into(holder.imaHinhAnh);
        holder.TenSP.setText(doash.getTenSP());
        holder.MaSP.setText("SP000".concat(String.valueOf(doash.getMaSP())));
        holder.SoLuong.setText("x".concat(String.valueOf(doash.getSoLuongBan())));
        dongia_format = NumberFormat.getNumberInstance(Locale.US).format(doash.getDoanhThu());
        holder.GiaTong.setText(dongia_format + "đ");
    }



    @Override
    public int getItemCount() {
        return doashes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView GiaTong,TenSP,MaSP,SoLuong;
        public  ImageView imaHinhAnh;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            GiaTong = (TextView) itemView.findViewById(R.id.giatong);
            TenSP = (TextView) itemView.findViewById(R.id.TenSP);
            SoLuong = (TextView) itemView.findViewById(R.id.SoLuong);
            MaSP = (TextView) itemView.findViewById(R.id.MaSP);
            imaHinhAnh = (ImageView) itemView.findViewById(R.id.imageviewAnh);
        }
    }

}
