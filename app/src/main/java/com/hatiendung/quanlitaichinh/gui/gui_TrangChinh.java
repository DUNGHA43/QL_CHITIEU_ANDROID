package com.hatiendung.quanlitaichinh.gui;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hatiendung.quanlitaichinh.R;
import com.hatiendung.quanlitaichinh.databinding.ActivityGuiTrangChinhBinding;

public class gui_TrangChinh extends AppCompatActivity {
    ActivityGuiTrangChinhBinding binding;
    String data = "";
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityGuiTrangChinhBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent = getIntent();
        data = intent.getStringExtra("taikhoan");
        Bundle bundle = new Bundle();
        bundle.putString("taikhoan", data);
        replaceFragment(new gui_trangchu_Fragment(), bundle);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int i = item.getItemId();
            if(i == R.id.trangchu){
                replaceFragment(new gui_trangchu_Fragment(), bundle);
            }
            if(i == R.id.ngansach){
                replaceFragment(new gui_ngansach_Fragment(), bundle);
            }
            if(i == R.id.them){
                replaceFragment(new gui_them_Fragment(), bundle);
            }
            if(i == R.id.chitieu){
                replaceFragment(new gui_chitieu_Fragment(), bundle);
            }
            if(i == R.id.thongke){
                replaceFragment(new gui_thongke_Fragment(), bundle);
            }
            return true;
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void replaceFragment(Fragment fragment, Bundle bundle){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_view, fragment);
        fragment.setArguments(bundle);
        fragmentTransaction.commit();
    }
}