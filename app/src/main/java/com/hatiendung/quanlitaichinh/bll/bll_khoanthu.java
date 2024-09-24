package com.hatiendung.quanlitaichinh.bll;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.hatiendung.quanlitaichinh.dal.dal_khoanthu;
import com.hatiendung.quanlitaichinh.dto.dto_KhoanThu;

public class bll_khoanthu {
    Context context;
    dal_khoanthu dal_khoanthu;
    public bll_khoanthu(Context context){
        this.context = context;
        dal_khoanthu = new dal_khoanthu(context);
    }
    public int getTaiKhoan_id(String taikhoan){
        return dal_khoanthu.getTaiKhoan_id(taikhoan);
    }

    public boolean add_khoanthu(dto_KhoanThu khoanThu){
        return dal_khoanthu.add_khoanthu(khoanThu);
    }

    public boolean update_khoanthu(dto_KhoanThu khoanThu){
        return dal_khoanthu.update_khoanthu(khoanThu);
    }

    public boolean delete_khoanthu(dto_KhoanThu khoanThu){
        return dal_khoanthu.delete_khoanthu(khoanThu);
    }

    public Cursor getAllKhoanChi(int taikhoan_id, String date, String fomat){
        return dal_khoanthu.getAllKhoanChi(taikhoan_id, date, fomat);
    }

    public int getNganSachHT(int taikhoan_id){
        return dal_khoanthu.getNganSachHT(taikhoan_id);
    }

    public int getSumNganSach(int taikhoan_id){
        return dal_khoanthu.getSumNganSach(taikhoan_id);
    }

    public float getSumNganSachToday(int id, String ngay) { return dal_khoanthu.getSumNganSachToday(id, ngay); }

    public float getSumKhoanThu(int id, String date, String fomat){
        return dal_khoanthu.getSumKhoanThu(id, date, fomat);
    }
}
