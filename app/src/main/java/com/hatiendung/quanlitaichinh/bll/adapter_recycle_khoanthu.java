package com.hatiendung.quanlitaichinh.bll;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hatiendung.quanlitaichinh.R;
import com.hatiendung.quanlitaichinh.dto.dto_KhoanThu;

import java.util.ArrayList;

public class adapter_recycle_khoanthu extends RecyclerView.Adapter<adapter_recycle_khoanthu.ViewHolderKhoanThu>{
    private Context context;
    private ArrayList<dto_KhoanThu> arrKhoanThu;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(dto_KhoanThu khoanthu);
    }

    public adapter_recycle_khoanthu(Context context, ArrayList<dto_KhoanThu> arrKhoanThu, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.arrKhoanThu = arrKhoanThu;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public adapter_recycle_khoanthu.ViewHolderKhoanThu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycle_khoanthu, parent, false);
        return new ViewHolderKhoanThu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_recycle_khoanthu.ViewHolderKhoanThu holder, int position) {
        dto_KhoanThu khoanthu = arrKhoanThu.get(position);
        holder.id.setText(String.valueOf(khoanthu.getId()));
        holder.taikhoan_id.setText(String.valueOf(khoanthu.getTaikhoan_id()));
        holder.nguonthu.setText(khoanthu.getNguonthu());
        holder.sotien.setText(String.valueOf(khoanthu.getSotien()));
        holder.ngay.setText(khoanthu.getNgay());
        holder.mota.setText(khoanthu.getMota());

        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(khoanthu));
    }

    @Override
    public int getItemCount() {
        return arrKhoanThu.size();
    }

    public static class ViewHolderKhoanThu extends RecyclerView.ViewHolder{
        TextView id, taikhoan_id, sotien, nguonthu, ngay, mota;
        ImageView iconKhoanThu;
        public ViewHolderKhoanThu(@NonNull View itemView){
            super(itemView);
            iconKhoanThu = itemView.findViewById(R.id.itemiconKhoanChi);
            id = itemView.findViewById(R.id.item_idKhoanThu);
            taikhoan_id = itemView.findViewById(R.id.item_idTaiKhoanKT);
            sotien = itemView.findViewById(R.id.itemSoTienKhoanThu);
            nguonthu = itemView.findViewById(R.id.item_NguonThu);
            ngay = itemView.findViewById(R.id.itemNgayKhoanThu);
            mota = itemView.findViewById(R.id.itemMoTaKT);
        }
    }
}
