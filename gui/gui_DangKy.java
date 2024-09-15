package com.hatiendung.quanlitaichinh.gui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.hatiendung.quanlitaichinh.bll.bll_gmailxacthuc;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hatiendung.quanlitaichinh.R;
import com.hatiendung.quanlitaichinh.bll.bll_taikhoan;
import com.hatiendung.quanlitaichinh.dto.dto_TaiKhoan;

import javax.mail.MessagingException;

public class gui_DangKy extends AppCompatActivity {
    TextView btnQuayLaiDangNhap;
    EditText txtTaiKhoanDK, txtMatKhauDK, txtGmailDK, txtMaXacThuc;
    Button btnGuiMaXacThucDK, btnDangKyTK;
    bll_gmailxacthuc gmailxacthuc = new bll_gmailxacthuc();
    bll_taikhoan bllTaikhoan;
    dto_TaiKhoan taiKhoan = new dto_TaiKhoan();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gui_dang_ky);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        anhxaid();
        bllTaikhoan = new bll_taikhoan(this);
        txtMaXacThuc.setEnabled(false);
        btnDangKyTK.setEnabled(false);


        btnQuayLaiDangNhap.setOnClickListener(v -> {
            finish();
        });

        btnGuiMaXacThucDK.setOnClickListener(v -> {
            String taikhoan = txtTaiKhoanDK.getText().toString().trim();
            String matkhau = txtMatKhauDK.getText().toString().trim();
            String gmail = txtGmailDK.getText().toString().trim();
            if(taikhoan.isEmpty() || matkhau.isEmpty() || gmail.isEmpty()){
                Toast.makeText(gui_DangKy.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            } else if (gmailxacthuc.isGmail(gmail) == false) {
                Toast.makeText(gui_DangKy.this, "Địa chỉ gmail không hợp lệ!", Toast.LENGTH_SHORT).show();
            }else if(taikhoan.equals(bllTaikhoan.checkRegisteredAcc(taikhoan))){
                Toast.makeText(this, "Tài khoản này đã tồn tại!", Toast.LENGTH_SHORT).show();
            }else if(gmail.equals(bllTaikhoan.checkRegisteredGmail(gmail))){
                Toast.makeText(this, "Gmail này đã tồn tại!", Toast.LENGTH_SHORT).show();
            }else{
                try{
                    gmailxacthuc.sendMail(gmail);
                    Toast.makeText(gui_DangKy.this, "Đã gửi mã xác minh đến gmail!", Toast.LENGTH_SHORT).show();
                    txtMaXacThuc.setEnabled(true);
                    btnDangKyTK.setEnabled(true);
                }
                catch (Exception e){
                    Toast.makeText(gui_DangKy.this, "Gửi gmail thất bại! Thử lại sau.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDangKyTK.setOnClickListener(v -> {
            taiKhoan.setTaikhoan(txtTaiKhoanDK.getText().toString().trim());
            taiKhoan.setMatkhau(txtMatKhauDK.getText().toString().trim());
            taiKhoan.setGmail(txtGmailDK.getText().toString().trim());
            if(gmailxacthuc.getMa().equals(txtMaXacThuc.getText().toString())){
                bllTaikhoan.registerAcc(taiKhoan);
                Toast.makeText(gui_DangKy.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(gui_DangKy.this, "Đăng ký thất bại! Thử lại sau.", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void anhxaid(){
        btnQuayLaiDangNhap = findViewById(R.id.btnQuayLaiDangNhap);
        txtTaiKhoanDK = findViewById(R.id.txtTaiKhoanDK);
        txtMatKhauDK = findViewById(R.id.txtMatKhauDK);
        txtGmailDK = findViewById(R.id.txtGmailDK);
        txtMaXacThuc = findViewById(R.id.txtMaXacThucDK);
        btnGuiMaXacThucDK = findViewById(R.id.btnGuiMaXacThucDK);
        btnDangKyTK = findViewById(R.id.btnDangKyTK);
    }
}