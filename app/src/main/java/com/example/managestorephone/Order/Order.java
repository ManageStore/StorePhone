package com.example.managestorephone.Order;

public class Order {
    private int maSP;
    private String tenSP;
    private String hinhAnh;
    private int soluong;
    private int giasp;
    private int tong;

//    public Order(int maSP, String tenSP, String hinhAnh, int soluong, int giasp) {
//        this.maSP = maSP;

    public int getTong() {
        return tong;
    }

    public void setTong(int tong) {
        this.tong = tong;
    }
//        this.tenSP = tenSP;
//        this.hinhAnh = hinhAnh;
//        this.soluong = soluong;
//        this.giasp = giasp;
//    }


    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getGiaSP() {
        return giasp;
    }

    public void setGiaSP(int giasp) {
        this.giasp = giasp;
    }
}
