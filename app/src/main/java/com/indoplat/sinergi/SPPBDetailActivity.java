package com.indoplat.sinergi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.chip.Chip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.pushy.sdk.Pushy;

public class SPPBDetailActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sppbdetail);

        String indexSPPB = getIntent().getStringExtra("indexSPPB");

        //cari data header SPPB
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        ProgressDialog pDialog;
        pDialog = new ProgressDialog(SPPBDetailActivity.this);

        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("IPP_SINERGI_CREDENTIAL", MODE_PRIVATE);

        //dapatkan server address untuk koneksinya
        String serverName=sharedPreferences.getString("serverAddress","");
        String postUrl = "http://"+serverName+"/ipp/sinergi/mobiles/getsppbheader";
        RequestQueue requestQueue = Volley.newRequestQueue(SPPBDetailActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String indexESPPB="", tanggalSPPB="", tanggalPerluSPPB="", nomerSPPB="", namaSPPB="",
                                departemenSPPB="", sitesSPPB="", purchaseRequestSPPB="", supplierSPPB="",
                                keteranganSPPB="";
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray dataArray = jsonObject.getJSONArray("data");
                            JSONObject dataobj = dataArray.getJSONObject(0);

                            indexESPPB = dataobj.getString("id");
                            tanggalSPPB = dataobj.getString("tg");
                            tanggalPerluSPPB = dataobj.getString("tp");
                            nomerSPPB = dataobj.getString("nr");
                            namaSPPB = dataobj.getString("nm");
                            departemenSPPB = dataobj.getString("dp");
                            //sitesSPPB = dataobj.getString("si");
                            purchaseRequestSPPB = dataobj.getString("pr");
                            supplierSPPB = dataobj.getString("sp");
                            keteranganSPPB= dataobj.getString("kt");

                            TextView lblNoSPPB, lblTgSPPB, lblTgPerlu, lblBuatSPPB, lblDepSPPB,
                                    lblPR, lblSupplier, lblketerangan;

                            lblNoSPPB = (TextView) findViewById(R.id.lblNoESPPB);
                            lblTgSPPB = (TextView) findViewById(R.id.lblTglESPPB);
                            lblTgPerlu = (TextView) findViewById(R.id.lblTglEPerlu);
                            lblBuatSPPB = (TextView) findViewById(R.id.lblPembuatESPPB);
                            lblDepSPPB = (TextView) findViewById(R.id.lblDepartemenESPPB);
                            lblPR = (TextView) findViewById(R.id.lblNoEPR);
                            lblSupplier = (TextView) findViewById(R.id.lblPrefESupplier);
                            lblketerangan = (TextView) findViewById(R.id.lblKeteranganESPPB);

                            lblNoSPPB.setText(nomerSPPB.toString());
                            lblTgSPPB.setText(tanggalSPPB.toString());
                            lblTgPerlu.setText(tanggalPerluSPPB.toString());
                            lblBuatSPPB.setText(namaSPPB.toString());
                            lblDepSPPB.setText(departemenSPPB.toString());
                            lblPR.setText(purchaseRequestSPPB.toString());
                            lblSupplier.setText(supplierSPPB.toString());
                            lblketerangan.setText(keteranganSPPB.toString());

                            //cari detail SPPB mulai disini
                            String postUrl = "http://"+serverName+"/ipp/sinergi/mobiles/getsppbdetail";
                            RequestQueue requestQueue = Volley.newRequestQueue(SPPBDetailActivity.this);
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl,
                                    new Response.Listener<String>() {
                                        Context context;
                                        RecyclerView recyclerView;
                                        private Adapter_SPPB_Detail adapter;
                                        private ArrayList<SPPB_Detail_Holder> SPPBDetailHolderList = new ArrayList<SPPB_Detail_Holder>();

                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray dataArray = jsonObject.getJSONArray("data");
                                                Integer jumdata=dataArray.length();
                                                for (int i = 0; i < dataArray.length(); i++) {
                                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                                    String indexItemSPPB = dataobj.getString("id");
                                                    String purchaseRequestSPPB = dataobj.getString("pr");
                                                    String purchaseOrderSPPB = dataobj.getString("po");
                                                    String deskripsiBarang = dataobj.getString("ds");
                                                    String merekBarang = dataobj.getString("me");
                                                    String tipeBarang= dataobj.getString("ti");
                                                    String jumlahRequest= dataobj.getString("ju");
                                                    String jumlahPO= dataobj.getString("jp");
                                                    String keteranganDetail= dataobj.getString("kt");
                                                    String attachmentBarang= dataobj.getString("dk");
                                                    String isDeleted= dataobj.getString("is");
                                                    String deleteReason= dataobj.getString("dr");

                                                    if(!indexItemSPPB.isEmpty()){
                                                        SPPBDetailHolderList.add(new SPPB_Detail_Holder(indexItemSPPB, purchaseRequestSPPB,
                                                                purchaseOrderSPPB, deskripsiBarang, merekBarang,
                                                                tipeBarang, jumlahRequest, jumlahPO,
                                                                keteranganDetail, attachmentBarang, isDeleted, deleteReason));
                                                    }
                                                }

                                                recyclerView = (RecyclerView) findViewById(R.id.recycle_sppbdetail);
                                                adapter = new Adapter_SPPB_Detail(SPPBDetailHolderList);
                                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SPPBDetailActivity.this);
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
                                    params.put("id", indexSPPB.toString());
                                    return params;
                                }
                            };
                            requestQueue.add(stringRequest);
                            if (pDialog.isShowing())
                                pDialog.dismiss();

                            //cari detail SPPB berakhir disini

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        builder.setTitle("Error");
                        builder.setMessage(error.toString());
                        builder.setPositiveButton("Oke",null);
                        AlertDialog dialog = builder.create();
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", indexSPPB.toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);

        ImageView btnback = (ImageView) findViewById(R.id.btnBack);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSPPB = new Intent(SPPBDetailActivity.this, SPPBActivity.class);
                startActivity(intentSPPB);
            }
        });

        Button btnApproval = (Button) findViewById(R.id.btnApproval);

        btnApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate( R.layout.activity_approval, null);

                Chip chipYes = (Chip) view.findViewById(R.id.chipYes);
                Chip chipNo = (Chip) view.findViewById(R.id.chipNo);
                EditText keterangan = (EditText) view.findViewById(R.id.txtKeteranganApproval);

                chipYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chipNo.setChecked(false);
                    }
                });

                chipNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chipYes.setChecked(false);
                    }
                });

                AlertDialog.Builder dialog = new AlertDialog.Builder( SPPBDetailActivity.this );
                dialog.setView(view);
                dialog.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences;

                        ProgressDialog pDialog2;
                        pDialog2 = new ProgressDialog(SPPBDetailActivity.this);
                        pDialog2.setMessage("Menyimpan data approval. Mohon tunggu...");
                        if (!pDialog2.isShowing()) {
                            pDialog2.show();
                            pDialog2.setCanceledOnTouchOutside(false);
                        }

                        sharedPreferences = getSharedPreferences("IPP_SINERGI_CREDENTIAL", MODE_PRIVATE);
                        String serverName = sharedPreferences.getString("serverAddress","");
                        String userIndex = sharedPreferences.getString("userIndex","");
                        String userName = sharedPreferences.getString("userName","");
                        String userDepartment = sharedPreferences.getString("userDepartment","");
                        String userEmail = sharedPreferences.getString("userEmail","");
                        String userToken = sharedPreferences.getString("tokenNotif", "");
                        String jawaban;

                        if(chipYes.isChecked()) {
                            jawaban = "1";
                        }
                        else {
                            jawaban = "2";
                        }

                        String postUrl = "http://"+serverName+"/ipp/sinergi/mobiles/saveapprovalsppb";
                        RequestQueue requestQueue = Volley.newRequestQueue(SPPBDetailActivity.this);

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (pDialog2.isShowing())
                                            pDialog2.dismiss();

                                        if (response.contains("OK")) {
                                            Toast toast = Toast.makeText(getApplicationContext(), "Approval anda berhasil disimpan", Toast.LENGTH_LONG);
                                            toast.show();

                                            //Intent intentSPPBList = new Intent(SPPBDetailActivity.this, SPPBActivity.class);
                                            //startActivity(intentSPPBList);
                                            finish();
                                        } else {
                                            Toast toast = Toast.makeText(getApplicationContext(), "Approval anda gagal disimpan. Silahkan coba beberapa saat lagi", Toast.LENGTH_SHORT);
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
                                params.put("nu", userName.toString());
                                params.put("nd", userDepartment.toString());
                                params.put("im", userEmail.toString());
                                params.put("ut", userToken.toString());
                                params.put("is", indexSPPB.toString());
                                params.put("jw", jawaban.toString());
                                params.put("kt", keterangan.toString());
                                return params;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                });
                dialog.setNeutralButton("Batal",null);
                dialog.show();
            }
        });
    }
}