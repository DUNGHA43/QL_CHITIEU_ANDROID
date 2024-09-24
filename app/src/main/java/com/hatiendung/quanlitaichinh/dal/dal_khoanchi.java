package com.hatiendung.quanlitaichinh.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hatiendung.quanlitaichinh.dto.dto_KhoanChi;

public class dal_khoanchi {
    private Context context;
    private db_databasecontext db;
    SQLiteDatabase sql;
    private String query;
    private Cursor cursor;
    private ContentValues contentValues;

    public dal_khoanchi(Context context){
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

    public String getTaiKhoan_ten(String id){
        String data = "";
        try{
            query = String.format("SELECT * FROM tblTaiKhoan WHERE id = '%s'", id);
            cursor = sql.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do {
                    data = cursor.getString(1);
                }while (cursor.moveToNext());
            }
            return data;
        }catch (Exception e){
            return data;
        }
    }

    public int getDanhMuc_id(String loaichitieu){
        int data = -1;
        try{
            query = String.format("SELECT * FROM tblDanhMucChiTieu WHERE loaichitieu = '%s'", loaichitieu);
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

    public String getDanhMuc_ten(int id){
        String data = "";
        try{
            query = "SELECT * FROM tblDanhMucChiTieu WHERE id = "+ id;
            cursor = sql.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do {
                    data = cursor.getString(1);
                }while (cursor.moveToNext());
            }
            return data;
        }catch (Exception e){
            return data;
        }
    }

    public boolean add_khoanchi(dto_KhoanChi khoanchi){
        contentValues = new ContentValues();
        contentValues.put("taikhoan_id", khoanchi.getTaikhoan_id());
        contentValues.put("danhmuc_id", khoanchi.getDanhmuc_id());
        contentValues.put("sotien", khoanchi.getSotien());
        contentValues.put("ngay", khoanchi.getNgay());
        contentValues.put("mota", khoanchi.getMota());
        long check = sql.insert("tblKhoanChiTieu",null, contentValues);
        if(check == 0){
            return false;
        }else{
            return true;
        }
    }

    public boolean update_khoanchi(dto_KhoanChi khoanchi){
        contentValues = new ContentValues();
        contentValues.put("taikhoan_id", khoanchi.getTaikhoan_id());
        contentValues.put("danhmuc_id", khoanchi.getDanhmuc_id());
        contentValues.put("sotien", khoanchi.getSotien());
        contentValues.put("ngay", khoanchi.getNgay());
        contentValues.put("mota", khoanchi.getMota());
        long check = sql.update("tblKhoanChiTieu", contentValues, "id = ?", new String[] {String.valueOf(khoanchi.getId())});
        if (check == 0) {
            return false;
        }else{
            return true;
        }
    }

    public boolean delete_khoanchi(dto_KhoanChi khoanchi){
        long check = sql.delete("tblKhoanChiTieu", "id = ?", new String[] {String.valueOf(khoanchi.getId())});
        if (check == 0) {
            return false;
        }else{
            return true;
        }
    }

    public Cursor getAllKhoanChi(int taikhoan_id, String date, String fomat){
        query = "SELECT id, taikhoan_id, danhmuc_id, sotien,  ngay, mota FROM tblKhoanChiTieu\n" +
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
            return data - getSumKhoanChi(taikhoan_id);
        }catch (Exception e){
            return data;
        }
    }

    public int getChiPhi(int taikhoan_id,  String date, String fomat){
        int data = -1;
        try {
            query = "SELECT SUM(sotien) FROM tblKhoanChiTieu\n" +
                    "WHERE taikhoan_id = " + taikhoan_id + " AND strftime('" + fomat + "', substr(ngay, 7, 4) || '-' || substr(ngay, 4, 2) || '-' || substr(ngay, 1, 2)) = '" + date + "'";
            cursor = sql.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    data = cursor.getInt(0);
                } while (cursor.moveToNext());
            }
            return data;
        }catch (Exception e){
            return data;
        }
    }

    public float getSumChiPhiToday(int id, String ngay){
        float data = -1;
        try{
            query = "SELECT SUM(sotien) FROM tblKhoanChiTieu \n" +
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

    public Cursor getSumChiTieu_DanhMucToDay(int id, String ngay){
        query = "SELECT danhmuc_id, SUM(sotien) FROM tblKhoanChiTieu \n" +
                "WHERE taikhoan_id = "+id+" \n" +
                "AND substr(ngay, 7, 4) || '-' || substr(ngay, 4, 2) || '-' || substr(ngay, 1, 2) = '"+ngay+"'\n" +
                "GROUP BY danhmuc_id";
        return sql.rawQuery(query, null);
    }

    public Cursor getSumChiTieu_DanhMuc(int id, String date, String fomat){
        query = "SELECT danhmuc_id, SUM(sotien) FROM tblKhoanChiTieu\n" +
                "WHERE taikhoan_id = "+id+" \n" +
                "AND strftime('"+fomat+"', substr(ngay, 7, 4) || '-' || substr(ngay, 4, 2) || '-' || substr(ngay, 1, 2)) = '"+date+"'\n" +
                "GROUP BY danhmuc_id";
        return sql.rawQuery(query, null);
    }

    public float getSumChiTieu(int id, String date, String fomat){
        float data = -1;
        try{
            query = "SELECT SUM(sotien) FROM tblKhoanChiTieu\n" +
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
