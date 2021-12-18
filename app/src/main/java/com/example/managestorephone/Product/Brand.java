package com.example.managestorephone.Product;

public class Brand {

    private String tenhang;
    private String hinhAnhhang;

    public Brand() {
    }

    public Brand(String tenhang, String hinhAnhhang) {
        this.tenhang = tenhang;
        this.hinhAnhhang = hinhAnhhang;
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
