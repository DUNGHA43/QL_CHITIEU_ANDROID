package com.hatiendung.quanlitaichinh.dto;

public class dto_KhoanChi {
    private int id;
    private int taikhoan_id;
    private int danhmuc_id;
    private double sotien;
    private String ngay;
    private String mota;

    public dto_KhoanChi() {
    }

    public dto_KhoanChi(int id, int taikhoan_id, int danhmuc_id, double sotien, String ngay, String mota) {
        this.id = id;
        this.taikhoan_id = taikhoan_id;
        this.danhmuc_id = danhmuc_id;
        this.sotien = sotien;
        this.ngay = ngay;
        this.mota = mota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaikhoan_id() {
        return taikhoan_id;
    }

    public void setTaikhoan_id(int taikhoan_id) {
        this.taikhoan_id = taikhoan_id;
    }

    public int getDanhmuc_id() {
        return danhmuc_id;
    }

    public void setDanhmuc_id(int danhmuc_id) {
        this.danhmuc_id = danhmuc_id;
    }

    public double getSotien() {
        return sotien;
    }

    public void setSotien(double sotien) {
        this.sotien = sotien;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    @Override
    public String toString() {
        return "dto_KhoanChi{" +
                "taikhoan_id='" + taikhoan_id + '\'' +
                ", danhmuc_id='" + danhmuc_id + '\'' +
                ", sotien=" + sotien +
                ", ngay='" + ngay + '\'' +
                ", mota='" + mota + '\'' +
                '}';
    }
}
