package com.example.managestorephone.ui.goods;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.managestorephone.Adapter.BrandAdapter;
import com.example.managestorephone.Adapter.ProductListAdapter;
import com.example.managestorephone.Adapter.RecyclerViewAdapter;
import com.example.managestorephone.models.Brand;
import com.example.managestorephone.models.product;
import com.example.managestorephone.R;
import com.example.managestorephone.databinding.FragmentGoodsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GoodsFragment extends Fragment {

    private FragmentGoodsBinding binding;

    String url = "http://192.168.1.8/android_TH/product.php";
    String urlBrand = "http://192.168.1.8/android_TH/brand.php";

    List<Brand> brandList;
    List<product> listProduct;
    RecyclerView recyclerView,recyclerView2;
    String hinhAnh="HinhAnh";
    String ten = "TenSP";
    String soluong = "SoLuong";
    String giaban = "GiaBan";
    String maSP = "MaSP";
    JsonArrayRequest request,request2;
    RequestQueue requestQueue,requestQueue2;
    RecyclerView.LayoutManager layoutManager,layoutManager2;
    RecyclerView.Adapter recyclerViewAdapter,recyclerViewAdapter2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGoodsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
        call_json2();

//        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener(){
//
//            GestureDetector gestureDetector = new GestureDetector(getActivity(),new GestureDetector.SimpleOnGestureListener(){
//                @Override
//                public boolean onSingleTapUp(MotionEvent e) {
//                    return true;
//                }
//            });
//            @Override
//            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//                view = rv.findChildViewUnder(e.getX(),e.getY());
//                if(view != null && gestureDetector.onTouchEvent(e)){
//                    RecyclerViewPosition = rv.getChildAdapterPosition(view);
//
//                    Toast.makeText(getActivity(), ImageTitle.get(RecyclerViewPosition), Toast.LENGTH_SHORT).show();
//                }
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//            }
//        });

        return root;
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
                        getProduct.setMaSP(jsonObject.getString(maSP));
                        getProduct.setSoluong(jsonObject.getInt(soluong));
                        getProduct.setGiaban(jsonObject.getInt(giaban));

                        getProduct.setHinhAnh(jsonObject.getString(hinhAnh));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    listProduct.add(getProduct);
                }
                recyclerViewAdapter = new RecyclerViewAdapter(listProduct,getActivity());
                recyclerView.setAdapter(recyclerViewAdapter);
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

    private void call_json2() {

        request2 = new JsonArrayRequest(urlBrand, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i=0;i<response.length();i++){
                    Brand getBrand = new Brand();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);

                        getBrand.setTenhang(jsonObject.getString("TenHang"));

                        getBrand.setHinhAnhhang(jsonObject.getString("hinhAnh"));

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
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
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