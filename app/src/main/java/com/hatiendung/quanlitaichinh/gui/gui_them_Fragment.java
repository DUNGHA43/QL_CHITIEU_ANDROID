package com.hatiendung.quanlitaichinh.gui;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
 * Use the {@link gui_them_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class gui_them_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Spinner spinner_chonmuc;
    LinearLayout lineartxtHangMuc, linearHangMuc, linearNguonThu;
    Button btnAnUong, btnDiLai, btnTrangPhuc, btnSucKhoe, btnDichVu, btnBaoHiem, btnLamDep, btnChoVay, btnLuu;
    Button btnToday, btnCloseDate, btnSaveDate;
    DatePicker datePicker;
    String valueDate = "", stateSwitch = "", data = "";
    EditText txtMoTa, txtNguonThu, txtSoTien;
    TextView txtHangMuc, btnChonNgay;
    dto_KhoanChi khoanchi;
    dto_KhoanThu khoanthu;
    bll_khoanchi bll_khoanchi;
    bll_khoanthu bll_khoanthu;
    public gui_them_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment gui_them_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static gui_them_Fragment newInstance(String param1, String param2) {
        gui_them_Fragment fragment = new gui_them_Fragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gui_them_, container, false);
        anhxa(view);
        bll_khoanchi = new bll_khoanchi(requireContext());
        bll_khoanthu = new bll_khoanthu(requireContext());
        Bundle bundle = getArguments();
        if(bundle != null){
            data = bundle.getString("taikhoan");
        }
        txtSoTien.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = charSequence.toString();

                if(!input.matches("\\d*")){
                    Toast.makeText(requireContext(), "Vui lòng chỉ nhập số!", Toast.LENGTH_SHORT).show();

                    String valid = input.replaceAll("[^\\d]", "");
                    txtSoTien.setText(valid);
                    txtSoTien.setSelection(valid.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        setValueSpinner();
        setSpinerSelected();
        setEventButton();
        return view;
    }

    private void anhxa(View view){
        spinner_chonmuc = view.findViewById(R.id.spinner_chonmuc);
        lineartxtHangMuc = view.findViewById(R.id.lineartxtHangMuc);
        linearHangMuc = view.findViewById(R.id.linearHangMuc);
        linearNguonThu = view.findViewById(R.id.linearNguonThu);
        btnAnUong = view.findViewById(R.id.btnAnUong);
        btnDiLai = view.findViewById(R.id.btnDiLai);
        btnTrangPhuc  = view.findViewById(R.id.btnTrangPhuc);
        btnSucKhoe = view.findViewById(R.id.btnSucKhoe);
        btnDichVu  = view.findViewById(R.id.btnDichVu);
        btnBaoHiem = view.findViewById(R.id.btnBaoHiem);
        btnLamDep  = view.findViewById(R.id.btnLamDep);
        btnChoVay = view.findViewById(R.id.btnChoVay);
        btnLuu = view.findViewById(R.id.btnLuu);
        txtHangMuc = view.findViewById(R.id.txtHangMuc);
        btnChonNgay = view.findViewById(R.id.txtChonNgay);
        txtSoTien = view.findViewById(R.id.txtSoTien);
        txtMoTa = view.findViewById(R.id.txtMota);
        txtNguonThu = view.findViewById(R.id.txtNguonThu);
    }
    private void setValueSpinner(){
        String[] items = {"Khoản chi", "Nguồn thu"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner_chonmuc.setAdapter(adapter);
    }
    private void setSpinerSelected(){
        spinner_chonmuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View selectedItemView, int positon, long id) {
                String selectedItem = adapterView.getItemAtPosition(positon).toString();
                if(selectedItem.equals("Khoản chi")){
                    linearNguonThu.setVisibility(View.GONE);
                    lineartxtHangMuc.setVisibility(View.VISIBLE);
                    linearHangMuc.setVisibility(View.VISIBLE);
                    stateSwitch = "Khoản chi";
                }
                if(selectedItem.equals("Nguồn thu")){
                    linearNguonThu.setVisibility(View.VISIBLE);
                    lineartxtHangMuc.setVisibility(View.GONE);
                    linearHangMuc.setVisibility(View.GONE);
                    stateSwitch = "Nguồn thu";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    private void setEventButton(){
        btnAnUong.setOnClickListener(v -> {
            txtHangMuc.setText("Ăn uống");
        });
        btnDiLai.setOnClickListener(v -> {
            txtHangMuc.setText("Đi lại");
        });
        btnTrangPhuc.setOnClickListener(v -> {
            txtHangMuc.setText("Trang phục");
        });
        btnSucKhoe.setOnClickListener(v -> {
            txtHangMuc.setText("Sức khỏe");
        });
        btnDichVu.setOnClickListener(v -> {
            txtHangMuc.setText("Dịch vụ");
        });
        btnBaoHiem.setOnClickListener(v -> {
            txtHangMuc.setText("Bảo hiểm");
        });
        btnLamDep.setOnClickListener(v -> {
            txtHangMuc.setText("Làm đẹp");
        });
        btnChoVay.setOnClickListener(v -> {
            txtHangMuc.setText("Cho vay");
        });
        btnChonNgay.setOnClickListener(v -> {
            Dialog dialog = new Dialog(requireContext());
            dialog.setContentView(R.layout.form_datepicker);
            datePicker = dialog.findViewById(R.id.datepicker);
            btnCloseDate = dialog.findViewById(R.id.btnCloseDate);
            btnSaveDate = dialog.findViewById(R.id.btnSaveDate);
            btnToday = dialog.findViewById(R.id.btnToday);

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
                btnChonNgay.setText(ngay+"-"+thang+"-"+datePicker.getYear());
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

                btnChonNgay.setText(ngay+"-"+thang+"-"+datePicker.getYear());
                dialog.dismiss();
            });

            dialog.show();
        });
        btnLuu.setOnClickListener(v -> {
            if(stateSwitch.equals("Khoản chi")){
                khoanchi = new dto_KhoanChi();
                khoanchi.setTaikhoan_id(bll_khoanchi.getTaiKhoan_id(data));
                khoanchi.setDanhmuc_id(bll_khoanchi.getDanhMuc_id(txtHangMuc.getText().toString().trim()));
                khoanchi.setSotien(Double.parseDouble(txtSoTien.getText().toString()));
                khoanchi.setNgay(btnChonNgay.getText().toString());
                khoanchi.setMota(txtMoTa.getText().toString());
                if(bll_khoanchi.add_KhoanChi(khoanchi)){
                    Toast.makeText(requireContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    txtSoTien.setText("0");
                    txtMoTa.setText("");
                    btnChonNgay.setText("");
                    txtHangMuc.setText("Chọn hạng mục");
                }
                else{
                    Toast.makeText(requireContext(), "Thêm không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
            if(stateSwitch.equals("Nguồn thu")){
                khoanthu = new dto_KhoanThu();
                khoanthu.setTaikhoan_id(bll_khoanthu.getTaiKhoan_id(data));
                khoanthu.setSotien(Double.parseDouble(txtSoTien.getText().toString()));
                khoanthu.setNguonthu(txtNguonThu.getText().toString());
                khoanthu.setNgay(btnChonNgay.getText().toString());
                khoanthu.setMota(txtMoTa.getText().toString());
                if(bll_khoanthu.add_khoanthu(khoanthu)){
                    Toast.makeText(requireContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    txtSoTien.setText("0");
                    txtMoTa.setText("");
                    btnChonNgay.setText("");
                    txtNguonThu.setText("");
                }
                else{
                    Toast.makeText(requireContext(), "Thêm không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}