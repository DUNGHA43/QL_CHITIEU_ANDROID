package com.hatiendung.quanlitaichinh.bll;

import android.content.Context;
import android.database.Cursor;

import com.hatiendung.quanlitaichinh.dal.dal_khoanchi;
import com.hatiendung.quanlitaichinh.dto.dto_KhoanChi;

public class bll_khoanchi {
    Context context;
    dal_khoanchi dal_khoanchi;

    public bll_khoanchi(Context context) {
        this.context = context;
        dal_khoanchi = new dal_khoanchi(context);
    }

    public int getTaiKhoan_id(String taikhoan){
        return dal_khoanchi.getTaiKhoan_id(taikhoan);
    }

    public String getTaiKhoan_ten(String id) { return  dal_khoanchi.getTaiKhoan_ten(id); }

    public int getDanhMuc_id(String loaichitieu){
        return dal_khoanchi.getDanhMuc_id(loaichitieu);
    }
    public boolean add_KhoanChi(dto_KhoanChi khoanChi){
        return dal_khoanchi.add_khoanchi(khoanChi);
    }
    public boolean update_khoanchi(dto_KhoanChi khoanchi){
        return dal_khoanchi.update_khoanchi(khoanchi);
    }
    public boolean delete_khoanchi(dto_KhoanChi khoanchi){
        return  dal_khoanchi.delete_khoanchi(khoanchi);
    }
    public Cursor getAllKhoanChi(int taikhoan_id, String date, String fomat){
        return dal_khoanchi.getAllKhoanChi(taikhoan_id, date, fomat);
    }
    public int getSumNganSach(int taikhoan_id){
        return dal_khoanchi.getSumNganSach(taikhoan_id);
    }
    public int getChiPhi(int taikhoan_id,  String date, String fomat){
        return dal_khoanchi.getChiPhi(taikhoan_id, date, fomat);
    }
    public float getSumChiPhiToday(int id, String ngay){ return dal_khoanchi.getSumChiPhiToday(id, ngay); }

    public String getDanhMuc_ten(int id){
        return dal_khoanchi.getDanhMuc_ten(id);
    }

    public Cursor getSumChiTieu_DanhMucToDay(int id, String ngay){ return dal_khoanchi.getSumChiTieu_DanhMucToDay(id, ngay); }

    public Cursor getSumChiTieu_DanhMuc(int id, String date, String fomat){
        return dal_khoanchi.getSumChiTieu_DanhMuc(id, date, fomat);
    }

    public float getSumChiTieu(int id, String date, String fomat){
        return dal_khoanchi.getSumChiTieu(id, date, fomat);
    }
}
