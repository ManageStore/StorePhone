package com.example.managestorephone.ui.history;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.example.managestorephone.History.HistoryOrder;
import com.example.managestorephone.History.HistoryOrderAdapter;
import com.example.managestorephone.R;
import com.example.managestorephone.databinding.FragmentCustomerBinding;
import com.example.managestorephone.databinding.FragmentHistoryBinding;
import com.example.managestorephone.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;

    String url= Utils.BASE_URL.concat("android_TH/order/ViewOrder.php");

    List<HistoryOrder> historyOrderList;
    RecyclerView recyclerView;
    HistoryOrderAdapter historyOrderAdapter;
    EditText searchEdit;

    JsonArrayRequest request;
    RequestQueue requestQueue;
    View view;
    int RecyclerViewPosition;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter recyclerViewAdapter;
    ArrayList<String> ImageTitle;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        historyOrderList = new ArrayList<>();
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        call_json();



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
        List<HistoryOrder> filteredList = new ArrayList<>();

        for (HistoryOrder item : historyOrderList) {
            if (item.getHoTen().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);

            }
        }

//        recyclerViewAdapter = new CustomerListAdapter(customerList,getActivity());
        historyOrderAdapter.filterList(filteredList);


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
                    HistoryOrder getHistoryAdapter = new HistoryOrder();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);

                        getHistoryAdapter.setHoTen(jsonObject.getString("HoTen"));
                        getHistoryAdapter.setMaHD(jsonObject.getInt("MaHD"));
                        getHistoryAdapter.setMaKH(jsonObject.getInt("MaKH"));
                        getHistoryAdapter.setSDT(jsonObject.getString("SDT"));
                        getHistoryAdapter.setDonGia(jsonObject.getInt("DonGia"));
                        getHistoryAdapter.setNgayBan(jsonObject.getString("NgayBan"));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    historyOrderList.add(getHistoryAdapter);

                }
                historyOrderAdapter = new HistoryOrderAdapter(historyOrderList,getActivity());
                recyclerView.setAdapter(historyOrderAdapter);

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