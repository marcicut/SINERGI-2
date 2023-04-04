package com.indoplat.sinergi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText txtuserid;
        EditText txtpassword;
        Button btnlogin;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        txtuserid = (EditText) findViewById(R.id.txtUserID);
        txtpassword = (EditText) findViewById(R.id.txtPassword);
        btnlogin = (Button) findViewById(R.id.btnLogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences;
                ProgressDialog pDialog;
                String userid;
                String password;

                sharedPreferences = getSharedPreferences("IPP_SINERGI_CREDENTIAL", MODE_PRIVATE);

                //dapatkan server address untuk koneksinya
                String serverName=sharedPreferences.getString("serverAddress","");

                pDialog = new ProgressDialog(LoginActivity.this);
                userid = txtuserid.getText().toString().trim();
                password = txtpassword.getText().toString().trim();

                if (userid.isEmpty()) {
                    builder.setTitle("Login Error");
                    builder.setMessage("Anda harus mengisi user ID atau alamat email terlebih dahulu");
                    //builder.setMessage(serverName.toString().trim());
                    builder.setPositiveButton("Oke",null);
                    AlertDialog dialog = builder.create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                } else if (password.isEmpty()) {
                    builder.setTitle("Login Error");
                    builder.setMessage("Anda harus mengisi password terlebih dahulu");
                    builder.setPositiveButton("Oke",null);
                    AlertDialog dialog = builder.create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                } else{
                    pDialog.setMessage("Mengecek otoritas...");
                    if (!pDialog.isShowing()) {
                        pDialog.show();
                        pDialog.setCanceledOnTouchOutside(false);
                    }

                    String postUrl = "http://"+serverName+"/ipp/sinergi/mobiles/login";
                    RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    String respon[] = response.split("~");
                                    //If we are getting success from server
                                    Snackbar.make(v, response.toString(), Snackbar.LENGTH_SHORT).show();
                                    if (respon[0].contains("OK")) {
                                        //tambahkan data login ke sharedPreference
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("userIndex", respon[1]);
                                        editor.putString("userDepartment", respon[3]);
                                        editor.putString("userID", respon[11]);
                                        editor.putString("userName", respon[9].toString().trim());
                                        editor.apply();

                                        if (pDialog.isShowing())
                                            pDialog.dismiss();

                                        Intent intentHome;
                                        intentHome = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(intentHome);
                                        finish();
                                        //Snackbar.make(v, response.toString(), Snackbar.LENGTH_SHORT).show();
                                    } else {
                                        if (pDialog.isShowing())
                                            pDialog.dismiss();
                                        builder.setTitle("Login Error");
                                        builder.setMessage(respon[0].toString().trim());
                                        builder.setPositiveButton("Oke",null);
                                        AlertDialog dialog = builder.create();
                                        dialog.setCanceledOnTouchOutside(false);
                                        dialog.show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //You can handle error here if you want
                                    if (pDialog.isShowing())
                                        pDialog.dismiss();
                                    builder.setTitle("Login Error");
                                    builder.setMessage("Tidak dapat menghubungi server. Silahkan coba beberapa saat lagi");
                                    builder.setPositiveButton("Oke",null);
                                    AlertDialog dialog = builder.create();
                                    dialog.setCanceledOnTouchOutside(false);
                                    dialog.show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            //Adding parameters to request
                            params.put("id", userid);
                            params.put("pe", password);
                            //returning parameter
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            }
        });
    }
}