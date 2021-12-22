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
import com.example.managestorephone.Product.Brand;
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

public class ViewDetailBrand extends AppCompatActivity {

    Brand brand = null;

    TextView mahang,tvImgUpdate;
    EditText tenhang;
    ImageView hinhHang;
    String MaHang,TenHang;
    Button btdelBrand;

    Toolbar toolbarbrand;
    Menu action;
    Bitmap bitmap;

    String url_edit_hang = Utils.BASE_URL+"android_TH/upload_detail_brand.php";
    String url_edit_img_hang = Utils.BASE_URL+"android_TH/upload_img_brand.php";
    String url_del_brand = Utils.BASE_URL+"android_TH/deletebrand.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_brand);

        toolbarbrand = findViewById(R.id.toolbarbrand);
        setSupportActionBar(toolbarbrand);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Object object = getIntent().getSerializableExtra("detailBrand");
        if (object instanceof Brand) {
            brand = (Brand) object;
        }
        tenhang = (EditText) findViewById(R.id.detail_tenhang);
        mahang = (TextView) findViewById(R.id.detail_mahang);
        tvImgUpdate = (TextView) findViewById(R.id.tvupdateImg);
        hinhHang = (ImageView) findViewById(R.id.hinhhang);
        btdelBrand = (Button) findViewById(R.id.deleteBr);

        if (brand != null) {
            Glide.with(getApplicationContext()).load(brand.getHinhAnhhang()).into(hinhHang);
            tenhang.setText(brand.getTenhang());
            mahang.setText(String.valueOf(brand.getMahang()));
        }

        tvImgUpdate.setVisibility(View.INVISIBLE);
        tvImgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosefile();
            }
        });

        btdelBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MaHang = mahang.getText().toString();

                deleteBrandFunction(MaHang);

                startActivity(new Intent(ViewDetailBrand.this, MainActivity.class));
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

                tvImgUpdate.setVisibility(View.VISIBLE);

                tenhang.setFocusableInTouchMode(true);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;

            case R.id.menu_save:

                 TenHang = tenhang.getText().toString().trim();
                 MaHang = mahang.getText().toString().trim();

                updateBrandFunction();

                action.findItem(R.id.menu_edit).setVisible(true);
                action.findItem(R.id.menu_save).setVisible(false);

                tvImgUpdate.setVisibility(View.INVISIBLE);

                tenhang.setFocusableInTouchMode(false);

                tenhang.setFocusable(false);

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
                hinhHang.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

            MaHang = mahang.getText().toString().trim();

            UploadPicture(MaHang,getStringImage(bitmap));
        }
    }

    private void UploadPicture(final String Mahan,final String hinhAnh) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_edit_img_hang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")){
                        Toast.makeText(ViewDetailBrand.this, "Success", Toast.LENGTH_SHORT).show(); }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Log.e("a",e.toString());

//                    Toast.makeText(ViewDetailBrand.this, "Try Again!"+ e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("a",error.toString());
//                Toast.makeText(ViewDetailBrand.this, "Try Again!"+ error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("MaHang",Mahan);
                params.put("hinhAnh",hinhAnh);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ViewDetailBrand.this);
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
    private void updateBrandFunction() {

        final ProgressDialog progressDialog = new ProgressDialog(ViewDetailBrand.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_edit_hang,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(ViewDetailBrand.this, "Success", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(ViewDetailBrand.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ViewDetailBrand.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("TenHang",TenHang);
                params.put("MaHang",MaHang);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void deleteBrandFunction(final String msp) {

        final ProgressDialog progressDialog = new ProgressDialog(ViewDetailBrand.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_del_brand,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(ViewDetailBrand.this, "Success", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(ViewDetailBrand.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ViewDetailBrand.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("MaHang",msp);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
