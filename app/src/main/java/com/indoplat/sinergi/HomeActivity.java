package com.indoplat.sinergi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import me.pushy.sdk.Pushy;

public class HomeActivity extends AppCompatActivity {
    private TextView runningText;
    private TextView greetingText;
    private TextView NamaUser;
    private TextView DepartementUser;

    private CardView mnpurchasing;

    @Override
    public void onBackPressed() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_home);

        runningText = (TextView) findViewById(R.id.txtrunning);
        runningText.setSelected(true);

        greetingText = (TextView) findViewById(R.id.lblWelcomeUser);
        NamaUser = (TextView) findViewById(R.id.lblNamaUser);
        DepartementUser = (TextView) findViewById(R.id.lblDepartemenUser);
        mnpurchasing = (CardView) findViewById(R.id.mnPurchasing);

        greeting();

        mnpurchasing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences;
                sharedPreferences = getSharedPreferences("IPP_SINERGI_CREDENTIAL", MODE_PRIVATE);
                String serverName = sharedPreferences.getString("serverAddress","");
                String userIndex = sharedPreferences.getString("userIndex","");
                String postUrl = "http://"+serverName+"/ipp/sinergi/mobiles/otosppb";
                RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.contains("OK")) {
                                    Intent intentSPPB;
                                    intentSPPB = new Intent(HomeActivity.this, SPPBActivity.class);
                                    startActivity(intentSPPB);
                                    finish();
                                } else {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Anda tidak diperkenankan mengakses menu SPPB", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Terjadi kesalahan. Silahkan coba beberapa saat lagi", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("id", userIndex.toString());
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

        //klik bottom menu starts here
        BottomNavigationView bottomNavigation;
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.activity_main);
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.mnhome:
                        Intent intentHome;
                        intentHome = new Intent(HomeActivity.this, HomeActivity.class);
                        startActivity(intentHome);
                        finish();
                        break;

                    case R.id.mnaccount:
                        //Intent intentHome;
                        //intentHome = new Intent(HomeActivity.this, HomeActivity.class);
                        //startActivity(intentHome);
                        //finish();
                        break;

                    case R.id.mnnotif:
                        //Intent intentHome;
                        //intentHome = new Intent(HomeActivity.this, HomeActivity.class);
                        //startActivity(intentHome);
                        //finish();
                        break;

                    case R.id.mnscan:
                        //Intent intentHome;
                        //intentHome = new Intent(HomeActivity.this, HomeActivity.class);
                        //startActivity(intentHome);
                        //finish();
                        break;

                    case R.id.mnlogout:
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                        builder.setTitle("Konfirmasi");
                        builder.setMessage("Apakah anda yakin akan keluar dari portal SINERGI?");
                        builder.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intentLogin;
                                SharedPreferences sharedPreferences;

                                sharedPreferences = getSharedPreferences("IPP_SINERGI_CREDENTIAL", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("userIndex", "");
                                editor.putString("userDepartment", "");
                                editor.putString("userID", "");
                                editor.putString("userName", "");
                                editor.apply();

                                intentLogin = new Intent(HomeActivity.this, LoginActivity.class);
                                startActivity(intentLogin);
                                finish();
                            }
                        });
                        builder.setNegativeButton("Batal",null);
                        AlertDialog dialog = builder.create();
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                        break;

                    //default:
                    //    viewPager.setCurrentItem(0);
                }
                return true; // return true;
            }
        });
        //klik bottom menu ends here
    }

    private void greeting(){
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("IPP_SINERGI_CREDENTIAL", MODE_PRIVATE);
        String userName=sharedPreferences.getString("userName","");
        String userDepartment=sharedPreferences.getString("userDepartment","");

        NamaUser.setText(userName.toString());
        DepartementUser.setText("Departemen "+userDepartment.toString());

        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay>=0 && timeOfDay<12){
            greetingText.setText("Selamat Pagi,");
        }
        else
        if(timeOfDay>=12 && timeOfDay<15){
            greetingText.setText("Selamat Siang,");
        }
        else
        if(timeOfDay>=15 && timeOfDay<18){
            greetingText.setText("Selamat Sore,");
        }
        else
        if(timeOfDay>=18 && timeOfDay<24){
            greetingText.setText("Selamat Malam,");
        }
    }



}