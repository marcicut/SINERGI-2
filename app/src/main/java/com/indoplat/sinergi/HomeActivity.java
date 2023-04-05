package com.indoplat.sinergi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_navigation_menu, menu);
        return true;
    }

    /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
    AlertDialog dialog = builder.create();
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.mnhome:
                builder.setTitle("Menu HOME");
                builder.setMessage("Menu Home diklik");
                builder.setPositiveButton("Oke",null);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                return true;
            case R.id.mnabout:
                builder.setTitle("Menu ABOUT");
                builder.setMessage("Menu About diklik");
                builder.setPositiveButton("Oke",null);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                return true;
            case R.id.mnnotif:
                builder.setTitle("Menu NOTIFICATION");
                builder.setMessage("Menu Notification diklik");
                builder.setPositiveButton("Oke",null);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                return true;
            case R.id.mnscan:
                builder.setTitle("Menu SCAN");
                builder.setMessage("Menu Scan diklik");
                builder.setPositiveButton("Oke",null);
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                return true;
            case R.id.mnaccount:
                builder.setTitle("Menu ACCOUNT");
                builder.setMessage("Menu Account diklik");
                builder.setPositiveButton("Oke",null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

}