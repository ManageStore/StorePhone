package com.example.managestorephone.ui.goods;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.managestorephone.Product.BrandAdapter;
import com.example.managestorephone.Product.ProductListAdapter;
import com.example.managestorephone.Product.Brand;
import com.example.managestorephone.Product.product;

import com.example.managestorephone.R;
import com.example.managestorephone.databinding.FragmentProductsBinding;
import com.example.managestorephone.ui.sell.ListSearchProductFragment;
import com.example.managestorephone.ui.sell.SellFragment;
import com.example.managestorephone.utils.Utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment {
    FragmentProductsBinding binding;


    String urlBase= Utils.BASE_URL;
    String url = Utils.BASE_URL+"android_TH/product/product.php";
    String urlBrand = Utils.BASE_URL+"android_TH/brand/brand.php";


    EditText searchEdit;
    ProductListAdapter productListAdapter;
    Button add_product;

    List<Brand> brandList;
    List<product> listProduct;
    RecyclerView recyclerView,recyclerView2;
    String hinhAnh="HinhAnh";
    String ten = "TenSP";
    String soluong = "SoLuong";
    String giaban = "GiaBan";
    String gianhap = "GiaNhap";
    String maSP = "MaSP";
    JsonArrayRequest request,request2;
    RequestQueue requestQueue,requestQueue2;
    RecyclerView.LayoutManager layoutManager,layoutManager2;
    RecyclerView.Adapter recyclerViewAdapter,recyclerViewAdapter2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProductsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        binding.tvAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(getContext(), AddProductActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);


            }
        });
        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                ProductsFragment productFragment= new ProductsFragment();
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main,productFragment);
                fragmentTransaction.commit();
            }
        });


        binding.tvaddBrandProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), AddBrandProductActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);


            }
        });

        brandList = new ArrayList<>();
        recyclerView2 = (RecyclerView) root.findViewById(R.id.rec_hangdienthoai);

        listProduct = new ArrayList<>();
        recyclerView = (RecyclerView) root.findViewById(R.id.rec_dienthoai);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        layoutManager2 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView2.setLayoutManager(layoutManager2);

        call_json();
        call_jsonBrand();



        searchEdit = (EditText) root.findViewById(R.id.id_search_product);
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



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void filter(String text) {
        List<product> filteredList = new ArrayList<>();

        for (product item : listProduct) {
            if (item.getTenSP().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);

            }
        }

//        recyclerViewAdapter = new CustomerListAdapter(customerList,getActivity());
        productListAdapter.filterList(filteredList);

    }


    private void call_json() {

        request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i=0;i<response.length();i++){
                    product getProduct = new product();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);

                        getProduct.setTenSP(jsonObject.getString(ten));
                        getProduct.setMaSP(jsonObject.getInt(maSP));
                        getProduct.setSoluong(jsonObject.getInt(soluong));
                        getProduct.setGiaban(jsonObject.getInt(giaban));
                        getProduct.setGianhap(jsonObject.getInt(gianhap));

                        getProduct.setHinhAnh(urlBase.concat(jsonObject.getString(hinhAnh)));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    listProduct.add(getProduct);
                }
                productListAdapter = new ProductListAdapter(listProduct,getActivity());
                recyclerView.setAdapter(productListAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }

    private void call_jsonBrand() {

        request2 = new JsonArrayRequest(urlBrand, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i=0;i<response.length();i++){
                    Brand getBrand = new Brand();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);

                        getBrand.setTenhang(jsonObject.getString("TenHang"));
                        getBrand.setMahang(Integer.parseInt(jsonObject.getString("MaHang")));

                        getBrand.setHinhAnhhang(urlBase.concat(jsonObject.getString("hinhAnh")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    brandList.add(getBrand);
                }
                recyclerViewAdapter2 = new BrandAdapter(getActivity(),brandList);
                recyclerView2.setAdapter(recyclerViewAdapter2);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue2 = Volley.newRequestQueue(getActivity());
        requestQueue2.add(request2);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}