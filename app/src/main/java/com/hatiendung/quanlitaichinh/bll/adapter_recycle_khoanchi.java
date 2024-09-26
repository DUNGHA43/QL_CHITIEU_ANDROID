package com.hatiendung.quanlitaichinh.bll;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hatiendung.quanlitaichinh.R;
import com.hatiendung.quanlitaichinh.dto.dto_KhoanChi;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class adapter_recycle_khoanchi extends RecyclerView.Adapter<adapter_recycle_khoanchi.ViewHolderKhoanChi> {
    private Context context;
    private ArrayList<dto_KhoanChi> arrKhoanChi;
    private OnItemClickListener onItemClickListener;
    private NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
    public interface OnItemClickListener {
        void onItemClick(dto_KhoanChi khoanchi);
    }

    public adapter_recycle_khoanchi(Context context, ArrayList<dto_KhoanChi> arrKhoanChi, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.arrKhoanChi = arrKhoanChi;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolderKhoanChi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycle_khoanchi, parent, false);
        return new ViewHolderKhoanChi(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderKhoanChi holder, int position) {
        dto_KhoanChi khoanchi = arrKhoanChi.get(position);
        holder.id.setText(khoanchi.getId()+"");
        holder.taikhoan_id.setText(khoanchi.getTaikhoan_id()+"");
        holder.danhmuc_id.setText(khoanchi.getDanhmuc_id()+"");
        holder.sotien.setText(numberFormat.format(khoanchi.getSotien())+" vnđ");
        holder.ngay.setText(khoanchi.getNgay());
        holder.mota.setText(khoanchi.getMota());
        switch (holder.danhmuc_id.getText().toString()){
            case "1":
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.food);
                holder.iconKhoanChi.setImageBitmap(bitmap);
                break;
            case "2":
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.car);
                holder.iconKhoanChi.setImageBitmap(bitmap);
                break;
            case "3":
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.shirt);
                holder.iconKhoanChi.setImageBitmap(bitmap);
                break;
            case "4":
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.health);
                holder.iconKhoanChi.setImageBitmap(bitmap);
                break;
            case "5":
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.service);
                holder.iconKhoanChi.setImageBitmap(bitmap);
                break;
            case "6":
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.insurance);
                holder.iconKhoanChi.setImageBitmap(bitmap);
                break;
            case "7":
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.beautify);
                holder.iconKhoanChi.setImageBitmap(bitmap);
                break;
            case "8":
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.loan);
                holder.iconKhoanChi.setImageBitmap(bitmap);
                break;
        }

        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(khoanchi));
    }

    @Override
    public int getItemCount() {
        return arrKhoanChi.size();
    }


    public static class ViewHolderKhoanChi extends RecyclerView.ViewHolder{
        TextView id, taikhoan_id, danhmuc_id, sotien, ngay, mota;
        ImageView iconKhoanChi;
        public ViewHolderKhoanChi(@NonNull View itemView){
            super(itemView);
            iconKhoanChi = itemView.findViewById(R.id.itemiconKhoanChi);
            id = itemView.findViewById(R.id.item_idKhoanChi);
            taikhoan_id = itemView.findViewById(R.id.item_idTaiKhoanKC);
            danhmuc_id = itemView.findViewById(R.id.item_idDanhMucKC);
            sotien = itemView.findViewById(R.id.itemSoTienKhoanChi);
            ngay = itemView.findViewById(R.id.itemNgayKhoanChi);
            mota = itemView.findViewById(R.id.itemMoTaKC);
        }
    }
}
