package com.example.managestorephone.ui.goods;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.managestorephone.Product.Brand;
import com.example.managestorephone.R;
import com.example.managestorephone.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddProductActivity extends AppCompatActivity {

    Toolbar toolbar1;
    Button btnLuu;
    Spinner spinner;
    EditText txttendt,txtsoluong,txtgiaban,txtgianhap;
    TextView tvThemAnh,tvmabrand;
    CircleImageView imgPhone;

    ArrayList<String> brandList = new ArrayList<>();
    ArrayAdapter<String> brandAdapter;
    RequestQueue requestQueue;

    ProgressDialog progressDialog;
    Bitmap bitmap;
    boolean check = true;
    String mahang;
    String namePhone,thuonghieu,soluong,giaban,gianhap;

    String urlPath = Utils.BASE_URL+"android_TH/product/AddProduct.php";
    String urlSelectBrand = Utils.BASE_URL+"android_TH/brand/brand.php";
    String urlSelected = Utils.BASE_URL+"android_TH/brand/brandSelect.php";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);

        toolbar1 = findViewById(R.id.toolbarAdd);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txttendt = (EditText) findViewById(R.id.txttendt);
        txtsoluong = (EditText) findViewById(R.id.txtsoluong);
        txtgiaban = (EditText) findViewById(R.id.txtgianban);
        txtgianhap = (EditText) findViewById(R.id.txtgianhap);
        tvThemAnh = (TextView) findViewById(R.id.addImg);
        tvmabrand = (TextView) findViewById(R.id.mabrand);
        imgPhone = (CircleImageView) findViewById(R.id.image_phone);
        btnLuu = (Button) findViewById(R.id.buttonLuu);

        requestQueue = Volley.newRequestQueue(this);
        spinner = (Spinner)findViewById(R.id.spinner);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, urlSelectBrand, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i=0;i<response.length();i++){

                            Brand getBrand = new Brand();
                            JSONObject jsonObject = null;

                            try {
                                jsonObject = response.getJSONObject(i);

                                String nameBrand = jsonObject.getString("TenHang");

                                brandList.add(nameBrand);
                                brandAdapter = new ArrayAdapter<>(AddProductActivity.this,
                                        android.R.layout.simple_spinner_item,brandList);
                                brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner.setAdapter(brandAdapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        requestQueue.add(jsonArrayRequest);

//        thuonghieu = spinner.getSelectedItem().toString();
        spinner.setOnItemSelectedListener(selectBrand);


        tvThemAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                namePhone = txttendt.getText().toString();
                soluong = txtsoluong.getText().toString();
                giaban = txtgiaban.getText().toString();
                gianhap = txtgianhap.getText().toString();
                mahang = tvmabrand.getText().toString();


                addProductFunction();

                txttendt.setText(null);
                txtsoluong.setText(null);
                txtgiaban.setText(null);
                txtgianhap.setText(null);

            }
        });

    }
    private AdapterView.OnItemSelectedListener selectBrand =new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if(i > 0){
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlSelected, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i=0;i<response.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String maBrand = object.getString("MaHang");

                                    tvmabrand.setText(maBrand);
                                }


                                } catch (JSONException e) {
                                e.printStackTrace();
                            }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
                ){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> param = new HashMap<>();
                        param.put("TenHang",spinner.getSelectedItem().toString());

                        return param;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(AddProductActivity.this);
                requestQueue.add(stringRequest);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };


    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                imgPhone.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public void addProductFunction(){
        ByteArrayOutputStream byteArrayOutputStreamObject ;
        byteArrayOutputStreamObject = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);
        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();
        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(AddProductActivity.this,"Loading...","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                progressDialog.dismiss();

                // Printing uploading success message coming from server on android app.
                Toast.makeText(AddProductActivity.this,"Thêm thành công",Toast.LENGTH_LONG).show();

                // Setting image as transparent after done uploading.
                imgPhone.setImageResource(android.R.color.transparent);
            }

            @Override
            protected String doInBackground(Void... voids) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();
                HashMap<String,String> params = new HashMap<String,String>();

                params.put("TenSP",namePhone);
                params.put("SoLuong",soluong);
                params.put("GiaBan",giaban);
                params.put("GiaNhap",gianhap);
                params.put("MaHang",mahang);

                params.put("HinhAnh",ConvertImage);

                String finalData = imageProcessClass.ImageHttpRequest(urlPath,params);

                return finalData;
            }

        }
        AsyncTaskUploadClass asyncTaskUploadClass = new AsyncTaskUploadClass();
        asyncTaskUploadClass.execute();
    }

    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL, HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {

                URL url;
                HttpURLConnection httpURLConnectionObject;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject;
                BufferedReader bufferedReaderObject;
                int RC;

                url = new URL(requestURL);

                httpURLConnectionObject = (HttpURLConnection) url.openConnection();

                httpURLConnectionObject.setReadTimeout(19000);

                httpURLConnectionObject.setConnectTimeout(19000);

                httpURLConnectionObject.setRequestMethod("POST");

                httpURLConnectionObject.setDoInput(true);

                httpURLConnectionObject.setDoOutput(true);

                OutPutStream = httpURLConnectionObject.getOutputStream();

                bufferedWriterObject = new BufferedWriter(

                        new OutputStreamWriter(OutPutStream, "UTF-8"));

                bufferedWriterObject.write(bufferedWriterDataFN(PData));

                bufferedWriterObject.flush();

                bufferedWriterObject.close();

                OutPutStream.close();

                RC = httpURLConnectionObject.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReaderObject.readLine()) != null) {

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

    }

    private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

        StringBuilder stringBuilderObject;

        stringBuilderObject = new StringBuilder();

        for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {

            if (check)

                check = false;
            else
                stringBuilderObject.append("&");

            stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

            stringBuilderObject.append("=");

            stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
        }

        return stringBuilderObject.toString();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
        return true;
    }

}
