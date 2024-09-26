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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hatiendung.quanlitaichinh.R;
import com.hatiendung.quanlitaichinh.bll.adapter_recycle_khoanthu;
import com.hatiendung.quanlitaichinh.bll.bll_khoanthu;
import com.hatiendung.quanlitaichinh.dto.dto_KhoanThu;

import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link gui_ngansach_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class gui_ngansach_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    Button chonNgaySKT, chonThangSKT, chonNamSKT;
    String checkOptions = "ngày", data = "";
    LinearLayout linearNgaySKT, linearThangSKT, linearNamSKT;
    TextView txtChonNamSKT, txtChonThangSKT, txtChonNgaySKT, txtNganSachKTHT, txtNganSachTongKT;

    RecyclerView list_itemKT;
    bll_khoanthu khoanthu_bll;
    ArrayList<dto_KhoanThu> arrkhoanthu;
    adapter_recycle_khoanthu adapterKhoanThu;

    String valueDateSelect, fomatDateSelect = "%Y-%m-%d";
    DateTimeFormatter fomatDate =  DateTimeFormatter.ofPattern("dd-MM-yyyy");
    NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
    public gui_ngansach_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment gui_ngansach_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static gui_ngansach_Fragment newInstance(String param1, String param2) {
        gui_ngansach_Fragment fragment = new gui_ngansach_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_gui_ngansach_, container, false);
        anhxa(view);

        getParentFragmentManager().setFragmentResultListener("option", this, (requestKey, bundle) -> {
            checkOptions = bundle.getString("option");
        });

        Bundle bundle = getArguments();
        if(bundle != null){
            data = bundle.getString("taikhoan");
        }

        khoanthu_bll = new bll_khoanthu(requireContext());
        txtNganSachTongKT.setText(numberFormat.format(khoanthu_bll.getSumNganSach(khoanthu_bll.getTaiKhoan_id(data)))+" vnđ");
        txtNganSachKTHT.setText(numberFormat.format(khoanthu_bll.getNganSachHT(khoanthu_bll.getTaiKhoan_id(data)))+" vnđ");
        arrkhoanthu = new ArrayList<>();
        adapterKhoanThu = new adapter_recycle_khoanthu(requireContext(), arrkhoanthu, new adapter_recycle_khoanthu.OnItemClickListener() {
            @Override
            public void onItemClick(dto_KhoanThu khoanthu) {
                Fragment detailFragment = new gui_SuaXoaFragment();
                Bundle bd = new Bundle();
                bd.putString("options", "suaxoaKT");
                bd.putInt("id",khoanthu.getId());
                bd.putInt("taikhoan_id",khoanthu.getTaikhoan_id());
                bd.putInt("danhmuc_id",0);
                bd.putString("nguonthu",khoanthu.getNguonthu());
                bd.putDouble("sotien",khoanthu.getSotien());
                bd.putString("ngay",khoanthu.getNgay());
                bd.putString("mota",khoanthu.getMota());
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
            linearNgaySKT.setVisibility(View.VISIBLE);
            linearThangSKT.setVisibility(View.GONE);
            linearNamSKT.setVisibility(View.GONE);
            txtChonThangSKT.setText(LocalDate.now().format(fomatDate)+"");
            valueDateSelect = LocalDate.now()+"";
            fomatDateSelect = "%Y-%m-%d";
            loadDataKhoanThu(requireContext());
        }

        if(checkOptions.equals("tháng")){
            linearNgaySKT.setVisibility(View.GONE);
            linearThangSKT.setVisibility(View.VISIBLE);
            linearNamSKT.setVisibility(View.GONE);
            txtChonNgaySKT.setText(LocalDate.now().getMonthValue()+"-"+LocalDate.now().getYear());
            String thang = LocalDate.now().getMonthValue()+"";
            valueDateSelect = LocalDate.now().getYear()+"-"+((thang.length() == 1) ? thang = "0"+thang : thang);
            fomatDateSelect = "%Y-%m";
            loadDataKhoanThu(requireContext());
        }

        if(checkOptions.equals("năm")){
            linearNgaySKT.setVisibility(View.GONE);
            linearThangSKT.setVisibility(View.GONE);
            linearNamSKT.setVisibility(View.VISIBLE);
            txtChonNamSKT.setText(LocalDate.now().getYear()+"");
            valueDateSelect = LocalDate.now().getYear()+"";
            fomatDateSelect = "%Y";
            loadDataKhoanThu(requireContext());
        }
        setEventButton();
        return view;
    }

    private void anhxa(View view){
        chonNgaySKT = view.findViewById(R.id.chonNgaySKT);
        chonThangSKT = view.findViewById(R.id.chonThangSKT);
        chonNamSKT = view.findViewById(R.id.chonNamSKT);
        linearNgaySKT = view.findViewById(R.id.linearNgaySKT);
        linearThangSKT = view.findViewById(R.id.linearThangSKT);
        linearNamSKT = view.findViewById(R.id.linearNamSKT);
        txtChonNamSKT = view.findViewById(R.id.txtChonNamSKT);
        txtChonThangSKT = view.findViewById(R.id.txtChonThangSKT);
        txtChonNgaySKT = view.findViewById(R.id.txtChonNgaySKT);
        list_itemKT = view.findViewById(R.id.list_itemKT);
        txtNganSachTongKT = view.findViewById(R.id.txtNganSachTong);
        txtNganSachKTHT = view.findViewById(R.id.txtNganSachHT);
    }
    private void setEventButton(){
        chonNgaySKT.setOnClickListener(v -> {
            checkOptions = "ngày";
            linearNgaySKT.setVisibility(View.VISIBLE);
            linearThangSKT.setVisibility(View.GONE);
            linearNamSKT.setVisibility(View.GONE);
            txtChonNgaySKT.setText(LocalDate.now().format(fomatDate)+"");
            valueDateSelect = LocalDate.now()+"";
            fomatDateSelect = "%Y-%m-%d";
            loadDataKhoanThu(requireContext());
        });

        chonThangSKT.setOnClickListener(v -> {
            checkOptions = "tháng";
            linearNgaySKT.setVisibility(View.GONE);
            linearThangSKT.setVisibility(View.VISIBLE);
            linearNamSKT.setVisibility(View.GONE);
            txtChonThangSKT.setText(LocalDate.now().getMonthValue()+"-"+LocalDate.now().getYear());
            String thang = LocalDate.now().getMonthValue()+"";
            valueDateSelect = LocalDate.now().getYear()+"-"+((thang.length() == 1) ? thang = "0"+thang : thang);
            fomatDateSelect = "%Y-%m";
            loadDataKhoanThu(requireContext());
        });

        chonNamSKT.setOnClickListener(v -> {
            checkOptions = "năm";
            linearNgaySKT.setVisibility(View.GONE);
            linearThangSKT.setVisibility(View.GONE);
            linearNamSKT.setVisibility(View.VISIBLE);
            txtChonNamSKT.setText(LocalDate.now().getYear()+"");
            valueDateSelect = LocalDate.now().getYear()+"";
            fomatDateSelect = "%Y";
            loadDataKhoanThu(requireContext());
        });

        txtChonThangSKT.setOnClickListener(v -> {
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
                txtChonThangSKT.setText(thang+"-"+datePicker.getYear());
                valueDateSelect = datePicker.getYear()+"-"+thang;
                fomatDateSelect = "%Y-&m";
                loadDataKhoanThu(requireContext());
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
                txtChonThangSKT.setText(thang+"-"+datePicker.getYear());
                valueDateSelect = datePicker.getYear()+"-"+thang;
                fomatDateSelect = "%Y-%m";
                loadDataKhoanThu(requireContext());
                dialog.dismiss();
            });
            dialog.show();
        });

        txtChonNamSKT.setOnClickListener(v -> {
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
                txtChonNamSKT.setText(datePicker.getYear()+"");
                valueDateSelect = datePicker.getYear()+"";
                fomatDateSelect = "%Y";
                loadDataKhoanThu(requireContext());
                dialog.dismiss();
            });
            btnCloseDate.setOnClickListener(set -> {
                dialog.dismiss();
            });
            btnSaveDate.setOnClickListener(set -> {
                txtChonNamSKT.setText(datePicker.getYear()+"");
                valueDateSelect = datePicker.getYear()+"";
                fomatDateSelect = "%Y";
                loadDataKhoanThu(requireContext());
                dialog.dismiss();
            });
            dialog.show();
        });

        txtChonNgaySKT.setOnClickListener(v  -> {
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
                txtChonNgaySKT.setText(ngay+"-"+thang+"-"+datePicker.getYear());
                valueDateSelect = datePicker.getYear()+"-"+thang+"-"+ngay;
                fomatDateSelect = "%Y-%m-%d";
                loadDataKhoanThu(requireContext());
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

                txtChonNgaySKT.setText(ngay+"-"+thang+"-"+datePicker.getYear());
                valueDateSelect = datePicker.getYear()+"-"+thang+"-"+ngay;
                fomatDateSelect = "%Y-%m-%d";
                loadDataKhoanThu(requireContext());
                dialog.dismiss();
            });

            dialog.show();
        });
    }
    private void loadDataKhoanThu(Context context){
        arrkhoanthu.clear();
        list_itemKT.setAdapter(null);
        list_itemKT.setLayoutManager(null);

        Cursor cursor = khoanthu_bll.getAllKhoanChi(khoanthu_bll.getTaiKhoan_id(data), valueDateSelect, fomatDateSelect);
        if (cursor.getCount() == 0) {
            Toast.makeText(requireContext(), "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
            return;
        }
        while (cursor.moveToNext()) {
            arrkhoanthu.add(new dto_KhoanThu(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getDouble(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)

            ));
        }
        list_itemKT.setAdapter(adapterKhoanThu);
        list_itemKT.setLayoutManager(new LinearLayoutManager(context));
    }
}