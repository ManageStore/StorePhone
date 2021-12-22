package com.example.managestorephone;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;


public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.ViewHolder>
{
    Context context;
    List<Customer> customers;

    public CustomerListAdapter(List<Customer> getCustomerAdapter,Context context) {
        super();
        this.context = context;
        this.customers = getCustomerAdapter;
    }
    @NonNull
    @Override
    public CustomerListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerListAdapter.ViewHolder holder, int position) {
        Customer customer = customers.get(position);
        holder.avtata.setText(customer.getHoTen().charAt(0)+"");
        holder.nameCustomer.setText(customer.getHoTen());
        holder.numberMobile.setText(customer.getSDT());
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameCustomer,numberMobile,avtata;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avtata = (TextView) itemView.findViewById(R.id.avataCustomer);

            nameCustomer = (TextView) itemView.findViewById(R.id.nameCustomer);
            numberMobile = (TextView) itemView.findViewById(R.id.numberMobile);

        }
    }

    public void filterList(List<Customer> filteredList) {
        customers = filteredList;
        notifyDataSetChanged();
    }
}

