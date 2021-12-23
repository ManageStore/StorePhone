package com.example.managestorephone;

import static com.example.managestorephone.R.layout.activity_login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.managestorephone.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText txtName,txtPass;
    Button btnSignin;
    CheckBox cbBox;

    String Name,Password;
    SharedPreferences sharedPreferences;
    String url = Utils.BASE_URL+"android_TH/UserLogin.php";
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_login);

        sharedPreferences = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        sessionManager = new SessionManager(this);

        AnhXa();

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Name=txtName.getText().toString().trim();
                Password=txtPass.getText().toString().trim();
                if (TextUtils.isEmpty(Name) || TextUtils.isEmpty(Password)){
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else
                    logIn(Name,Password);

            }
        });

        String loginStatus = sharedPreferences.getString(getResources().getString(R.string.preLoginState),"");
        if(loginStatus.equals("LoggedIn")){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }

    }

    public void logIn(final String email,final String password) {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("login");

                    if (success.equals("1")){
                        for(int i= 0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);

                            String name = object.getString("name").trim();
                            String email = object.getString("email").trim();
                            String password = object.getString("password").trim();

                            String id = object.getString("id").trim();

                            sessionManager.createSession(name,email,password,id);

                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            if(cbBox.isChecked()){
                                editor.putString(getResources().getString(R.string.preLoginState),"LoggedIn");
                            }else{
                                editor.putString(getResources().getString(R.string.preLoginState),"LoggedOut");
                            }
                            editor.apply();

                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        }
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Tên hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Tên hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("a",error.toString());
//                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("name",email);
                param.put("password",password);

                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    private void AnhXa() {
        txtName = (TextInputEditText) findViewById(R.id.txtName);
        txtPass = (TextInputEditText) findViewById(R.id.txtPassword);
        btnSignin = (Button) findViewById(R.id.btnSignin);
        cbBox = (CheckBox) findViewById(R.id.cbBox);
    }
}