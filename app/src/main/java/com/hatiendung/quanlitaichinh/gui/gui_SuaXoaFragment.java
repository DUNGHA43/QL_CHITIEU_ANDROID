package com.hatiendung.quanlitaichinh.gui;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hatiendung.quanlitaichinh.R;
import com.hatiendung.quanlitaichinh.bll.bll_khoanchi;
import com.hatiendung.quanlitaichinh.bll.bll_khoanthu;
import com.hatiendung.quanlitaichinh.dto.dto_KhoanChi;
import com.hatiendung.quanlitaichinh.dto.dto_KhoanThu;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link gui_SuaXoaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class gui_SuaXoaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    String options, mota, ngay, nguonthu, optionView;
    Double sotien;
    int id, taikhoan_id, danhmuc_id;

    EditText txtSoTienSuaXoa, txtMotaSuaXoa, txtNguonThuSuaXoa;
    TextView txtHangMucSuaXoa, txtChonNgaySuaXoa;
    Button btnAnUongSuaXoa, btnDiLaiSuaXoa, btnTrangPhucSuaXoa, btnSucKhoeSuaXoa, btnDichVuSuaXoa, btnBaoHiemSuaXoa, btnLamDepSuaXoa, btnChoVaySuaXoa, btnSua, btnXoa;
    LinearLayout linearNguonThuSuaXoa, lineartxtHangMucSuaXoa, linearHangMucSuaXoa;
    DatePicker datePicker;
    Bundle bundle;
    bll_khoanchi khoanchi_bll;
    bll_khoanthu khoanthu_bll;
    dto_KhoanChi khoanchi;
    dto_KhoanThu khoanthu;
    public gui_SuaXoaFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment gui_SuaXoaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static gui_SuaXoaFragment newInstance(String param1, String param2) {
        gui_SuaXoaFragment fragment = new gui_SuaXoaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gui__sua_xoa, container, false);
        khoanchi_bll = new bll_khoanchi(requireContext());
        khoanthu_bll = new bll_khoanthu(requireContext());
        khoanchi = new dto_KhoanChi();
        khoanthu = new dto_KhoanThu();
        anhxa(view);
        bundle = getArguments();
        if(bundle != null){
            optionView = bundle.getString("option");
            options = bundle.getString("options");
            id = bundle.getInt("id");
            taikhoan_id = bundle.getInt("taikhoan_id");
            danhmuc_id = bundle.getInt("danhmuc_id");
            nguonthu = bundle.getString("nguonthu");
            sotien = bundle.getDouble("sotien");
            ngay = bundle.getString("ngay");
            mota = bundle.getString("mota");
        }
        if(options.equals("suaxoaKC")){
            linearHangMucSuaXoa.setVisibility(View.VISIBLE);
            lineartxtHangMucSuaXoa.setVisibility(View.VISIBLE);
            linearNguonThuSuaXoa.setVisibility(View.GONE);
        }
        if(options.equals("suaxoaKT")){
            linearHangMucSuaXoa.setVisibility(View.GONE);
            lineartxtHangMucSuaXoa.setVisibility(View.GONE);
            linearNguonThuSuaXoa.setVisibility(View.VISIBLE);
        }
        txtSoTienSuaXoa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = charSequence.toString();

                if(!input.matches("\\d*")){
                    Toast.makeText(requireContext(), "Vui lòng chỉ nhập số!", Toast.LENGTH_SHORT).show();

                    String valid = input.replaceAll("[^\\d]", "");
                    txtSoTienSuaXoa.setText(valid);
                    txtSoTienSuaXoa.setSelection(valid.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        loadData();
        setEventButton();
        return view;
    }

    private void anhxa(View view) {
        txtSoTienSuaXoa = view.findViewById(R.id.txtSoTienSuaXoa);
        txtMotaSuaXoa = view.findViewById(R.id.txtMotaSuaXoa);
        txtNguonThuSuaXoa = view.findViewById(R.id.txtNguonThuSuaXoa);
        txtHangMucSuaXoa = view.findViewById(R.id.txtHangMucSuaXoa);
        txtChonNgaySuaXoa = view.findViewById(R.id.txtChonNgaySuaXoa);
        btnAnUongSuaXoa = view.findViewById(R.id.btnAnUongSuaXoa);
        btnDiLaiSuaXoa = view.findViewById(R.id.btnDiLaiSuaXoa);
        btnTrangPhucSuaXoa = view.findViewById(R.id.btnTrangPhucSuaXoa);
        btnSucKhoeSuaXoa = view.findViewById(R.id.btnSucKhoeSuaXoa);
        btnDichVuSuaXoa = view.findViewById(R.id.btnDichVuSuaXoa);
        btnBaoHiemSuaXoa = view.findViewById(R.id.btnBaoHiemSuaXoa);
        btnLamDepSuaXoa = view.findViewById(R.id.btnLamDepSuaXoa);
        btnChoVaySuaXoa = view.findViewById(R.id.btnChoVaySuaXoa);
        btnSua = view.findViewById(R.id.btnSua);
        btnXoa = view.findViewById(R.id.btnXoa);
        linearNguonThuSuaXoa = view.findViewById(R.id.linearNguonThuSuaXoa);
        lineartxtHangMucSuaXoa = view.findViewById(R.id.lineartxtHangMucSuaXoa);
        linearHangMucSuaXoa = view.findViewById(R.id.linearHangMucSuaXoa);
    }

    private void setEventButton(){
        btnAnUongSuaXoa.setOnClickListener(v -> {
            txtHangMucSuaXoa.setText("Ăn uống");
        });
        btnDiLaiSuaXoa.setOnClickListener(v -> {
            txtHangMucSuaXoa.setText("Đi lại");
        });
        btnTrangPhucSuaXoa.setOnClickListener(v -> {
            txtHangMucSuaXoa.setText("Trang phục");
        });
        btnSucKhoeSuaXoa.setOnClickListener(v -> {
            txtHangMucSuaXoa.setText("Sức khỏe");
        });
        btnDichVuSuaXoa.setOnClickListener(v -> {
            txtHangMucSuaXoa.setText("Dịch vụ");
        });
        btnBaoHiemSuaXoa.setOnClickListener(v -> {
            txtHangMucSuaXoa.setText("Bảo hiểm");
        });
        btnLamDepSuaXoa.setOnClickListener(v -> {
            txtHangMucSuaXoa.setText("Làm đẹp");
        });
        btnChoVaySuaXoa.setOnClickListener(v -> {
            txtHangMucSuaXoa.setText("Cho vay");
        });
        txtChonNgaySuaXoa.setOnClickListener(v -> {
            Dialog dialog = new Dialog(requireContext());
            dialog.setContentView(R.layout.form_datepicker);
            datePicker = dialog.findViewById(R.id.datepicker);
            Button btnCloseDate = dialog.findViewById(R.id.btnCloseDate);
            Button btnSaveDate = dialog.findViewById(R.id.btnSaveDate);
            Button btnToday = dialog.findViewById(R.id.btnToday);

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            datePicker.init(year, month, day, null);

            btnToday.setOnClickListener(set -> {
                String ngay = String.format("%d", datePicker.getDayOfMonth());
                String thang = String.format("%d", datePicker.getMonth() + 1);

                if(ngay.length() == 1){
                    ngay = "0"+ngay;
                }
                if(thang.length() == 1){
                    thang = "0"+thang;
                }
                txtChonNgaySuaXoa.setText(ngay+"-"+thang+"-"+datePicker.getYear());
                dialog.dismiss();
            });

            btnCloseDate.setOnClickListener(set -> {
                dialog.dismiss();
            });

            btnSaveDate.setOnClickListener(set -> {
                String ngay = String.format("%d", datePicker.getDayOfMonth());
                String thang = String.format("%d", datePicker.getMonth() + 1);

                if(ngay.length() == 1){
                    ngay = "0"+ngay;
                }
                if(thang.length() == 1){
                    thang = "0"+thang;
                }

                txtChonNgaySuaXoa.setText(ngay+"-"+thang+"-"+datePicker.getYear());
                dialog.dismiss();
            });

            dialog.show();
        });

        btnSua.setOnClickListener(v -> {
            if(options.equals("suaxoaKC")){
                khoanchi.setId(id);
                khoanchi.setTaikhoan_id(taikhoan_id);
                khoanchi.setDanhmuc_id(khoanchi_bll.getDanhMuc_id(txtHangMucSuaXoa.getText().toString()));
                khoanchi.setSotien(Double.parseDouble(txtSoTienSuaXoa.getText().toString()));
                khoanchi.setNgay(txtChonNgaySuaXoa.getText().toString());
                khoanchi.setMota(txtMotaSuaXoa.getText().toString());
                if(khoanchi_bll.update_khoanchi(khoanchi)){
                    Toast.makeText(requireContext(), "Lưu thành công!", Toast.LENGTH_SHORT).show();
                    bundle = new Bundle();
                    bundle.putString("options", optionView);
                    getParentFragmentManager().popBackStack();
                }
                else{
                    Toast.makeText(requireContext(), "Lưu không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
            if(options.equals("suaxoaKT")){
                khoanthu.setId(id);
                khoanthu.setTaikhoan_id(taikhoan_id);
                khoanthu.setNguonthu(txtNguonThuSuaXoa.getText().toString());
                khoanthu.setSotien(Double.parseDouble(txtSoTienSuaXoa.getText().toString()));
                khoanthu.setNgay(txtChonNgaySuaXoa.getText().toString());
                khoanthu.setMota(txtMotaSuaXoa.getText().toString());

                if(khoanthu_bll.update_khoanthu(khoanthu)){
                    Toast.makeText(requireContext(), "Lưu thành công!", Toast.LENGTH_SHORT).show();
                    bundle = new Bundle();
                    bundle.putString("options", optionView);
                    getParentFragmentManager().popBackStack();
                }
                else{
                    Toast.makeText(requireContext(), "Lưu không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnXoa.setOnClickListener(v -> {
            if(options.equals("suaxoaKC")){
                khoanchi.setId(id);
                if(khoanchi_bll.delete_khoanchi(khoanchi)){
                    Toast.makeText(requireContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                    getParentFragmentManager().popBackStack();
                }
                else{
                    Toast.makeText(requireContext(), "Xóa không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
            if(options.equals("suaxoaKT")){
                khoanthu.setId(id);
                if(khoanthu_bll.delete_khoanthu(khoanthu)){
                    Toast.makeText(requireContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                    getParentFragmentManager().popBackStack();
                }
                else{
                    Toast.makeText(requireContext(), "Xóa không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadData(){
        if(options.equals("suaxoaKC")){
            txtSoTienSuaXoa.setText(String.valueOf(sotien).substring(0, String.valueOf(sotien).length() - 1));
            txtHangMucSuaXoa.setText(khoanchi_bll.getDanhMuc_ten(danhmuc_id));
            txtMotaSuaXoa.setText(mota);
            txtChonNgaySuaXoa.setText(ngay);
        }
        if(options.equals("suaxoaKT")){
            txtSoTienSuaXoa.setText(String.valueOf(sotien).substring(0, String.valueOf(sotien).length() - 1));
            txtMotaSuaXoa.setText(mota);
            txtChonNgaySuaXoa.setText(ngay);
            txtNguonThuSuaXoa.setText(nguonthu);
        }
    }
}