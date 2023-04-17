package com.indoplat.sinergi;

import static android.content.Context.MODE_PRIVATE;

import static java.lang.ref.Cleaner.create;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class Adapter_SPPB_Detail extends RecyclerView.Adapter<Adapter_SPPB_Detail.SPPBDetailHolder> {
    private ArrayList<SPPB_Detail_Holder> dataList;

    public Adapter_SPPB_Detail(ArrayList<SPPB_Detail_Holder> dataList){
        this.dataList = dataList;
    }

    @Override
    public SPPBDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_sppb_detail, parent, false);
        return new SPPBDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(SPPBDetailHolder holder, int position){
        holder.lblNamaBarang.setText(dataList.get(position).getDeskripsiBarang());
        holder.lblMerekBarang.setText(dataList.get(position).getMerekBarang());
        holder.lblTipeBarang.setText(dataList.get(position).getTipeBarang());
        holder.lblJumlahMinta.setText(dataList.get(position).getJumlahRequest());
        holder.lblJumlahBeli.setText(dataList.get(position).getJumlahPO());
        holder.lblPrNumber.setText(dataList.get(position).getPurchaseRequestSPPB());
        holder.lblPoNumber.setText(dataList.get(position).getPurchaseOrderSPPB());
        holder.lblKeteranganDetailBarang.setText(dataList.get(position).getKeteranganDetail());

        //cek apakah item tersebut ada attachment atau tidak
        String attachment = dataList.get(position).getAttachmentBarang();
        if(!attachment.isEmpty()) {
            holder.btnAttachment.setVisibility(View.VISIBLE);
        }
        else {
            holder.btnAttachment.setVisibility(View.INVISIBLE);
        }

        //cek apakah item tersebut dihapus atau tidak
        String itemDeleted = dataList.get(position).getIsDeleted();
        if(itemDeleted.contains("1")){ //item barang dihapus oleh Purchasing
            holder.btnDeleteReason.setVisibility(View.VISIBLE);
            holder.layoutSPPBDetail.setBackgroundColor(Color.parseColor("#FFDFBF"));
        }
        else { //item barang tidak dihapus oleh Purchasing
            holder.btnDeleteReason.setVisibility(View.INVISIBLE);
            holder.layoutSPPBDetail.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        //button attachment diklik mulai disini
        holder.btnAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namafile = dataList.get(position).getAttachmentBarang();

                byte[] decodedString = Base64.decode(namafile, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                ImageView image = new ImageView(view.getContext());
                image.setImageBitmap(decodedByte);
                image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                image.invalidate();
                AlertDialog aDialog = new AlertDialog.Builder(view.getContext())
                        .setTitle("Attachment")
                        .setView(image)
                        .setPositiveButton("OK",null)
                        .create();
                        aDialog.show();
            }
        });
        //button attachment diklik selesai disini

        //button delete reason diklik mulai disini
        holder.btnDeleteReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String delReason = dataList.get(position).getDeleteReason();
                AlertDialog aDialog = new AlertDialog.Builder(view.getContext())
                        .setTitle("Alasan Hapus Item")
                        .setMessage(delReason.toString())
                        .setPositiveButton("OK",null)
                        .create();
                aDialog.show();
            }
        });
        //button delete reason diklik selesai disini

    }

    @Override
    public int getItemCount(){
        return (dataList != null) ? dataList.size() : 0;
    }

    public  class SPPBDetailHolder extends RecyclerView.ViewHolder{
        private TextView lblNamaBarang, lblMerekBarang, lblTipeBarang, lblJumlahMinta, lblJumlahBeli,
                lblPrNumber, lblPoNumber, lblKeteranganDetailBarang;
        private Button btnAttachment, btnDeleteReason;
        private LinearLayout layoutSPPBDetail;
        public SPPBDetailHolder(View itemView){
            super(itemView);
            lblNamaBarang = (TextView) itemView.findViewById(R.id.lblNamaBarang);
            lblMerekBarang = (TextView) itemView.findViewById(R.id.lblMerekBarang);
            lblTipeBarang = (TextView) itemView.findViewById(R.id.lblTipeBarang);
            lblJumlahMinta = (TextView) itemView.findViewById(R.id.lblJumlahMinta);
            lblJumlahBeli = (TextView) itemView.findViewById(R.id.lblJumlahBeli);
            lblPrNumber = (TextView) itemView.findViewById(R.id.lblPrNumber);
            lblPoNumber = (TextView) itemView.findViewById(R.id.lblPoNumber);
            lblKeteranganDetailBarang = (TextView) itemView.findViewById(R.id.lblKeteranganDetailBarang);
            btnAttachment = (Button) itemView.findViewById(R.id.btnAttachment);
            btnDeleteReason = (Button) itemView.findViewById(R.id.btnDelReason);
            layoutSPPBDetail = (LinearLayout) itemView.findViewById(R.id.layoutSPPBDetail);
        }
    }
}