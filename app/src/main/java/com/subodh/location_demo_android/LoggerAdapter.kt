package com.subodh.location_demo_android

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class LoggerAdapter(
    var mContext: Context,
    var arrayList: ArrayList<String>
) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.logger_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvLatLng.text = arrayList[position] + ""
        val rnd = Random()
        val currentColor =
            Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        holder.tvLatLng.setTextColor(currentColor)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

}