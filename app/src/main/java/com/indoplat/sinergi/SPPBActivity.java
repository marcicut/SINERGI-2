package com.indoplat.sinergi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SPPBActivity extends AppCompatActivity {

    private ImageView btnback;
    SharedPreferences sharedPreferences;

    @Override
    public void onBackPressed() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sppbactivity);

        getDaftarSPPB();

        ImageView btnback = (ImageView) findViewById(R.id.btnBack);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome = new Intent(SPPBActivity.this, HomeActivity.class);
                startActivity(intentHome);
            }
        });

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullrefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDaftarSPPB();
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    public void getDaftarSPPB(){
        ProgressDialog pDialog;
        pDialog = new ProgressDialog(SPPBActivity.this);
        pDialog.setMessage("Sedang mengambil data dari server...");
        if (!pDialog.isShowing()) {
            pDialog.show();
            pDialog.setCanceledOnTouchOutside(false);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        sharedPreferences = getSharedPreferences("IPP_SINERGI_CREDENTIAL", MODE_PRIVATE);

        //dapatkan nama user yang sedang login saat ini
        String userID=sharedPreferences.getString("userIndex","");
        String serverName = sharedPreferences.getString("serverAddress","");
        String postUrl = "http://"+serverName+"/ipp/sinergi/mobiles/getapprovalsppb";

        RequestQueue requestQueue = Volley.newRequestQueue(SPPBActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl,
                new Response.Listener<String>() {
                    Context context;
                    RecyclerView recyclerView;
                    private Adapter_SPPB_List adapter;
                    private ArrayList<SPPB_List_Holder> SPPBHolderList = new ArrayList<SPPB_List_Holder>();

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray dataArray = jsonObject.getJSONArray("data");
                            Integer jumdata=dataArray.length();
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject dataobj = dataArray.getJSONObject(i);
                                String indexSPPB = dataobj.getString("id");
                                String tanggalSPPB = dataobj.getString("tg");
                                String nomorSPPB = dataobj.getString("nr");
                                String namaSPPB = dataobj.getString("nm");
                                String departemenSPPB = dataobj.getString("dp");
                                String statusSPPB= dataobj.getString("st");

                                if(!indexSPPB.isEmpty()){
                                    SPPBHolderList.add(new SPPB_List_Holder(tanggalSPPB, nomorSPPB, indexSPPB, namaSPPB, departemenSPPB, statusSPPB));
                                }
                            }

                            recyclerView = (RecyclerView) findViewById(R.id.recycle_sppb);
                            adapter = new Adapter_SPPB_List(SPPBHolderList);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SPPBActivity.this);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        builder.setTitle("Data Error");
                        builder.setMessage("Ada kesalahan dalam pengambilan data");
                        builder.setPositiveButton("Oke",null);
                        AlertDialog dialog = builder.create();
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", userID.toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}