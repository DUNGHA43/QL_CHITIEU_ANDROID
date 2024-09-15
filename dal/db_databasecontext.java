package com.hatiendung.quanlitaichinh.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class db_databasecontext extends SQLiteOpenHelper {
    private static String DB_NAME = "qlchitieu.db"; // Tên database
    private static String DB_PATH = "";
    private final Context context;
    private SQLiteDatabase database;

    public db_databasecontext(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        createDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    private void createDatabase(){
        if(!checkDatabase()){
            this.getReadableDatabase();
            try{
                copyDatabase();
                Log.d("Database_Helper", "Database copied");
            }catch (Exception e){
                Log.e("Database_Helper", "Err copying database!", e);
            }
        }
    }
    private boolean checkDatabase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDatabase() throws Exception{
        InputStream inputStream = context.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream outputStream = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0){
            outputStream.write(buffer, 0, length);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public SQLiteDatabase openDB(){
        String dbPath = DB_PATH + DB_NAME;
        if(database == null || !database.isOpen()){
            database = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
        }
        return database;
    }

    public void closeDB(){
        if(database != null){
            database.close();
        }
        super.close();
    }
}
