package com.subodh.location_demo_android

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvLatLng: TextView = itemView.findViewById(R.id.tvLatLng)

}