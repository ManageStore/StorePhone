package com.example.managestorephone;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.managestorephone.Product.ProductListAdapter;
import com.example.managestorephone.Product.product;

import java.text.NumberFormat;
import java.util.Locale;

public class ViewDetailProduct extends AppCompatActivity {

    product Product=null;

    ImageView imgProduct;
    TextView tvTen,tvGia,tvGiaNhap,tvSoluong;
    String gia_format,gianhap_format;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_product);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final  Object object =getIntent().getSerializableExtra("detail");
        if(object instanceof product){
            Product = (product) object;
        }
        imgProduct = (ImageView) findViewById(R.id.imgProduct);
        tvTen = (TextView)findViewById(R.id.detail_ten);
        tvSoluong = (TextView) findViewById(R.id.detail_soluong);
        tvGia = (TextView) findViewById(R.id.detail_gia);
        tvGiaNhap = (TextView) findViewById(R.id.detail_gianhap);

        if (Product != null){
            Glide.with(getApplicationContext()).load(Product.getHinhAnh()).into(imgProduct);
            tvTen.setText(Product.getTenSP());
            tvSoluong.setText(String.valueOf(Product.getSoluong()));
            gia_format= NumberFormat.getNumberInstance(Locale.US).format(Product.getGiaban());
            tvGia.setText(gia_format+"đ");
            gianhap_format= NumberFormat.getNumberInstance(Locale.US).format(Product.getGianhap());
            tvGiaNhap.setText(gianhap_format+"đ");
        }

    }
}
