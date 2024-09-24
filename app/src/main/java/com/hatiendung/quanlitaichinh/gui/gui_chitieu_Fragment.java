package com.hatiendung.quanlitaichinh.gui;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.time.LocalDate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hatiendung.quanlitaichinh.R;
import com.hatiendung.quanlitaichinh.bll.adapter_recycle_khoanchi;
import com.hatiendung.quanlitaichinh.bll.bll_khoanchi;
import com.hatiendung.quanlitaichinh.dto.dto_KhoanChi;

import java.io.File;
import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link gui_chitieu_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class gui_chitieu_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button chonNgaySKC, chonThangSKC, chonNamSKC;
    String checkOptions = "ngày", data = "";
    LinearLayout linearNgaySKC, linearThangSKC, linearNamSKC;
    TextView txtChonNamSKC, txtChonThangSKC, txtChonNgaySKC, txtNganSachKC, txtChiPhiKC;

    RecyclerView list_itemKC;
    bll_khoanchi khoanchi_bll;
    ArrayList<dto_KhoanChi> arrKhoanChi;
    adapter_recycle_khoanchi adapterKhoanChi;

    String valueDateSelect, fomatDateSelect = "%Y-%m-%d";
    DateTimeFormatter fomatDate =  DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public gui_chitieu_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment gui_chitieu_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static gui_chitieu_Fragment newInstance(String param1, String param2) {
        gui_chitieu_Fragment fragment = new gui_chitieu_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_gui_chitieu_, container, false);
        anhxa(view);

        getParentFragmentManager().setFragmentResultListener("option", this, (requestKey, bundle) -> {
            checkOptions = bundle.getString("option");
        });

        Bundle bundle = getArguments();
        if(bundle != null){
            data = bundle.getString("taikhoan");
        }

        khoanchi_bll = new bll_khoanchi(requireContext());
        txtNganSachKC.setText(khoanchi_bll.getSumNganSach(khoanchi_bll.getTaiKhoan_id(data))+" vnđ");
        txtChiPhiKC.setText(khoanchi_bll.getChiPhi(khoanchi_bll.getTaiKhoan_id(data), valueDateSelect, fomatDateSelect)+" vnđ");
        arrKhoanChi = new ArrayList<>();
        adapterKhoanChi = new adapter_recycle_khoanchi(requireContext(), arrKhoanChi, new adapter_recycle_khoanchi.OnItemClickListener() {
            @Override
            public void onItemClick(dto_KhoanChi khoanchi) {
                Fragment detailFragment = new gui_SuaXoaFragment();
                Bundle bd = new Bundle();
                bd.putString("options", "suaxoaKC");
                bd.putInt("id",khoanchi.getId());
                bd.putInt("taikhoan_id",khoanchi.getTaikhoan_id());
                bd.putInt("danhmuc_id",khoanchi.getDanhmuc_id());
                bd.putString("nguonthu","");
                bd.putDouble("sotien",khoanchi.getSotien());
                bd.putString("ngay",khoanchi.getNgay());
                bd.putString("mota",khoanchi.getMota());
                bd.putString("option", checkOptions);
                detailFragment.setArguments(bd);

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.frame_layout_view, detailFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        if(checkOptions.equals("ngày")){
            linearNgaySKC.setVisibility(View.VISIBLE);
            linearThangSKC.setVisibility(View.GONE);
            linearNamSKC.setVisibility(View.GONE);
            txtChonNgaySKC.setText(LocalDate.now().format(fomatDate)+"");
            valueDateSelect = LocalDate.now()+"";
            fomatDateSelect = "%Y-%m-%d";
            loadDataKhoanChi(requireContext());
            txtChiPhiKC.setText(khoanchi_bll.getChiPhi(khoanchi_bll.getTaiKhoan_id(data), valueDateSelect, fomatDateSelect)+" vnđ");
        }

        if(checkOptions.equals("tháng")){
            linearNgaySKC.setVisibility(View.GONE);
            linearThangSKC.setVisibility(View.VISIBLE);
            linearNamSKC.setVisibility(View.GONE);
            txtChonThangSKC.setText(LocalDate.now().getMonthValue()+"-"+LocalDate.now().getYear());
            String thang = LocalDate.now().getMonthValue()+"";
            valueDateSelect = LocalDate.now().getYear()+"-"+((thang.length() == 1) ? thang = "0"+thang : thang);
            fomatDateSelect = "%Y-%m";
            loadDataKhoanChi(requireContext());
            txtChiPhiKC.setText(khoanchi_bll.getChiPhi(khoanchi_bll.getTaiKhoan_id(data), valueDateSelect, fomatDateSelect)+" vnđ");
        }

        if(checkOptions.equals("năm")){
            linearNgaySKC.setVisibility(View.GONE);
            linearThangSKC.setVisibility(View.GONE);
            linearNamSKC.setVisibility(View.VISIBLE);
            txtChonNamSKC.setText(LocalDate.now().getYear()+"");
            valueDateSelect = LocalDate.now().getYear()+"";
            fomatDateSelect = "%Y";
            loadDataKhoanChi(requireContext());
            txtChiPhiKC.setText(khoanchi_bll.getChiPhi(khoanchi_bll.getTaiKhoan_id(data), valueDateSelect, fomatDateSelect)+" vnđ");
        }
        setEventButton();
        return view;
    }
    private void anhxa(View view){
        chonNgaySKC = view.findViewById(R.id.chonNgaySKC);
        chonThangSKC = view.findViewById(R.id.chonThangSKC);
        chonNamSKC = view.findViewById(R.id.chonNamSKC);
        linearNgaySKC = view.findViewById(R.id.linearNgaySKC);
        linearThangSKC = view.findViewById(R.id.linearThangSKC);
        linearNamSKC = view.findViewById(R.id.linearNamSKC);
        txtChonNamSKC = view.findViewById(R.id.txtChonNamSKC);
        txtChonThangSKC = view.findViewById(R.id.txtChonThangSKC);
        txtChonNgaySKC = view.findViewById(R.id.txtChonNgaySKC);
        list_itemKC = view.findViewById(R.id.list_itemKC);
        txtChiPhiKC = view.findViewById(R.id.txtChiPhiKC);
        txtNganSachKC = view.findViewById(R.id.txtNganSachKC);
    }
    private void setEventButton(){
        chonNgaySKC.setOnClickListener(v -> {
            checkOptions = "ngày";
            linearNgaySKC.setVisibility(View.VISIBLE);
            linearThangSKC.setVisibility(View.GONE);
            linearNamSKC.setVisibility(View.GONE);
            txtChonNgaySKC.setText(LocalDate.now().format(fomatDate)+"");
            valueDateSelect = LocalDate.now()+"";
            fomatDateSelect = "%Y-%m-%d";
            loadDataKhoanChi(requireContext());
            txtChiPhiKC.setText(khoanchi_bll.getChiPhi(khoanchi_bll.getTaiKhoan_id(data), valueDateSelect, fomatDateSelect)+" vnđ");
        });
        chonThangSKC.setOnClickListener(v -> {
            checkOptions = "tháng";
            linearNgaySKC.setVisibility(View.GONE);
            linearThangSKC.setVisibility(View.VISIBLE);
            linearNamSKC.setVisibility(View.GONE);
            txtChonThangSKC.setText(LocalDate.now().getMonthValue()+"-"+LocalDate.now().getYear());
            String thang = LocalDate.now().getMonthValue()+"";
            valueDateSelect = LocalDate.now().getYear()+"-"+((thang.length() == 1) ? thang = "0"+thang : thang);
            fomatDateSelect = "%Y-%m";
            loadDataKhoanChi(requireContext());
            txtChiPhiKC.setText(khoanchi_bll.getChiPhi(khoanchi_bll.getTaiKhoan_id(data), valueDateSelect, fomatDateSelect)+" vnđ");
        });
        chonNamSKC.setOnClickListener(v -> {
            checkOptions = "năm";
            linearNgaySKC.setVisibility(View.GONE);
            linearThangSKC.setVisibility(View.GONE);
            linearNamSKC.setVisibility(View.VISIBLE);
            txtChonNamSKC.setText(LocalDate.now().getYear()+"");
            valueDateSelect = LocalDate.now().getYear()+"";
            fomatDateSelect = "%Y";
            loadDataKhoanChi(requireContext());
            txtChiPhiKC.setText(khoanchi_bll.getChiPhi(khoanchi_bll.getTaiKhoan_id(data), valueDateSelect, fomatDateSelect)+" vnđ");
        });
        txtChonThangSKC.setOnClickListener(v -> {
            Dialog dialog = new Dialog(requireContext());
            dialog.setContentView(R.layout.form_datepicker);
            DatePicker datePicker = dialog.findViewById(R.id.datepicker);
            Button btnCloseDate = dialog.findViewById(R.id.btnCloseDate);
            Button btnSaveDate = dialog.findViewById(R.id.btnSaveDate);
            Button btnToday = dialog.findViewById(R.id.btnToday);
            try{
                Field daySpinner = datePicker.getClass().getDeclaredField("mDaySpinner");
                daySpinner.setAccessible(true);
                ((View) daySpinner.get(datePicker)).setVisibility(View.GONE);
            }catch (Exception e){
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            datePicker.init(year, month,1, null);

            btnToday.setOnClickListener(set -> {
                String thang = String.format("%d", datePicker.getMonth() + 1);
                if(thang.length() == 1){
                    thang = "0"+thang;
                }
                txtChonThangSKC.setText(thang+"-"+datePicker.getYear());
                valueDateSelect = datePicker.getYear()+"-"+thang;
                fomatDateSelect = "%Y-&m";
                loadDataKhoanChi(requireContext());
                txtChiPhiKC.setText(khoanchi_bll.getChiPhi(khoanchi_bll.getTaiKhoan_id(data), valueDateSelect, fomatDateSelect)+" vnđ");
                dialog.dismiss();
            });
            btnCloseDate.setOnClickListener(set -> {
                dialog.dismiss();
            });
            btnSaveDate.setOnClickListener(set -> {
                String thang = String.format("%d", datePicker.getMonth() + 1);
                if(thang.length() == 1){
                    thang = "0"+thang;
                }
                txtChonThangSKC.setText(thang+"-"+datePicker.getYear());
                valueDateSelect = datePicker.getYear()+"-"+thang;
                fomatDateSelect = "%Y-%m";
                loadDataKhoanChi(requireContext());
                txtChiPhiKC.setText(khoanchi_bll.getChiPhi(khoanchi_bll.getTaiKhoan_id(data), valueDateSelect, fomatDateSelect)+" vnđ");
                dialog.dismiss();
            });
            dialog.show();
        });

        txtChonNamSKC.setOnClickListener(v -> {
            Dialog dialog = new Dialog(requireContext());
            dialog.setContentView(R.layout.form_datepicker);
            DatePicker datePicker = dialog.findViewById(R.id.datepicker);
            Button btnCloseDate = dialog.findViewById(R.id.btnCloseDate);
            Button btnSaveDate = dialog.findViewById(R.id.btnSaveDate);
            Button btnToday = dialog.findViewById(R.id.btnToday);
            try{
                Field daySpinner = datePicker.getClass().getDeclaredField("mDaySpinner");
                daySpinner.setAccessible(true);
                ((View) daySpinner.get(datePicker)).setVisibility(View.GONE);

                Field monthSpinner = datePicker.getClass().getDeclaredField("mMonthSpinner");
                monthSpinner.setAccessible(true);
                ((View) monthSpinner.get(datePicker)).setVisibility(View.GONE);
            }catch (Exception e){
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);

            datePicker.init(year, 1,1, null);

            btnToday.setOnClickListener(set -> {
                txtChonNamSKC.setText(datePicker.getYear()+"");
                valueDateSelect = datePicker.getYear()+"";
                fomatDateSelect = "%Y";
                loadDataKhoanChi(requireContext());
                txtChiPhiKC.setText(khoanchi_bll.getChiPhi(khoanchi_bll.getTaiKhoan_id(data), valueDateSelect, fomatDateSelect)+" vnđ");
                dialog.dismiss();
            });
            btnCloseDate.setOnClickListener(set -> {
                dialog.dismiss();
            });
            btnSaveDate.setOnClickListener(set -> {
                txtChonNamSKC.setText(datePicker.getYear()+"");
                valueDateSelect = datePicker.getYear()+"";
                fomatDateSelect = "%Y";
                loadDataKhoanChi(requireContext());
                txtChiPhiKC.setText(khoanchi_bll.getChiPhi(khoanchi_bll.getTaiKhoan_id(data), valueDateSelect, fomatDateSelect)+" vnđ");
                dialog.dismiss();
            });
            dialog.show();
        });
        txtChonNgaySKC.setOnClickListener(v  -> {
            Dialog dialog = new Dialog(requireContext());
            dialog.setContentView(R.layout.form_datepicker);
            DatePicker datePicker = dialog.findViewById(R.id.datepicker);
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
                txtChonNgaySKC.setText(ngay+"-"+thang+"-"+datePicker.getYear());
                valueDateSelect = datePicker.getYear()+"-"+thang+"-"+ngay;
                fomatDateSelect = "%Y-%m-%d";
                loadDataKhoanChi(requireContext());
                txtChiPhiKC.setText(khoanchi_bll.getChiPhi(khoanchi_bll.getTaiKhoan_id(data), valueDateSelect, fomatDateSelect)+" vnđ");
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

                txtChonNgaySKC.setText(ngay+"-"+thang+"-"+datePicker.getYear());
                valueDateSelect = datePicker.getYear()+"-"+thang+"-"+ngay;
                fomatDateSelect = "%Y-%m-%d";
                loadDataKhoanChi(requireContext());
                txtChiPhiKC.setText(khoanchi_bll.getChiPhi(khoanchi_bll.getTaiKhoan_id(data), valueDateSelect, fomatDateSelect)+" vnđ");
                dialog.dismiss();
            });

            dialog.show();
        });
    }

    private void loadDataKhoanChi(Context context) {
        arrKhoanChi.clear();
        list_itemKC.setAdapter(null);
        list_itemKC.setLayoutManager(null);
        Cursor cursor = khoanchi_bll.getAllKhoanChi(khoanchi_bll.getTaiKhoan_id(data), valueDateSelect, fomatDateSelect);
        if (cursor.getCount() == 0) {
            Toast.makeText(requireContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
            return;
        }
        while (cursor.moveToNext()){
            arrKhoanChi.add(new dto_KhoanChi(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getDouble(3),
                    cursor.getString(4),
                    cursor.getString(5)
            ));
        }
        list_itemKC.setAdapter(adapterKhoanChi);
        list_itemKC.setLayoutManager(new LinearLayoutManager(context));
    }
}