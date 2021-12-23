package com.example.managestorephone.ui.history;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.managestorephone.Customer;
import com.example.managestorephone.CustomerListAdapter;
import com.example.managestorephone.History.HistoryOrder;
import com.example.managestorephone.History.HistoryOrderProduct;
import com.example.managestorephone.History.HistoryOrderProductAdapter;
import com.example.managestorephone.MainActivity;
import com.example.managestorephone.Product.Brand;
import com.example.managestorephone.R;
import com.example.managestorephone.ui.goods.ViewDetailProduct;
import com.example.managestorephone.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class ViewDetailsHistoryOrder extends AppCompatActivity {


 HistoryOrder historyOrder;
    TextView TenKH,SDT,TongTien;
    Integer MaHD;

    List<HistoryOrderProduct> historyOrderProductList;
    RecyclerView recyclerView;
    HistoryOrderProductAdapter historyOrderProductAdapter;

    JsonArrayRequest request;
    RequestQueue requestQueue;
    View view;
    int RecyclerViewPosition;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter recyclerViewAdapter;

    Toolbar toolbarbrand;
    Menu action;

    String url= Utils.BASE_URL+"android_TH/order/ViewOrderId.php";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history_order);

        toolbarbrand = findViewById(R.id.toolbarbrand);
        setSupportActionBar(toolbarbrand);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        historyOrderProductList = new ArrayList<>();

        final Object object = getIntent().getSerializableExtra("detailOrder");
        if (object instanceof HistoryOrder) {
            historyOrder = (HistoryOrder) object;
        }
        MaHD= historyOrder.getMaHD();
        TenKH = (TextView) findViewById(R.id.TenKH);
        SDT = (TextView) findViewById(R.id.SDT);
        TongTien = (TextView) findViewById(R.id.TongTien);

        TenKH.setText(historyOrder.getHoTen());
        SDT.setText(historyOrder.getSDT());

        String tongtien_format = NumberFormat.getNumberInstance(Locale.US).format(historyOrder.getDonGia());
        TongTien.setText(tongtien_format+"đ");

        toolbarbrand.setTitle("HD000".concat(String.valueOf(historyOrder.getMaHD())));

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(ViewDetailsHistoryOrder.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        getMaDH();

    }
    private void getMaDH() {

        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                    try {
                        JSONArray j = new JSONArray(response);

                        for (int i = 0; i < j.length(); i++) {
                            JSONObject jsonObject = null;
                            jsonObject = j.getJSONObject(i);
                            HistoryOrderProduct getHistoryOrderProduct = new HistoryOrderProduct();
                            getHistoryOrderProduct.setMaHD(jsonObject.getInt("MaHD"));
                            getHistoryOrderProduct.setGiaTong(jsonObject.getInt("GiaTong"));
                            getHistoryOrderProduct.setSoLuong(jsonObject.getInt("SoLuong"));
                            getHistoryOrderProduct.setMaSP(jsonObject.getInt("MaSP"));
                            getHistoryOrderProduct.setTenSP(jsonObject.getString("TenSP"));
                            historyOrderProductList.add(getHistoryOrderProduct);

                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                historyOrderProductAdapter = new HistoryOrderProductAdapter(historyOrderProductList, ViewDetailsHistoryOrder.this);
                recyclerView.setAdapter(historyOrderProductAdapter);

                }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewDetailsHistoryOrder.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("MaHD",String.valueOf(MaHD));
                return params;
            }
        };

        requestQueue = Volley.newRequestQueue(ViewDetailsHistoryOrder.this);
        requestQueue.add(request);
    }





    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return true;
    }






}
