package com.indoplat.sinergi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        TextView lblversion;
        lblversion = (TextView) findViewById(R.id.lblVersion);
        lblversion.setText("Portal SINERGI :: versi "+BuildConfig.VERSION_NAME+"\nby PT Indoplat Perkasa Purnama");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ProgressDialog pDialog;
                pDialog = new ProgressDialog(SplashActivity.this);
                pDialog.setMessage("Initializing...");
                if (!pDialog.isShowing())
                    pDialog.show();

                SharedPreferences sharedPreferences;
                sharedPreferences = getSharedPreferences("IPP_SINERGI_CREDENTIAL", MODE_PRIVATE);

                //tambahkan data nama server yang digunakan sebagai acuannya
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("serverAddress", "192.168.0.228");
                //editor.putString("userID", "");
                editor.apply();

                String uname=sharedPreferences.getString("userID","");

                if(!uname.isEmpty()){
                    Intent intentHome;
                    intentHome = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intentHome);
                    finish();
                }
                else{
                    Intent intentLogin;
                    intentLogin = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intentLogin);
                    finish();
                }

                if (pDialog.isShowing())
                    pDialog.dismiss();
            }
        }, SPLASH_TIME_OUT);
    }
}