package com.subodh.location_demo_android;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView tvLatLng;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        tvLatLng = itemView.findViewById(R.id.tvLatLng);
    }
}
