package com.example.managestorephone.ui.goods;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.managestorephone.MainActivity;
import com.example.managestorephone.Product.product;
import com.example.managestorephone.R;
import com.example.managestorephone.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ViewDetailProduct extends AppCompatActivity {

    private  static  final String TAG = ViewDetailProduct.class.getSimpleName();

    product Product = null;

    ImageView imgProduct;
    Button btDel;
    TextView tvMasp, updateImg;
    EditText tvTen, tvGia, tvGiaNhap, tvSoluong;
    String gia_format, gianhap_format;
    Toolbar toolbar;

    String Masp, Tensp, soluongsp, giabansp, gianhapsp;
    String gia_ban, gia_nhap;
    boolean check = true;
    ProgressDialog progressDialog;
    Bitmap bitmap;
    Menu action;

    String url_edit = Utils.BASE_URL+"upload_detail_product.php";
    String url_edit_img = Utils.BASE_URL+"android_TH/upload_img_product.php";
    String url_del_product = Utils.BASE_URL+"/deleteproduct.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof product) {
            Product = (product) object;
        }
        tvMasp = (TextView) findViewById(R.id.tvMasp);
        updateImg = (TextView) findViewById(R.id.updateImg);
        imgProduct = (ImageView) findViewById(R.id.imgProduct);
        tvTen = (EditText) findViewById(R.id.detail_ten);
        tvSoluong = (EditText) findViewById(R.id.detail_soluong);
        tvGia = (EditText) findViewById(R.id.detail_gia);
        tvGiaNhap = (EditText) findViewById(R.id.detail_gianhap);
        btDel = (Button) findViewById(R.id.deletePr);

        if (Product != null) {
            Glide.with(getApplicationContext()).load(Product.getHinhAnh()).into(imgProduct);
            tvTen.setText(Product.getTenSP());
            tvMasp.setText(String.valueOf(Product.getMaSP()));
            tvSoluong.setText(String.valueOf(Product.getSoluong()));

            gia_ban = String.valueOf(Product.getGiaban());
            gia_nhap = String.valueOf(Product.getGianhap());

            gia_format = NumberFormat.getNumberInstance(Locale.US).format(Product.getGiaban());
            tvGia.setText(gia_format + "đ");
            gianhap_format = NumberFormat.getNumberInstance(Locale.US).format(Product.getGianhap());
            tvGiaNhap.setText(gianhap_format + "đ");
        }


        updateImg.setVisibility(View.INVISIBLE);
        updateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosefile();

            }
        });

        btDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Masp = tvMasp.getText().toString();

                deleteProductFunction(Masp);

                startActivity(new Intent(ViewDetailProduct.this, MainActivity.class));
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

                updateImg.setVisibility(View.VISIBLE);

                tvGia.setText(gia_ban);
                tvGiaNhap.setText(gia_nhap);

                tvTen.setFocusableInTouchMode(true);
                tvSoluong.setFocusableInTouchMode(true);
                tvGia.setFocusableInTouchMode(true);
                tvGiaNhap.setFocusableInTouchMode(true);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;

            case R.id.menu_save:

                Masp = tvMasp.getText().toString();
                soluongsp = tvSoluong.getText().toString();
                giabansp = tvGia.getText().toString();
                gianhapsp = tvGiaNhap.getText().toString();
                Tensp = tvTen.getText().toString();

                updateProductFunction();

                action.findItem(R.id.menu_edit).setVisible(true);
                action.findItem(R.id.menu_save).setVisible(false);

                updateImg.setVisibility(View.INVISIBLE);

                tvGia.setText(gia_format + "đ");
                tvGiaNhap.setText(gianhap_format + "đ");

                tvTen.setFocusableInTouchMode(false);
                tvSoluong.setFocusableInTouchMode(false);
                tvGia.setFocusableInTouchMode(false);
                tvGiaNhap.setFocusableInTouchMode(false);

                tvTen.setFocusable(false);
                tvSoluong.setFocusable(false);
                tvGia.setFocusable(false);
                tvGiaNhap.setFocusable(false);

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }

    private void choosefile(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data.getData() != null){
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imgProduct.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

            Masp = tvMasp.getText().toString();

            UploadPicture(Masp,getStringImage(bitmap));
        }
    }

    private void UploadPicture(final String Masp,final String hinhanh) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_edit_img, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.i(TAG,response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")){
                        Toast.makeText(ViewDetailProduct.this, "Success", Toast.LENGTH_SHORT).show(); }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(ViewDetailProduct.this, "Try Again!"+ e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("a",error.toString());

                Toast.makeText(ViewDetailProduct.this, "Try Again!"+ error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("MaSP",Masp);
                params.put("HinhAnh",hinhanh);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ViewDetailProduct.this);
        requestQueue.add(stringRequest);
    }

    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodeImage = Base64.encodeToString(imageByteArray,Base64.DEFAULT);

        return  encodeImage;
    }

    //Edit
    private void updateProductFunction() {

        final ProgressDialog progressDialog = new ProgressDialog(ViewDetailProduct.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_edit,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(ViewDetailProduct.this, "Success", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(ViewDetailProduct.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ViewDetailProduct.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("TenSP",Tensp);
                params.put("SoLuong",soluongsp);
                params.put("GiaBan",giabansp);
                params.put("GiaNhap",gianhapsp);
                params.put("MaSP",Masp);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void deleteProductFunction(final String msp) {

        final ProgressDialog progressDialog = new ProgressDialog(ViewDetailProduct.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_del_product,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(ViewDetailProduct.this, "Success", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(ViewDetailProduct.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ViewDetailProduct.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("MaSP",msp);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
