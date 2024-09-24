package com.hatiendung.quanlitaichinh.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.hatiendung.quanlitaichinh.dto.dto_KhoanThu;

public class dal_khoanthu {
    private Context context;
    private db_databasecontext db;
    SQLiteDatabase sql;
    private String query;
    private Cursor cursor;
    private ContentValues contentValues;

    public dal_khoanthu(Context context){
        this.context = context;
        db = new db_databasecontext(context);
        sql = db.openDB();
    }

    public int getTaiKhoan_id(String taikhoan){
        int data = -1;
        try{
            query = String.format("SELECT * FROM tblTaiKhoan WHERE taikhoan = '%s'", taikhoan);
            cursor = sql.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do {
                    data = cursor.getInt(0);
                }while (cursor.moveToNext());
            }
            return data;
        }catch (Exception e){
            return data;
        }
    }

    public boolean add_khoanthu(dto_KhoanThu khoanThu){
        contentValues = new ContentValues();
        contentValues.put("taikhoan_id", khoanThu.getTaikhoan_id());
        contentValues.put("sotien", khoanThu.getSotien());
        contentValues.put("nguonthu", khoanThu.getNguonthu());
        contentValues.put("ngay", khoanThu.getNgay());
        contentValues.put("mota", khoanThu.getMota());
        long check = sql.insert("tblThuNhap",null, contentValues);
        if(check == 0){
            return false;
        }else{
            return true;
        }
    }

    public boolean update_khoanthu(dto_KhoanThu khoanThu){
        contentValues = new ContentValues();
        contentValues.put("taikhoan_id", khoanThu.getTaikhoan_id());
        contentValues.put("sotien", khoanThu.getSotien());
        contentValues.put("nguonthu", khoanThu.getNguonthu());
        contentValues.put("ngay", khoanThu.getNgay());
        contentValues.put("mota", khoanThu.getMota());
        long check = sql.update("tblThuNhap", contentValues, "id = ?", new String[] {String.valueOf(khoanThu.getId())});
        if(check == 0){
            return false;
        }else{
            return true;
        }
    }

    public boolean delete_khoanthu(dto_KhoanThu khoanThu){
        long check = sql.delete("tblThuNhap", "id = ?", new String[] {String.valueOf(khoanThu.getId())});
        if(check == 0){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getAllKhoanChi(int taikhoan_id, String date, String fomat){
        query = "SELECT id, taikhoan_id, sotien, nguonthu, ngay, mota FROM tblThuNhap\n" +
                "WHERE taikhoan_id = "+taikhoan_id+" AND strftime('"+fomat+"', substr(ngay, 7, 4) || '-' || substr(ngay, 4, 2) || '-' || substr(ngay, 1, 2)) = '"+date+"'";
        return sql.rawQuery(query,null);
    }

    private int getSumKhoanChi(int taikhoan_id){
        int data = -1;
        try{
            query = "SELECT SUM(sotien) FROM tblKhoanChiTieu WHERE taikhoan_id = "+taikhoan_id;
            cursor = sql.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do {
                    data = cursor.getInt(0);
                }while (cursor.moveToNext());
            }
            return data;
        }catch (Exception e){
            return data;
        }
    }

    public int getNganSachHT(int taikhoan_id){
        int data = -1;
        try{
            query = "SELECT SUM(sotien) FROM tblThuNhap WHERE taikhoan_id = "+taikhoan_id;
            cursor = sql.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do {
                    data = cursor.getInt(0);
                }while (cursor.moveToNext());
            }
            return data - getSumKhoanChi(taikhoan_id);
        }catch (Exception e){
            return data;
        }
    }

    public int getSumNganSach(int taikhoan_id){
        int data = -1;
        try{
            query = "SELECT SUM(sotien) FROM tblThuNhap WHERE taikhoan_id = "+taikhoan_id;
            cursor = sql.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do {
                    data = cursor.getInt(0);
                }while (cursor.moveToNext());
            }
            return data;
        }catch (Exception e){
            return data;
        }
    }

    public float getSumNganSachToday(int id, String ngay){
        float data = -1;
        try{
            query = "SELECT SUM(sotien) FROM tblThuNhap \n" +
                    "WHERE taikhoan_id = "+id+" \n" +
                    "AND substr(ngay, 7, 4) || '-' || substr(ngay, 4, 2) || '-' || substr(ngay, 1, 2) = '"+ngay+"'";
            cursor = sql.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do {
                    data = cursor.getInt(0);
                }while (cursor.moveToNext());
            }
            return data;
        }catch (Exception e){
            return data;
        }
    }

    public float getSumKhoanThu(int id, String date, String fomat){
        float data = -1;
        try{
            query = "SELECT SUM(sotien) FROM tblThuNhap\n" +
                    "WHERE taikhoan_id = "+id+" \n" +
                    "AND strftime('"+fomat+"', substr(ngay, 7, 4) || '-' || substr(ngay, 4, 2) || '-' || substr(ngay, 1, 2)) = '"+date+"'";
            cursor = sql.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do {
                    data = cursor.getInt(0);
                }while (cursor.moveToNext());
            }
            return data;
        }catch (Exception e){
            return data;
        }
    }
}
