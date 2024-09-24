package com.hatiendung.quanlitaichinh.gui;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.CheckBox;
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

public class gui_DoiMatKhau extends AppCompatActivity {
    EditText txtMatKhauMoi, txtXNMatKhauMoi;
    CheckBox ckbCheckShowHideMK;
    Button btnDoiMatKhau;
    TextView btnQuayLaiTrangDangNhap;
    Intent intent;
    boolean check = true;
    String data = "";
    bll_taikhoan bllTaikhoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gui_doi_mat_khau);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        anhxa();
        bllTaikhoan = new bll_taikhoan(this);
        intent = getIntent();
        data = intent.getStringExtra("taikhoan");
        ckbCheckShowHideMK.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                txtMatKhauMoi.setInputType(InputType.TYPE_CLASS_TEXT);
                txtXNMatKhauMoi.setInputType(InputType.TYPE_CLASS_TEXT);
            }
            else{
                txtMatKhauMoi.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                txtXNMatKhauMoi.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });

        btnDoiMatKhau.setOnClickListener(v -> {
            if(txtMatKhauMoi.getText().toString().isEmpty() || txtXNMatKhauMoi.getText().toString().isEmpty()){
                Toast.makeText(this, "Không được để trống!", Toast.LENGTH_SHORT).show();
            }
            else if(txtXNMatKhauMoi.getText().toString().equals(txtMatKhauMoi.getText().toString()))
            {
                if(bllTaikhoan.changePass(txtXNMatKhauMoi.getText().toString(), data) == true){
                    Toast.makeText(this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Đổi mật khẩu không thành công!", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Mật khẩu không trùng nhau!", Toast.LENGTH_SHORT).show();
            }
        });

        btnQuayLaiTrangDangNhap.setOnClickListener(v -> {
            Intent intent = new Intent(gui_DoiMatKhau.this, gui_DangNhap.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void anhxa(){
        txtMatKhauMoi = findViewById(R.id.txtMatKhauMoi);
        txtXNMatKhauMoi = findViewById(R.id.txtXNMatKhauMoi);
        ckbCheckShowHideMK = findViewById(R.id.ckbCheckShowHideMK);
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhau);
        btnQuayLaiTrangDangNhap = findViewById(R.id.btnQuayLaiTrangDangNhap);
    }
}