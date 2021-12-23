package com.example.managestorephone.ui.setting;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.managestorephone.LoginActivity;
import com.example.managestorephone.MainActivity;
import com.example.managestorephone.databinding.FragmentSettingBinding;
import com.example.managestorephone.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SettingFragment extends Fragment {

    private FragmentSettingBinding binding;

    String MKcu,MKmoi,MKxn;

    String url= Utils.BASE_URL.concat("android_TH/userPassReset.php");


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btLuumk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = Utils.getId;
                MKcu = binding.edmkc.getText().toString();
                MKmoi = binding.edmkm.getText().toString();
                MKxn = binding.xmmk.getText().toString();

                if(TextUtils.isEmpty(MKcu) || TextUtils.isEmpty(MKmoi) || TextUtils.isEmpty(MKxn)){
                    Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else{
                    if (MKmoi.equals(MKxn) == false){
                        Toast.makeText(getContext(), "Các mật khẩu đã nhập không khớp.Hãy thử lại!", Toast.LENGTH_SHORT).show();
                    }else{
                        SaveEditDetail(id,MKcu,MKmoi);

                        binding.edmkc.setText(null);
                        binding.edmkm.setText(null);
                        binding.xmmk.setText(null);
                    }
                }

            }
        });

        binding.btLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có chắc không?");

                builder.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing, but close the dialog
                        startActivity(new Intent(getContext(), LoginActivity.class));
                    }
                });

                builder.setNegativeButton("KHÔNG", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        return root;
    }
    //Edit
    private void SaveEditDetail(final String ID,final String passOld,final String passNew) {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getContext(), "Mật khẩu không đúng!", Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("passwordOld",passOld);
                params.put("passwordNew",passNew);
                params.put("id",ID);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
