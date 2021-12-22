package com.example.managestorephone.Product;

import java.io.Serializable;

public class Brand implements Serializable {

    private int mahang;
    private String tenhang;
    private String hinhAnhhang;

    public int getMahang() {
        return mahang;
    }

    public void setMahang(int mahang) {
        this.mahang = mahang;
    }

    public String getTenhang() {
        return tenhang;
    }

    public void setTenhang(String tenhang) {
        this.tenhang = tenhang;
    }

    public String getHinhAnhhang() {
        return hinhAnhhang;
    }

    public void setHinhAnhhang(String hinhAnhhang) {
        this.hinhAnhhang = hinhAnhhang;
    }

}
