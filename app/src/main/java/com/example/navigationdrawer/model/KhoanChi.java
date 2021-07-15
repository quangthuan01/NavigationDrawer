package com.example.navigationdrawer.model;

public class KhoanChi {
    private String idKhoanChi;
    private String titleKhoanChi;
    private String moneyKhoanChi;
    private String dateKhoanChi;

    public KhoanChi() {
    }

    public KhoanChi(String idKhoanChi, String titleKhoanChi, String moneyKhoanChi, String dateKhoanChi) {
        this.idKhoanChi = idKhoanChi;
        this.titleKhoanChi = titleKhoanChi;
        this.moneyKhoanChi = moneyKhoanChi;
        this.dateKhoanChi = dateKhoanChi;
    }

    public String getIdKhoanChi() {
        return idKhoanChi;
    }

    public void setIdKhoanChi(String idKhoanChi) {
        this.idKhoanChi = idKhoanChi;
    }

    public String getTitleKhoanChi() {
        return titleKhoanChi;
    }

    public void setTitleKhoanChi(String titleKhoanChi) {
        this.titleKhoanChi = titleKhoanChi;
    }

    public String getMoneyKhoanChi() {
        return moneyKhoanChi;
    }

    public void setMoneyKhoanChi(String moneyKhoanChi) {
        this.moneyKhoanChi = moneyKhoanChi;
    }

    public String getDateKhoanChi() {
        return dateKhoanChi;
    }

    public void setDateKhoanChi(String dateKhoanChi) {
        this.dateKhoanChi = dateKhoanChi;
    }
}
