package com.example.navigationdrawer.model;

public class LoaiThu {
    private String idLoaiThu;
    private String titleLoaiThu;
    private String dateLoaiThu;
    private String idUserLoaiThu;

    public LoaiThu(String idLoaiThu, String titleLoaiThu, String dateLoaiThu, String idUserLoaiThu) {
        this.idLoaiThu = idLoaiThu;
        this.titleLoaiThu = titleLoaiThu;
        this.dateLoaiThu = dateLoaiThu;
        this.idUserLoaiThu = idUserLoaiThu;
    }

    public LoaiThu() {
    }

    public String getIdLoaiThu() {
        return idLoaiThu;
    }

    public void setIdLoaiThu(String idLoaiThu) {
        this.idLoaiThu = idLoaiThu;
    }

    public String getTitleLoaiThu() {
        return titleLoaiThu;
    }

    public void setTitleLoaiThu(String titleLoaiThu) {
        this.titleLoaiThu = titleLoaiThu;
    }

    public String getDateLoaiThu() {
        return dateLoaiThu;
    }

    public void setDateLoaiThu(String dateLoaiThu) {
        this.dateLoaiThu = dateLoaiThu;
    }

    public String getIdUserLoaiThu() {
        return idUserLoaiThu;
    }

    public void setIdUserLoaiThu(String idUserLoaiThu) {
        this.idUserLoaiThu = idUserLoaiThu;
    }
}
