package com.example.navigationdrawer.model;

public class LoaiChi {
   private String idLoaiChi;
   private String titleLoaiChi;
   private String dateLoaiChi;
   private String idUserLoaiChi;

    public LoaiChi() {
    }

    public LoaiChi(String idLoaiChi, String titleLoaiChi, String dateLoaiChi, String idUserLoaiChi) {
        this.idLoaiChi = idLoaiChi;
        this.titleLoaiChi = titleLoaiChi;
        this.dateLoaiChi = dateLoaiChi;
        this.idUserLoaiChi = idUserLoaiChi;
    }

    public String getIdLoaiChi() {
        return idLoaiChi;
    }

    public void setIdLoaiChi(String idLoaiChi) {
        this.idLoaiChi = idLoaiChi;
    }

    public String getTitleLoaiChi() {
        return titleLoaiChi;
    }

    public void setTitleLoaiChi(String titleLoaiChi) {
        this.titleLoaiChi = titleLoaiChi;
    }

    public String getDateLoaiChi() {
        return dateLoaiChi;
    }

    public void setDateLoaiChi(String dateLoaiChi) {
        this.dateLoaiChi = dateLoaiChi;
    }

    public String getIdUserLoaiChi() {
        return idUserLoaiChi;
    }

    public void setIdUserLoaiChi(String idUserLoaiChi) {
        this.idUserLoaiChi = idUserLoaiChi;
    }
}
