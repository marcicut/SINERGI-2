package com.indoplat.sinergi;

public class SPPB_List_Holder {
    private String indexSPPB;
    private String nomorSPPB;
    private String tanggalSPPB;
    private String namaPembuatSPPB;
    private String departemenSPPB;
    private String statusSPPB;

    public SPPB_List_Holder(String tanggal, String noref, String index, String nama, String departemen, String status){
        this.indexSPPB = index;
        this.nomorSPPB = noref;
        this.tanggalSPPB = tanggal;
        this.namaPembuatSPPB = nama;
        this.departemenSPPB = departemen;
        this.statusSPPB = status;
    }
    public String getIndexSPPB(){ return indexSPPB; }
    public void setIndexSPPB(String indexSPPB){
        this.indexSPPB = indexSPPB;
    }

    public String getNomorSPPB(){
        return nomorSPPB;
    }
    public void setNomorSPPB(String nomorSPPB){
        this.nomorSPPB = nomorSPPB;
    }

    public String getTanggalSPPB() {
        return tanggalSPPB;
    }
    public void setTanggalSPPB(String tanggalSPPB){
        this.tanggalSPPB = tanggalSPPB;
    }

    public String getNamaPembuatSPPB() {
        return namaPembuatSPPB;
    }
    public void setNamaPembuatSPPB(String namaPembuatSPPB){this.namaPembuatSPPB = namaPembuatSPPB;}

    public String getDepartemenSPPB() {
        return departemenSPPB;
    }
    public void setDepartemenSPPB(String departemenSPPB){this.departemenSPPB = departemenSPPB;}

    public String getStatusSPPB() {
        return statusSPPB;
    }
    public void setStatusSPPB(String statusSPPB){
        this.statusSPPB = statusSPPB;
    }
}