package com.hatiendung.quanlitaichinh.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.hatiendung.quanlitaichinh.dto.dto_TaiKhoan;

public class dal_taikhoan
{
    private Context context;
    private db_databasecontext db;
    SQLiteDatabase sql;
    private String query;
    private Cursor cursor;
    private ContentValues contentValues;
    public dal_taikhoan(Context context) {
        this.context = context;
        db = new db_databasecontext(context);
        sql = db.openDB();
    }

    public String loginApp(String taikhoan){
        String data = "";
        try{
            query = String.format("SELECT * FROM tblTaiKhoan WHERE taikhoan = '%s'", taikhoan);
            cursor = sql.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do {
                    data = cursor.getString(2);
                }while (cursor.moveToNext());
            }
            return data;
        }catch (Exception e){
            return data;
        }
    }

    public String checkRegisteredAcc(String taikhoan){
        String data = "";
        try{
            query = String.format("SELECT * FROM tblTaiKhoan WHERE taikhoan = '%s'", taikhoan);
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

    public String checkRegisteredGmail(String gmail){
        String data = "";
        try{
            query = String.format("SELECT * FROM tblTaiKhoan WHERE gmail = '%s'", gmail);
            cursor = sql.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do {
                    data = cursor.getString(3);
                }while (cursor.moveToNext());
            }
            return data;
        }catch (Exception e){
            return data;
        }
    }

    public String searchAccountWithGmail(String gmail){
        String data = "";
        query = String.format("SELECT * FROM tblTaiKhoan WHERE gmail = '%s'", gmail);
        cursor = sql.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                data = cursor.getString(1);
            }while (cursor.moveToNext());
        }
        return data;
    }

    public boolean registerAcc(dto_TaiKhoan dtoTaiKhoan){
        contentValues = new ContentValues();
        contentValues.put("taikhoan",dtoTaiKhoan.getTaikhoan());
        contentValues.put("matkhau",dtoTaiKhoan.getMatkhau());
        contentValues.put("gmail",dtoTaiKhoan.getGmail());
        long check = sql.insert("tblTaiKhoan",null, contentValues);
        if(check == 0){
            return false;
        }else{
            return true;
        }
    }

    public boolean changePass(String matkhaumoi, String taikhoan){
        contentValues = new ContentValues();
        contentValues.put("matkhau", matkhaumoi);
        int check = sql.update("tblTaiKhoan", contentValues, "taikhoan = ?", new String[] {taikhoan});
        if(check == 0){
            return false;
        }else
        {
            return true;
        }
    }
}
