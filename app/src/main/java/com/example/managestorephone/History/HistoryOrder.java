package com.example.managestorephone.History;

import java.io.Serializable;

public class HistoryOrder implements Serializable {
    private int MaHD;
    private int MaKH;
    private String HoTen;
    private String SDT;
    private int DonGia;

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public int getMaHD() {
        return MaHD;
    }

    public void setMaHD(int maHD) {
        MaHD = maHD;
    }

    public int getMaKH() {
        return MaKH;
    }

    public void setMaKH(int maKH) {
        MaKH = maKH;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public int getDonGia() {
        return DonGia;
    }

    public void setDonGia(int donGia) {
        DonGia = donGia;
    }

    public String getNgayBan() {
        return NgayBan;
    }

    public void setNgayBan(String ngayBan) {
        NgayBan = ngayBan;
    }

    private String NgayBan;

}
