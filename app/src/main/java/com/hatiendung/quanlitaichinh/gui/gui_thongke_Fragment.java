package com.hatiendung.quanlitaichinh.gui;

import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hatiendung.quanlitaichinh.R;
import com.hatiendung.quanlitaichinh.bll.bll_khoanchi;
import com.hatiendung.quanlitaichinh.bll.bll_khoanthu;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link gui_thongke_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class gui_thongke_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    Button chonNgayTK, chonThangTK, chonNamTK;
    TextView txtChonNgayTK, txtChonThangTK, txtChonNamTK;
    LinearLayout linearChonNgay, linearChonThang, linearChonNam;
    BarChart barChartTK;
    PieChart pieChartTK;
    bll_khoanchi khoanchi_bll;
    bll_khoanthu khoanthu_bll;

    String checkOptions = "ngày", data = "";
    String valueDateSelect, fomatDateSelect = "%Y-%m-%d";
    DateTimeFormatter fomatDate =  DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public gui_thongke_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment gui_thongke_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static gui_thongke_Fragment newInstance(String param1, String param2) {
        gui_thongke_Fragment fragment = new gui_thongke_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_gui_thongke_, container, false);
        khoanthu_bll = new bll_khoanthu(requireContext());
        khoanchi_bll = new bll_khoanchi(requireContext());
        anhxa(view);

        Bundle bundle = getArguments();
        if(bundle != null){
            data = bundle.getString("taikhoan");
        }

        if(checkOptions.equals("ngày")){
            linearChonNgay.setVisibility(View.VISIBLE);
            linearChonThang.setVisibility(View.GONE);
            linearChonNam.setVisibility(View.GONE);
            txtChonNgayTK.setText(LocalDate.now().format(fomatDate)+"");
            valueDateSelect = LocalDate.now()+"";
            fomatDateSelect = "%Y-%m-%d";
            setBarChart();
            setPieChart();
        }

        if (checkOptions.equals("tháng")) {
            linearChonNgay.setVisibility(View.GONE);
            linearChonThang.setVisibility(View.VISIBLE);
            linearChonNam.setVisibility(View.GONE);
            txtChonThangTK.setText(LocalDate.now().getMonthValue()+"-"+LocalDate.now().getYear());
            String thang = LocalDate.now().getMonthValue()+"";
            valueDateSelect = LocalDate.now().getYear()+"-"+((thang.length() == 1) ? thang = "0"+thang : thang);
            fomatDateSelect = "%Y-%m";
            setBarChart();
            setPieChart();
        }

        if (checkOptions.equals("năm")) {
            linearChonNgay.setVisibility(View.GONE);
            linearChonThang.setVisibility(View.GONE);
            linearChonNam.setVisibility(View.VISIBLE);
            txtChonNamTK.setText(LocalDate.now().getYear()+"");
            valueDateSelect = LocalDate.now().getYear()+"";
            fomatDateSelect = "%Y";
            setBarChart();
            setPieChart();
        }

        setEventButton();
        return view ;
    }

    private void setEventButton() {
        chonNgayTK.setOnClickListener(v -> {
            checkOptions = "ngày";
            linearChonNgay.setVisibility(View.VISIBLE);
            linearChonThang.setVisibility(View.GONE);
            linearChonNam.setVisibility(View.GONE);
            txtChonNgayTK.setText(LocalDate.now().format(fomatDate)+"");
            valueDateSelect = LocalDate.now()+"";
            fomatDateSelect = "%Y-%m-%d";
            setBarChart();
            setPieChart();
        });

        chonThangTK.setOnClickListener(v -> {
            checkOptions = "tháng";
            linearChonNgay.setVisibility(View.GONE);
            linearChonThang.setVisibility(View.VISIBLE);
            linearChonNam.setVisibility(View.GONE);
            txtChonThangTK.setText(LocalDate.now().getMonthValue()+"-"+LocalDate.now().getYear());
            String thang = LocalDate.now().getMonthValue()+"";
            valueDateSelect = LocalDate.now().getYear()+"-"+((thang.length() == 1) ? thang = "0"+thang : thang);
            fomatDateSelect = "%Y-%m";
            setBarChart();
            setPieChart();
        });

        chonNamTK.setOnClickListener(v -> {
            checkOptions = "năm";
            linearChonNgay.setVisibility(View.GONE);
            linearChonThang.setVisibility(View.GONE);
            linearChonNam.setVisibility(View.VISIBLE);
            txtChonNamTK.setText(LocalDate.now().getYear()+"");
            valueDateSelect = LocalDate.now().getYear()+"";
            fomatDateSelect = "%Y";
            setBarChart();
            setPieChart();
        });

        txtChonNgayTK.setOnClickListener(v -> {
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
                txtChonNgayTK.setText(ngay+"-"+thang+"-"+datePicker.getYear());
                valueDateSelect = datePicker.getYear()+"-"+thang+"-"+ngay;
                fomatDateSelect = "%Y-%m-%d";
                dialog.dismiss();
                setBarChart();
                setPieChart();
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

                txtChonNgayTK.setText(ngay+"-"+thang+"-"+datePicker.getYear());
                valueDateSelect = datePicker.getYear()+"-"+thang+"-"+ngay;
                fomatDateSelect = "%Y-%m-%d";
                dialog.dismiss();
                setBarChart();
                setPieChart();
            });
            dialog.show();
        });

        txtChonThangTK.setOnClickListener(v -> {
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
                txtChonThangTK.setText(thang+"-"+datePicker.getYear());
                valueDateSelect = datePicker.getYear()+"-"+thang;
                fomatDateSelect = "%Y-&m";
                dialog.dismiss();
                setBarChart();
                setPieChart();
            });
            btnCloseDate.setOnClickListener(set -> {
                dialog.dismiss();
            });
            btnSaveDate.setOnClickListener(set -> {
                String thang = String.format("%d", datePicker.getMonth() + 1);
                if(thang.length() == 1){
                    thang = "0"+thang;
                }
                txtChonThangTK.setText(thang+"-"+datePicker.getYear());
                valueDateSelect = datePicker.getYear()+"-"+thang;
                fomatDateSelect = "%Y-%m";
                dialog.dismiss();
                setBarChart();
                setPieChart();
            });
            dialog.show();
        });

        txtChonNamTK.setOnClickListener(v -> {
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
                txtChonNamTK.setText(datePicker.getYear()+"");
                valueDateSelect = datePicker.getYear()+"";
                fomatDateSelect = "%Y";
                setBarChart();
                setPieChart();
                dialog.dismiss();
            });
            btnCloseDate.setOnClickListener(set -> {
                dialog.dismiss();
            });
            btnSaveDate.setOnClickListener(set -> {
                txtChonNamTK.setText(datePicker.getYear()+"");
                valueDateSelect = datePicker.getYear()+"";
                fomatDateSelect = "%Y";
                setBarChart();
                setPieChart();
                dialog.dismiss();
            });
            dialog.show();
        });
    }

    private void setBarChart(){
        Float khoanthu = khoanthu_bll.getSumKhoanThu(khoanthu_bll.getTaiKhoan_id(data), valueDateSelect, fomatDateSelect);
        Float khoanchi = khoanchi_bll.getSumChiTieu(khoanchi_bll.getTaiKhoan_id(data), valueDateSelect, fomatDateSelect);
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0, khoanthu));
        barEntries.add(new BarEntry(1, khoanchi));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Thu | Chi");
        barDataSet.setColors(new int[]{android.graphics.Color.GREEN, android.graphics.Color.RED});
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        barChartTK.setData(barData);

        Legend legend = barChartTK.getLegend();
        legend.setEnabled(true);
        legend.setTextSize(14f);

        barChartTK.getDescription().setEnabled(false);
        barChartTK.getAxisLeft().setDrawGridLines(false);
        barChartTK.getXAxis().setDrawGridLines(false);
        barChartTK.getAxisRight().setEnabled(false);

        barChartTK.invalidate();
    }

    private void setPieChart(){
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        Cursor cursor = khoanchi_bll.getSumChiTieu_DanhMuc(khoanchi_bll.getTaiKhoan_id(data), valueDateSelect, fomatDateSelect);

        if(cursor.moveToFirst()){
            do{
                String danhmuc = khoanchi_bll.getDanhMuc_ten(cursor.getInt(0));
                Float sotien = cursor.getFloat(1);
                pieEntries.add(new PieEntry(sotien, danhmuc));
            }while (cursor.moveToNext());
        }
        cursor.close();

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData pieData = new PieData(pieDataSet);
        pieChartTK.setData(pieData);
        pieChartTK.getDescription().setEnabled(false);
        pieChartTK.setUsePercentValues(true);
        pieChartTK.setCenterText("Tình hình chi tiêu");
        pieChartTK.setCenterTextSize(18f);

        pieChartTK.invalidate();
    }

    private void anhxa(View view) {
        chonNgayTK = view.findViewById(R.id.chonNgayTK);
        chonThangTK = view.findViewById(R.id.chonThangTK);
        chonNamTK = view.findViewById(R.id.chonNamTK);
        txtChonNgayTK = view.findViewById(R.id.txtChonNgayTK);
        txtChonThangTK = view.findViewById(R.id.txtChonThangTK);
        txtChonNamTK = view.findViewById(R.id.txtChonNamTK);
        barChartTK = view.findViewById(R.id.barChartThuChiTK);
        pieChartTK = view.findViewById(R.id.pieChartTK);
        linearChonNgay = view.findViewById(R.id.linearNgayTK);
        linearChonThang = view.findViewById(R.id.linearThangTK);
        linearChonNam = view.findViewById(R.id.linearNamTK);
    }
}