package com.example.managestorephone.ui.customer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.managestorephone.MainActivity;
import com.example.managestorephone.MySingleton;
import com.example.managestorephone.R;
import com.example.managestorephone.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class AddCustomerActivity extends AppCompatActivity {

    Toolbar toolbar1;
    EditText edTenKH,edDiaChi,edDT;
    Button btLuuCus;
    String TenKH,DiaChi,SDT;

    String url_addCustomer = Utils.BASE_URL+"android_TH/customer/addCustomer.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcustomer);

        toolbar1 = findViewById(R.id.toolbarAddCus);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edTenKH = (EditText) findViewById(R.id.edtTenKH);
        edDiaChi = (EditText) findViewById(R.id.edtDC);
        edDT = (EditText) findViewById(R.id.edtSDT);
        btLuuCus = (Button) findViewById(R.id.buttonLuuCus);

        btLuuCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TenKH = edTenKH.getText().toString();
                DiaChi = edDiaChi.getText().toString();
                SDT = edDT.getText().toString();

                if(TextUtils.isEmpty(TenKH) || TextUtils.isEmpty(DiaChi) || TextUtils.isEmpty(SDT)){

                    Toast.makeText(AddCustomerActivity.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();

                }else{
                    registerNewAccount(TenKH,DiaChi,SDT);
                }
            }
        });

    }

    private void registerNewAccount(final String S_ten,final String S_DC,final String S_SDT){
        final ProgressDialog progressDialog = new ProgressDialog(AddCustomerActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, url_addCustomer, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("You are registered successfully")){
                    Toast.makeText(AddCustomerActivity.this, "Đăng kí thành công!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddCustomerActivity.this, MainActivity.class));
                    progressDialog.dismiss();
                    finish();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(AddCustomerActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(AddCustomerActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();

                param.put("HoTen",S_ten);
                param.put("DiaChi",S_DC);
                param.put("SDT",S_SDT);

                return param;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(3000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(AddCustomerActivity.this).addToRequestQueue(request);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
