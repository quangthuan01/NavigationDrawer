package com.example.navigationdrawer.model;

public class KhoanChi {
    private String idKhoanChi;
    private String titleKhoanChi;
    private String oldKhoanChi;
    private String moneyKhoanChi;
    private String dateKhoanChi;
    private String idUserKhoanChi;

    public KhoanChi() {
    }

    public KhoanChi(String idKhoanChi, String titleKhoanChi, String oldKhoanChi, String moneyKhoanChi, String dateKhoanChi, String idUserKhoanChi) {
        this.idKhoanChi = idKhoanChi;
        this.titleKhoanChi = titleKhoanChi;
        this.oldKhoanChi = oldKhoanChi;
        this.moneyKhoanChi = moneyKhoanChi;
        this.dateKhoanChi = dateKhoanChi;
        this.idUserKhoanChi = idUserKhoanChi;
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

    public String getOldKhoanChi() {
        return oldKhoanChi;
    }

    public void setOldKhoanChi(String oldKhoanChi) {
        this.oldKhoanChi = oldKhoanChi;
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

    public String getIdUserKhoanChi() {
        return idUserKhoanChi;
    }

    public void setIdUserKhoanChi(String idUserKhoanChi) {
        this.idUserKhoanChi = idUserKhoanChi;
    }
}
