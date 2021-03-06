package com.example.navigationdrawer.model;

public class KhoanThu {
    private String idKhoanThu;
    private String titleKhoanThu;
    private String oldTitle;
    private int moneyKhoanThu;
    private String dateKhoanThu;
    private String idUserKhoanThu;

    public KhoanThu() {
    }

    public KhoanThu(String idKhoanThu, String titleKhoanThu, String oldTitle, int moneyKhoanThu, String dateKhoanThu, String idUserKhoanThu) {
        this.idKhoanThu = idKhoanThu;
        this.titleKhoanThu = titleKhoanThu;
        this.oldTitle = oldTitle;
        this.moneyKhoanThu = moneyKhoanThu;
        this.dateKhoanThu = dateKhoanThu;
        this.idUserKhoanThu = idUserKhoanThu;
    }

    public String getIdKhoanThu() {
        return idKhoanThu;
    }

    public void setIdKhoanThu(String idKhoanThu) {
        this.idKhoanThu = idKhoanThu;
    }

    public String getTitleKhoanThu() {
        return titleKhoanThu;
    }

    public void setTitleKhoanThu(String titleKhoanThu) {
        this.titleKhoanThu = titleKhoanThu;
    }

    public String getOldTitle() {
        return oldTitle;
    }

    public void setOldTitle(String oldTitle) {
        this.oldTitle = oldTitle;
    }

    public int getMoneyKhoanThu() {
        return moneyKhoanThu;
    }

    public void setMoneyKhoanThu(int moneyKhoanThu) {
        this.moneyKhoanThu = moneyKhoanThu;
    }

    public String getDateKhoanThu() {
        return dateKhoanThu;
    }

    public void setDateKhoanThu(String dateKhoanThu) {
        this.dateKhoanThu = dateKhoanThu;
    }

    public String getIdUserKhoanThu() {
        return idUserKhoanThu;
    }

    public void setIdUserKhoanThu(String idUserKhoanThu) {
        this.idUserKhoanThu = idUserKhoanThu;
    }
}
