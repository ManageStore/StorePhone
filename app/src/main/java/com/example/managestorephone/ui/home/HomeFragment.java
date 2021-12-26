package com.example.managestorephone.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.managestorephone.Customer;
import com.example.managestorephone.CustomerListAdapter;
import com.example.managestorephone.History.HistoryOrderProduct;
import com.example.managestorephone.History.HistoryOrderProductAdapter;
import com.example.managestorephone.Home.Doash;
import com.example.managestorephone.Home.DoashAdapter;
import com.example.managestorephone.Product.product;
import com.example.managestorephone.R;
import com.example.managestorephone.databinding.FragmentHomeBinding;
import com.example.managestorephone.ui.history.ViewDetailsHistoryOrder;
import com.example.managestorephone.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    JsonArrayRequest request,requestcustomer,requestMoney,requestQuatity,requestOrder;
    RequestQueue requestQueue,requestQueueCustomer,requestQueueMoney,requestQueueQuatity,requestQueueOrder;

    List<Doash> doashList;
    RecyclerView recyclerView;
    DoashAdapter doashAdapter;


    View view;
    int RecyclerViewPosition;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter recyclerViewAdapter;


    int SoLuongKH;
    int SoLuongSP;
    int SoLuongDH;


    String urlProduct= Utils.BASE_URL+"android_TH/product/product.php";
    String urlCustomer= Utils.BASE_URL+"android_TH/customer/customer.php";
    String urlMoney= Utils.BASE_URL+"android_TH/home/productmoney.php";
    String urlQuatity= Utils.BASE_URL+"android_TH/home/productquatity.php";
    String urlOrder= Utils.BASE_URL+"android_TH/order/ViewOrder.php";




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        doashList =new ArrayList<>();



        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        call_json_customer();
        call_json_product();
        call_json_order();
        call_json_product_sell();

        binding.SoLuongKH.setText(String.valueOf(SoLuongKH));
        binding.SoLuongSP.setText(String.valueOf(SoLuongSP));
        binding.DoanhThu.setText(String.valueOf(SoLuongDH));

        binding.btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call_json_money();
                binding.btnDoanhThu.setTextColor(Color.parseColor("#2060CD"));
                binding.btnSoLuong.setTextColor(Color.BLACK);

            }
        });
        binding.btnSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call_json_product_sell();
                binding.btnSoLuong.setTextColor(Color.parseColor("#2060CD"));
                binding.btnDoanhThu.setTextColor(Color.BLACK);
            }
        });

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void call_json_money() {
        doashList =new ArrayList<>();
        requestMoney = new JsonArrayRequest(urlMoney, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("data", response.toString());
                for (int i=0;i<response.length();i++){
                    Doash getDoash =new Doash();

                    JSONObject jsonObject = null;

                    try {
                        jsonObject = response.getJSONObject(i);
                        getDoash.setDoanhThu(jsonObject.getInt("DoanhThu"));
                        getDoash.setSoLuongBan(jsonObject.getInt("SoLuongBan"));
                        getDoash.setMaSP(jsonObject.getInt("MaSP"));
                        getDoash.setTenSP(jsonObject.getString("TenSP"));
                        getDoash.setHinhAnh(Utils.BASE_URL.concat(jsonObject.getString("HinhAnh")));



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    doashList.add(getDoash);


                }
                doashAdapter = new DoashAdapter(doashList,getActivity());
                recyclerView.setAdapter(doashAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueueMoney = Volley.newRequestQueue(getActivity());
        requestQueueMoney.add(requestMoney);
    }
    private void call_json_product_sell() {
        doashList =new ArrayList<>();
        requestMoney = new JsonArrayRequest(urlQuatity, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("data", response.toString());
                for (int i=0;i<response.length();i++){
                    Doash getDoash =new Doash();

                    JSONObject jsonObject = null;

                    try {
                        jsonObject = response.getJSONObject(i);
                        getDoash.setDoanhThu(jsonObject.getInt("DoanhThu"));
                        getDoash.setSoLuongBan(jsonObject.getInt("SoLuongBan"));
                        getDoash.setMaSP(jsonObject.getInt("MaSP"));
                        getDoash.setTenSP(jsonObject.getString("TenSP"));
                        getDoash.setHinhAnh(Utils.BASE_URL.concat(jsonObject.getString("HinhAnh")));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    doashList.add(getDoash);


                }
                doashAdapter = new DoashAdapter(doashList,getActivity());
                recyclerView.setAdapter(doashAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueueMoney = Volley.newRequestQueue(getActivity());
        requestQueueMoney.add(requestMoney);
    }

    private void call_json_customer() {

        requestcustomer = new JsonArrayRequest(urlCustomer, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                SoLuongKH=response.length();
                binding.SoLuongKH.setText(String.valueOf(SoLuongKH));


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueueCustomer = Volley.newRequestQueue(getActivity());
        requestQueueCustomer.add(requestcustomer);
    }

    private void call_json_order() {

        requestOrder = new JsonArrayRequest(urlOrder, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                SoLuongDH= 0;
                binding.SoLuongDH.setText(String.valueOf(response.length()));
                for (int i=0;i<response.length();i++){


                    JSONObject jsonObject = null;

                    try {
                        jsonObject = response.getJSONObject(i);
                        SoLuongDH= SoLuongDH+ jsonObject.getInt("DonGia");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("sl", String.valueOf(SoLuongDH));


                }
                String gia_format= NumberFormat.getNumberInstance(Locale.US).format(SoLuongDH);

                binding.DoanhThu.setText(gia_format+"đ");





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueueOrder= Volley.newRequestQueue(getActivity());
        requestQueueOrder.add(requestOrder);
    }

    private void call_json_product() {
        request = new JsonArrayRequest(urlProduct, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

             SoLuongSP=response.length();

                binding.SoLuongSP.setText(String.valueOf(SoLuongSP));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }
}