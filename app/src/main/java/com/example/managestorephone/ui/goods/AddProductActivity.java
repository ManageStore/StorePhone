package com.example.managestorephone.ui.goods;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.managestorephone.MainActivity;
import com.example.managestorephone.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddProductActivity extends AppCompatActivity {

    Toolbar toolbar1;
    Button btnLuu;
    EditText txttendt,txtthuonghieu,txtsoluong,txtgiaban,txtgianhap;
    TextView tvThemAnh;
    CircleImageView imgPhone;

    ProgressDialog progressDialog;
    Bitmap bitmap;
    boolean check = true;
    String namePhone,thuonghieu,soluong,giaban,gianhap;
    String urlPath = "http://192.168.1.7:8080/android_TH/AddProduct.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_productdetail);

        toolbar1 = findViewById(R.id.toolbarAdd);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txttendt = (EditText) findViewById(R.id.txttendt);
        txtthuonghieu = (EditText) findViewById(R.id.txtthuonghieu);
        txtsoluong = (EditText) findViewById(R.id.txtsoluong);
        txtgiaban = (EditText) findViewById(R.id.txtgianban);
        txtgianhap = (EditText) findViewById(R.id.txtgianhap);
        tvThemAnh = (TextView) findViewById(R.id.addImg);
        imgPhone = (CircleImageView) findViewById(R.id.image_phone);
        btnLuu = (Button) findViewById(R.id.buttonLuu);

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
                thuonghieu = txtthuonghieu.getText().toString();
                soluong = txtsoluong.getText().toString();
                giaban = txtgiaban.getText().toString();
                gianhap = txtgianhap.getText().toString();

                addProductFunction();

                txttendt.setText(null);
                txtthuonghieu.setText(null);
                txtsoluong.setText(null);
                txtgiaban.setText(null);
                txtgianhap.setText(null);

            }
        });

    }

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

//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Uploading...");
//        progressDialog.show();

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(AddProductActivity.this,"Image is Uploading","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                progressDialog.dismiss();

                // Printing uploading success message coming from server on android app.
                Toast.makeText(AddProductActivity.this,s,Toast.LENGTH_LONG).show();

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
                params.put("MaHang",thuonghieu);

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
}
