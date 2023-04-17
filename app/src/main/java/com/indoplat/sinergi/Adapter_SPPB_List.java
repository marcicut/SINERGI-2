package com.indoplat.sinergi;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class Adapter_SPPB_List extends RecyclerView.Adapter<Adapter_SPPB_List.SPPBListHolder> {
    private ArrayList<SPPB_List_Holder> dataList;

    public Adapter_SPPB_List(ArrayList<SPPB_List_Holder> dataList){
        this.dataList = dataList;
    }

    @Override
    public SPPBListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_sppb_list, parent, false);
        return new SPPBListHolder(view);
    }

    @Override
    public void onBindViewHolder(SPPBListHolder holder, int position){
        holder.lblTanggalSPPB.setText(dataList.get(position).getTanggalSPPB());
        holder.lblNomorSPPB.setText(dataList.get(position).getNomorSPPB());
        holder.lblNamaPembuatSPPB.setText(dataList.get(position).getNamaPembuatSPPB());
        holder.lblDepartemenSPPB.setText(dataList.get(position).getDepartemenSPPB());
        holder.lblStatusSPPB.setText(dataList.get(position).getStatusSPPB());

        if(dataList.get(position).getStatusSPPB().contains("0")){
            //menunggu approval
            holder.lblStatusSPPB.setTextColor(Color.parseColor("#FFFFFF"));
            holder.lblStatusSPPB.setText("  MENUNGGU APPROVAL  ");
            holder.lblStatusSPPB.setBackgroundResource(R.drawable.blue_border);
        }
        else
        if(dataList.get(position).getStatusSPPB().contains("1")){
            //disetujui
            holder.lblStatusSPPB.setTextColor(Color.parseColor("#000000"));
            holder.lblStatusSPPB.setText("  DISETUJUI  ");
            holder.lblStatusSPPB.setBackgroundResource(R.drawable.green_border);
        }
        else
        if(dataList.get(position).getStatusSPPB().contains("2")){
            //ditolak
            holder.lblStatusSPPB.setTextColor(Color.parseColor("#FFFFFF"));
            holder.lblStatusSPPB.setText("  DITOLAK  ");
            holder.lblStatusSPPB.setBackgroundResource(R.drawable.red_border);
        }
        else
        if(dataList.get(position).getStatusSPPB().contains("4")){
            //ditutup oleh Purchasing
            holder.lblStatusSPPB.setTextColor(Color.parseColor("#CCCCCC"));
            holder.lblStatusSPPB.setText("  DITUTUP  ");
            holder.lblStatusSPPB.setBackgroundResource(R.drawable.grey_border);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String indexSPPB;
                indexSPPB = dataList.get(position).getIndexSPPB();

                Intent intentDetailSPPB;
                intentDetailSPPB = new Intent(view.getContext(), SPPBDetailActivity.class);
                intentDetailSPPB.putExtra("indexSPPB",indexSPPB.toString());
                view.getContext().startActivity(intentDetailSPPB);
            }
        });
    }

    @Override
    public int getItemCount(){
        return (dataList != null) ? dataList.size() : 0;
    }

    public  class SPPBListHolder extends RecyclerView.ViewHolder{
        private TextView lblNomorSPPB, lblTanggalSPPB, lblNamaPembuatSPPB, lblDepartemenSPPB, lblStatusSPPB;
        public SPPBListHolder(View itemView){
            super(itemView);
            lblNomorSPPB = (TextView) itemView.findViewById(R.id.lblNomorSPPB);
            lblTanggalSPPB = (TextView) itemView.findViewById(R.id.lblTanggalSPPB);
            lblNamaPembuatSPPB = (TextView) itemView.findViewById(R.id.lblPembuatSPPB);
            lblDepartemenSPPB = (TextView) itemView.findViewById(R.id.lblDepartemenPembuatSPPB);
            lblStatusSPPB = (TextView) itemView.findViewById(R.id.lblStatusSPPB);
        }
    }
}