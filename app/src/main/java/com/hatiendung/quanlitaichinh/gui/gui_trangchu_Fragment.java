package com.hatiendung.quanlitaichinh.gui;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;


import com.hatiendung.quanlitaichinh.R;
import com.hatiendung.quanlitaichinh.bll.bll_khoanchi;
import com.hatiendung.quanlitaichinh.bll.bll_khoanthu;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link gui_trangchu_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class gui_trangchu_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    BarChart barChart;
    PieChart pieChart;
    TextView txtTongSoDu, txtXinChaoTK;
    bll_khoanthu khoanthu_bll;
    bll_khoanchi khoanchi_bll;
    String data;
    NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
    public gui_trangchu_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment gui_trangchu_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static gui_trangchu_Fragment newInstance(String param1, String param2) {
        gui_trangchu_Fragment fragment = new gui_trangchu_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_gui_trangchu_, container, false);
        khoanthu_bll = new bll_khoanthu(requireContext());
        khoanchi_bll = new bll_khoanchi(requireContext());
        Bundle bundle = getArguments();
        if(bundle != null){
            data = bundle.getString("taikhoan");
        }
        anhxa(view);
        txtXinChaoTK.setText("Xin chào "+ data);
        txtTongSoDu.setText(numberFormat.format(khoanthu_bll.getNganSachHT(khoanthu_bll.getTaiKhoan_id(data)))+" vnđ");
        setDataBarChart();
        setDataPieChart();
        return view;
    }

    private void anhxa(View view){
        barChart = view.findViewById(R.id.barChartThuChi);
        pieChart = view.findViewById(R.id.PiaChart);
        txtTongSoDu = view.findViewById(R.id.txtTongSoDu);
        txtXinChaoTK = view.findViewById(R.id.txtXinChaoTK);
    }

    private void setDataBarChart(){
        String ngay = String.valueOf(LocalDate.now());
        Float khoanthu = khoanthu_bll.getSumNganSachToday(khoanthu_bll.getTaiKhoan_id(data), ngay);
        Float khoanchi = khoanchi_bll.getSumChiPhiToday(khoanthu_bll.getTaiKhoan_id(data), ngay);
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0, khoanthu));
        barEntries.add(new BarEntry(1, khoanchi));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Thu | Chi");
        barDataSet.setColors(new int[]{android.graphics.Color.GREEN, android.graphics.Color.RED});
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);



        Legend legend = barChart.getLegend();
        legend.setEnabled(true);
        legend.setTextSize(14f);

        barChart.getDescription().setEnabled(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);

        barChart.invalidate();
    }

    private void setDataPieChart(){
        String ngay = String.valueOf(LocalDate.now());
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        Cursor cursor = khoanchi_bll.getSumChiTieu_DanhMucToDay(khoanthu_bll.getTaiKhoan_id(data), ngay);

        if(cursor.moveToFirst()){
            do{
                String danhmuc = khoanchi_bll.getDanhMuc_ten(cursor.getInt(0));
                Float sotien = cursor.getFloat(1);
                pieEntries.add(new PieEntry(sotien, danhmuc));
            }while(cursor.moveToNext());
        }
        cursor.close();

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setUsePercentValues(true);
        pieChart.setCenterText("Chi tiêu hôm nay");
        pieChart.setCenterTextSize(18f);

        pieChart.invalidate();
    }
}