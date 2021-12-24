package com.example.managestorephone.ui.customer;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.managestorephone.Customer;
import com.example.managestorephone.CustomerListAdapter;
import com.example.managestorephone.R;
import com.example.managestorephone.databinding.FragmentCustomerBinding;
import com.example.managestorephone.ui.goods.ProductsFragment;
import com.example.managestorephone.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CustomerFragment extends Fragment {

    private FragmentCustomerBinding binding;

    String url = Utils.BASE_URL+"android_TH/customer/Customer.php";

    List<Customer> customerList;
    RecyclerView recyclerView;
    CustomerListAdapter customerListAdapter;
    EditText searchEdit;

    JsonArrayRequest request;
    RequestQueue requestQueue;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter recyclerViewAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCustomerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        customerList = new ArrayList<>();
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        call_json();

        binding.themCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),AddCustomerActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
            }
        });
        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                CustomerFragment customerFragment= new CustomerFragment();
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main,customerFragment);
                fragmentTransaction.commit();
            }
        });
        searchEdit =(EditText) root.findViewById(R.id.id_search_customer);

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });
        return root;
    }

    private void filter(String text) {
        List<Customer> filteredList = new ArrayList<>();

        for (Customer item : customerList) {
            if (item.getHoTen().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);

            }
        }

//        recyclerViewAdapter = new CustomerListAdapter(customerList,getActivity());
        customerListAdapter.filterList(filteredList);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void call_json() {

        request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i=0;i<response.length();i++){
                    Customer geCustomerAdapter = new Customer();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);
                        geCustomerAdapter.setHoTen(jsonObject.getString("HoTen"));
                        geCustomerAdapter.setSDT(jsonObject.getString("SDT"));
                        geCustomerAdapter.setMaKH(jsonObject.getInt("MaKH"));
                        geCustomerAdapter.setDiaChi(jsonObject.getString("DiaChi"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    customerList.add(geCustomerAdapter);

                }
                customerListAdapter = new CustomerListAdapter(customerList,getActivity());
                recyclerView.setAdapter(customerListAdapter);
                customerListAdapter.notifyDataSetChanged();

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