package com.example.managestorephone.ui.sell;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.managestorephone.Customer;
import com.example.managestorephone.CustomerListAdapter;
import com.example.managestorephone.MySingleton;
import com.example.managestorephone.Order.Order;
import com.example.managestorephone.Order.OrderAdapter;
import com.example.managestorephone.Product.product;
import com.example.managestorephone.R;
import com.example.managestorephone.databinding.FragmentSellBinding;
import com.example.managestorephone.utils.Utils;
import com.google.gson.Gson;

import org.jetbrains.annotations.TestOnly;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SellFragment extends Fragment{

    public static FragmentSellBinding binding;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView recyclerView;
    OrderAdapter orderAdapter;
    public static int tongDon=0;
    public static String tongDon_format;

    product Product=null;
    Customer customer=null;

    String url=Utils.BASE_URL.concat("android_TH/order/order.php");


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSellBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);


        Bundle bundleCustomer=this.getArguments();
        if(bundleCustomer !=null)
        {
            final  Object object =bundleCustomer.getSerializable("key_customer");
            if(object instanceof Customer){

                customer = (Customer) object;
                if(customer!=null)
                {
                    Utils.khachhang = customer;
                    binding.nameCustomerOrder.setText(Utils.khachhang.getHoTen());
                }

            }
        }
        Bundle bundle =this.getArguments();
        if(bundle !=null)
        {
                final  Object object =bundle.getSerializable("key");
                if(object instanceof product){
                    Product = (product) object;
                    if(Utils.manggiohang.size() > 0) {
                        boolean flag = false;
                        for (int i = 0; i < Utils.manggiohang.size(); i++) {
                            if (Utils.manggiohang.get(i).getMaSP() == Product.getMaSP()) {
                                Utils.manggiohang.get(i).setSoluong(1 + Utils.manggiohang.get(i).getSoluong());
                                Utils.manggiohang.get(i).setGiaSP(Product.getGiaban());
                                Utils.manggiohang.get(i).setTong(Utils.manggiohang.get(i).getSoluong() * Product.getGiaban());
                                flag = true;
                            }
                        }
                        if (flag == false) {
                            Order giohang = new Order();
                            giohang.setMaSP(Product.getMaSP());
                            giohang.setSoluong(1);
                            giohang.setTenSP(Product.getTenSP());
                            giohang.setGiaSP(Product.getGiaban());
                            giohang.setTong(Product.getGiaban());
                            Utils.manggiohang.add(giohang);
                        }
                    }
                    else{
                        Order giohang = new Order();
                        giohang.setMaSP(Product.getMaSP());
                        giohang.setSoluong(1);
                        giohang.setTenSP(Product.getTenSP());
                        giohang.setGiaSP(Product.getGiaban());
                        giohang.setTong(Product.getGiaban());
                        Utils.manggiohang.add(giohang);
                    }
                }




            }

            orderAdapter = new OrderAdapter(Utils.manggiohang,getActivity());
            recyclerView.setAdapter(orderAdapter);
            tongtien();


        binding.productlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListSearchProductFragment listSearchProductFragment= new ListSearchProductFragment();

                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main,listSearchProductFragment);

                fragmentTransaction.commit();

            }
        });

        binding.customerlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListSearchCustomerFragment listSearchCustomerFragment= new ListSearchCustomerFragment();

                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main,listSearchCustomerFragment);
                fragmentTransaction.commit();

            }
        });

        binding.thanhtoanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test",new Gson().toJson(Utils.manggiohang));
                String Str_maKH=String.valueOf(Utils.khachhang.getMaKH());
                String Str_DonGia=String.valueOf(tongDon);
                String Str_CTDH=new Gson().toJson(Utils.manggiohang);

                NewOrder(Str_maKH,Str_DonGia,Str_CTDH);

            }
        });




        return root;
    }

    public static void tongtien(){
        tongDon=0;
        if(Utils.manggiohang.size()>0)
        {
            for (int i = 0; i < Utils.manggiohang.size(); i++) {
               tongDon=tongDon + Utils.manggiohang.get(i).getTong();
            }

        }
        else
        {
            tongDon=0;
        }
        tongDon_format= NumberFormat.getNumberInstance(Locale.US).format(tongDon);

        binding.tongorder01.setText(tongDon_format+"đ");


    }


    private void NewOrder(final String MaKH,final String DonGia,final String ChiTietDonHang){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("You are successfully")){
                    Toast.makeText(getActivity(), "Thanh Toán thành công!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                    Utils.manggiohang.clear();
                    Utils.khachhang=null;
                    SellFragment sellFragment= new SellFragment();

                    FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment_content_main,sellFragment);
                    fragmentTransaction.commit();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("MaKH",MaKH);
                param.put("DonGia",DonGia);
                param.put("ChiTietDonHang",ChiTietDonHang);
                Log.d("data", ChiTietDonHang);
                return param;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(3000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(getActivity()).addToRequestQueue(request);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}