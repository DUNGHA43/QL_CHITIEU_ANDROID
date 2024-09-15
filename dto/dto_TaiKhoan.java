package com.hatiendung.quanlitaichinh.dto;

public class dto_TaiKhoan {
    private String taikhoan;
    private String matkhau;
    private String gmail;

    public dto_TaiKhoan(String taikhoan, String matkhau, String gmail) {
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
        this.gmail = gmail;
    }

    public dto_TaiKhoan() {
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    @Override
    public String toString() {
        return "dto_TaiKhoan{" +
                "gmail='" + gmail + '\'' +
                ", matkhau='" + matkhau + '\'' +
                ", taikhoan='" + taikhoan + '\'' +
                '}';
    }
}
