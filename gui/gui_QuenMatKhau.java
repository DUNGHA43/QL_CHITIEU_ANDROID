package com.hatiendung.quanlitaichinh.gui;

import android.content.Intent;
import android.os.Bundle;
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
import com.hatiendung.quanlitaichinh.bll.bll_gmailxacthuc;
import com.hatiendung.quanlitaichinh.bll.bll_taikhoan;

public class gui_QuenMatKhau extends AppCompatActivity {
    EditText txtGmailQMK, txtMaXacNhanQMK;
    TextView txtTaiKhoanQMK;
    Button btnChonGmail, btnGuiMaQMK, btnXacNhanQMK;
    bll_gmailxacthuc gmailxacthuc = new bll_gmailxacthuc();
    bll_taikhoan taikhoan;
    String data = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gui_quen_mat_khau);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        anhxa();
        taikhoan = new bll_taikhoan(this);

        btnChonGmail.setOnClickListener(v -> {
            data = taikhoan.searchAccountWithGmail(txtGmailQMK.getText().toString().trim());
            if(gmailxacthuc.isGmail(txtGmailQMK.getText().toString().trim()) == false){
                Toast.makeText(this, "Địa chỉ gmail không hợp lệ!", Toast.LENGTH_SHORT).show();
            }
            else if(txtGmailQMK.getText().toString().isEmpty()){
                Toast.makeText(this, "Vui lòng nhập gmail!", Toast.LENGTH_SHORT).show();
            }
            else if(data.isEmpty()){
                Toast.makeText(this, "Gmail này chưa đăng ký!", Toast.LENGTH_SHORT).show();
            }else{
                txtTaiKhoanQMK.setText(data);
            }
        });

        btnGuiMaQMK.setOnClickListener(v -> {
            String gmail = txtGmailQMK.getText().toString().trim();
            if(data.isEmpty()){
                Toast.makeText(this, "Gmail này chưa đăng ký!", Toast.LENGTH_SHORT).show();
            }
            else{
                if(gmail.isEmpty()){
                    Toast.makeText(this, "Vui lòng nhập gmail của bạn!", Toast.LENGTH_SHORT).show();
                } else if (gmailxacthuc.isGmail(gmail) == false) {
                    Toast.makeText(this, "Địa chỉ gmail không hợp lệ!", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        gmailxacthuc.sendMail(gmail);
                        Toast.makeText(this, "Đã gửi mã xác thực đến gmail!", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(this, "Gửi gmail thất bại! Thử lại sau.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnXacNhanQMK.setOnClickListener(v -> {
            if(gmailxacthuc.getMa().equals(txtMaXacNhanQMK.getText().toString())){
                Intent intent = new Intent(gui_QuenMatKhau.this, gui_DoiMatKhau.class);
                intent.putExtra("taikhoan", data);
                startActivity(intent);
            }else if(txtMaXacNhanQMK.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Vui lòng nhập mã xác thực!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Mã xác thực không chính xác!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void anhxa(){
        txtGmailQMK = findViewById(R.id.txtGmailQMK);
        txtMaXacNhanQMK = findViewById(R.id.txtMaXacNhanQMK);
        txtTaiKhoanQMK = findViewById(R.id.txtTaiKhoanQMK);
        btnGuiMaQMK = findViewById(R.id.btnGuiMaQMK);
        btnChonGmail = findViewById(R.id.btnChonGmail);
        btnXacNhanQMK = findViewById(R.id.btnXacNhanQMK);
    }
}