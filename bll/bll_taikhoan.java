package com.hatiendung.quanlitaichinh.bll;

import android.content.Context;

import com.hatiendung.quanlitaichinh.dal.dal_taikhoan;
import com.hatiendung.quanlitaichinh.dto.dto_TaiKhoan;

public class bll_taikhoan {
    Context context;
    dal_taikhoan dal_taikhoan;

    public bll_taikhoan(Context context) {
        this.context = context;
        dal_taikhoan = new dal_taikhoan(context);
    }
    public String login(String taikhoan){
        return dal_taikhoan.loginApp(taikhoan);
    }
    public String searchAccountWithGmail(String gmail) { return dal_taikhoan.searchAccountWithGmail(gmail); }
    public boolean changePass(String matkhaumoi, String taikhoan) { return dal_taikhoan.changePass(matkhaumoi, taikhoan); }
    public boolean registerAcc(dto_TaiKhoan taikhoan) { return dal_taikhoan.registerAcc(taikhoan); }
    public String checkRegisteredAcc(String taikhoan){ return dal_taikhoan.checkRegisteredAcc(taikhoan); }
    public String checkRegisteredGmail(String gmail){ return dal_taikhoan.checkRegisteredGmail(gmail); }
}
