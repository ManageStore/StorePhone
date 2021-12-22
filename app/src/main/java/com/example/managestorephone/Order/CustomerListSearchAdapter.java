package com.example.managestorephone.Order;

import android.content.Context;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.example.managestorephone.Customer;
import com.example.managestorephone.Product.product;
import com.example.managestorephone.R;
import com.example.managestorephone.ui.sell.SellFragment;

import java.util.ArrayList;
import java.util.List;


public class CustomerListSearchAdapter extends RecyclerView.Adapter<CustomerListSearchAdapter.ViewHolder>
{
    Context context;
    List<Customer> customers;

    public CustomerListSearchAdapter(List<Customer> getCustomerListSearchAdapter, Context context) {
        super();
        this.context = context;
        this.customers = getCustomerListSearchAdapter;
    }
    @NonNull
    @Override
    public CustomerListSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerListSearchAdapter.ViewHolder holder, int position) {
        Customer customer = customers.get(position);
        holder.avtata.setText(customer.getHoTen().charAt(0)+"");
        holder.nameCustomer.setText(customer.getHoTen());
        holder.numberMobile.setText(customer.getSDT());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundleCustomer =new Bundle();
                bundleCustomer.putSerializable("key_customer", customers.get(position));
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                SellFragment myFragment = new SellFragment();
                myFragment.setArguments(bundleCustomer);
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_content_main, myFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();

            }


        });
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

