package com.example.managestorephone.History;

import java.io.Serializable;

public class HistoryOrderProduct {
    private int MaHD;
    private int MaSP;
    private int SoLuong;
    private String TenSP;
    private String HinhAnh;

    private int GiaTong;

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public int getMaHD() {
        return MaHD;
    }

    public void setMaHD(int maHD) {
        MaHD = maHD;
    }

    public int getMaSP() {
        return MaSP;
    }

    public void setMaSP(int maSP) {
        MaSP = maSP;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public int getGiaTong() {
        return GiaTong;
    }

    public void setGiaTong(int giaTong) {
        GiaTong = giaTong;
    }
}
