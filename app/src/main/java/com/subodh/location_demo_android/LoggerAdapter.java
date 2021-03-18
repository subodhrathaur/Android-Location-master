package com.subodh.location_demo_android;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class LoggerAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context mContext;
    ArrayList<String> arrayList;

    public LoggerAdapter(Context mContext, ArrayList<String> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.logger_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvLatLng.setText(arrayList.get(position) + "");
        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        holder.tvLatLng.setTextColor(currentColor);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
