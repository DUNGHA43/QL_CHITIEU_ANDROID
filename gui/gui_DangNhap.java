package com.hatiendung.quanlitaichinh.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hatiendung.quanlitaichinh.R;
import com.hatiendung.quanlitaichinh.bll.bll_taikhoan;

public class gui_DangNhap extends AppCompatActivity {
    TextView btnOpenDangKyTK, btnOpenQuenTKMK;
    Button btnDangNhap;
    EditText txtTaiKhoan, txtMatKhau;
    bll_taikhoan bll_taikhoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gui_dang_nhap);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        anhxa();
        bll_taikhoan = new bll_taikhoan(this);
        btnOpenDangKyTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(gui_DangNhap.this, gui_DangKy.class);
                startActivity(intent);
            }
        });
        btnOpenQuenTKMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(gui_DangNhap.this, gui_QuenMatKhau.class);
                startActivity(intent);
            }
        });
        btnDangNhap.setOnClickListener(v -> {
            String taikhoan = txtTaiKhoan.getText().toString().trim();
            String matkhau = txtMatKhau.getText().toString().trim();
            if(taikhoan.isEmpty() || bll_taikhoan.login(taikhoan).isEmpty()){
                Toast.makeText(this, "Không được để trống!", Toast.LENGTH_SHORT).show();
            }
            else if(matkhau.equals(bll_taikhoan.login(taikhoan))){
                Intent intent = new Intent(gui_DangNhap.this, gui_TrangChinh.class);
                startActivity(intent);
                Toast.makeText(this, bll_taikhoan.login(txtTaiKhoan.getText().toString()), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Sai mật khẩu!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void anhxa(){
        btnDangNhap = findViewById(R.id.btnDangNhap);
        txtTaiKhoan = findViewById(R.id.txtTaiKhoan);
        txtMatKhau = findViewById(R.id.txtMatKhau);
        btnOpenQuenTKMK = findViewById(R.id.btnOpenQuenMK);
        btnOpenDangKyTK = findViewById(R.id.btnOpenDangKy);
    }
}