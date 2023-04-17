package com.indoplat.sinergi;

public class SPPB_Detail_Holder {
    private String indexItemSPPB;
    private String purchaseRequestSPPB;
    private String purchaseOrderSPPB;
    private String deskripsiBarang;
    private String merekBarang;
    private String tipeBarang;
    private String jumlahRequest;
    private String jumlahPO;
    private String keteranganDetail;
    private String attachmentBarang;
    private String isDeleted;
    private String deleteReason;

    public SPPB_Detail_Holder(String indexItemSPPB, String purchaseRequestSPPB, String purchaseOrderSPPB,
                              String deskripsiBarang, String merekBarang, String tipeBarang, String jumlahRequest,
                              String jumlahPO, String keteranganDetail, String attachmentBarang, String isDeleted,
                              String deleteReason){
        this.indexItemSPPB = indexItemSPPB;
        this.purchaseRequestSPPB = purchaseRequestSPPB;
        this.purchaseOrderSPPB = purchaseOrderSPPB;
        this.deskripsiBarang = deskripsiBarang;
        this.merekBarang = merekBarang;
        this.tipeBarang = tipeBarang;
        this.jumlahRequest = jumlahRequest;
        this.jumlahPO = jumlahPO;
        this.keteranganDetail = keteranganDetail;
        this.attachmentBarang = attachmentBarang;
        this.isDeleted = isDeleted;
        this.deleteReason = deleteReason;
    }
    public String getIndexItemSPPB(){ return indexItemSPPB; }
    public void setIndexItemSPPB(String indexItemSPPB){
        this.indexItemSPPB = indexItemSPPB;
    }

    public String getPurchaseRequestSPPB(){
        return purchaseRequestSPPB;
    }
    public void setPurchaseRequestSPPB(String purchaseRequestSPPB){
        this.purchaseRequestSPPB = purchaseRequestSPPB;
    }

    public String getPurchaseOrderSPPB() {
        return purchaseOrderSPPB;
    }
    public void setPurchaseOrderSPPB(String purchaseOrderSPPB){
        this.purchaseOrderSPPB = purchaseOrderSPPB;
    }

    public String getDeskripsiBarang() {
        return deskripsiBarang;
    }
    public void setDeskripsiBarang(String deskripsiBarang){this.deskripsiBarang = deskripsiBarang;}

    public String getMerekBarang() {
        return merekBarang;
    }
    public void setMerekBarang(String merekBarang){this.merekBarang = merekBarang;}

    public String getTipeBarang() {
        return tipeBarang;
    }
    public void setTipeBarang(String tipeBarang){
        this.tipeBarang = tipeBarang;
    }

    public String getJumlahRequest() {
        return jumlahRequest;
    }
    public void setJumlahRequest(String jumlahRequest){
        this.jumlahRequest = jumlahRequest;
    }

    public String getJumlahPO() {
        return jumlahPO;
    }
    public void setJumlahPO(String jumlahPO){
        this.jumlahPO = jumlahPO;
    }

    public String getKeteranganDetail() {
        return keteranganDetail;
    }
    public void setKeteranganDetail(String keteranganDetail){
        this.keteranganDetail = keteranganDetail;
    }

    public String getAttachmentBarang() {
        return attachmentBarang;
    }
    public void setAttachmentBarang(String attachmentBarang){
        this.attachmentBarang = attachmentBarang;
    }

    public String getIsDeleted() {
        return isDeleted;
    }
    public void setIsDeleted(String isDeleted){
        this.isDeleted = isDeleted;
    }

    public String getDeleteReason() {
        return deleteReason;
    }
    public void setDeleteReason(String deleteReason){
        this.deleteReason = deleteReason;
    }
}