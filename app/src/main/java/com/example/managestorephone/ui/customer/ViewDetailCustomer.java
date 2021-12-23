package com.example.managestorephone.ui.customer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.managestorephone.Customer;
import com.example.managestorephone.MainActivity;
import com.example.managestorephone.Product.Brand;
import com.example.managestorephone.R;
import com.example.managestorephone.ui.goods.ViewDetailBrand;
import com.example.managestorephone.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewDetailCustomer extends AppCompatActivity {

    Customer customer = null;

    Toolbar toolbar;
    EditText edTenKH,edDiaChi,edDT;
    Button btXoaCus;
    TextView tvMakh;
    String TenKH,DiaChi,SDT,MaKH;
    Menu action;

    String url_deleteCustomer = Utils.BASE_URL+"android_TH/customer/deleteCustomer.php";
    String url_updateCustomer = Utils.BASE_URL+"android_TH/customer/update_customer.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_customer);

        toolbar = findViewById(R.id.toolbarINCus);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Object object = getIntent().getSerializableExtra("detailCustomer");
        if (object instanceof Customer) {
            customer = (Customer) object;
        }

        edTenKH = (EditText) findViewById(R.id.edTenKH);
        edDiaChi = (EditText) findViewById(R.id.edDC);
        edDT = (EditText) findViewById(R.id.edSDT);
        tvMakh = (TextView) findViewById(R.id.makh) ;
        btXoaCus = (Button) findViewById(R.id.buttonXoaCus);

        if (customer != null) {
            edTenKH.setText(customer.getHoTen());
            edDiaChi.setText(customer.getDiaChi());
            edDT.setText(customer.getSDT());
            tvMakh.setText(String.valueOf(customer.getMaKH()));
        }

        btXoaCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MaKH = tvMakh.getText().toString();

                deleteCustomerFunction(MaKH);
                onSupportNavigateUp();


//                startActivity(new Intent(ViewDetailCustomer.this, MainActivity.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_action, menu);

        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_edit:

                edTenKH.setFocusableInTouchMode(true);
                edDT.setFocusableInTouchMode(true);
                edDiaChi.setFocusableInTouchMode(true);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;

            case R.id.menu_save:

                MaKH = tvMakh.getText().toString().trim();
                TenKH = edTenKH.getText().toString().trim();
                DiaChi = edDiaChi.getText().toString().trim();
                SDT = edDT.getText().toString().trim();

                UpdateCustomerFunction(MaKH,TenKH,DiaChi,SDT);

                action.findItem(R.id.menu_edit).setVisible(true);
                action.findItem(R.id.menu_save).setVisible(false);

                edTenKH.setFocusableInTouchMode(false);
                edDT.setFocusableInTouchMode(false);
                edDiaChi.setFocusableInTouchMode(false);

                edDiaChi.setFocusable(false);

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }

    private void UpdateCustomerFunction(final String msp,final String ten,final String dc,final String dt) {

        final ProgressDialog progressDialog = new ProgressDialog(ViewDetailCustomer.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_updateCustomer,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(ViewDetailCustomer.this, "Success", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(ViewDetailCustomer.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ViewDetailCustomer.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("MaKH",msp);
                params.put("HoTen",ten);
                params.put("DiaChi",dc);
                params.put("SDT",dt);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void deleteCustomerFunction(final String msp) {

        final ProgressDialog progressDialog = new ProgressDialog(ViewDetailCustomer.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_deleteCustomer,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(ViewDetailCustomer.this, "Success", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(ViewDetailCustomer.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ViewDetailCustomer.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("MaKH",msp);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
