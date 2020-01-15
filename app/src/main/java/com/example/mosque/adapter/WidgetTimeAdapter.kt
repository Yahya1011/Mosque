package com.example.mosque.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mosque.R
import com.example.mosque.model.Jadwal
import com.example.mosque.model.JadwalSholat
import com.example.mosque.model.Mosque
import kotlinx.android.synthetic.main.waktu_shalat_item.view.*

class WidgetTimeAdapter(val itemDatas : MutableList<Jadwal>) : RecyclerView.Adapter<WidgetTimeAdapter.TimeViewHolder>() {

    var mContext: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        mContext = parent.context
        val layoutInflater = LayoutInflater.from(mContext).inflate(R.layout.waktu_shalat_item, parent, false)
        return TimeViewHolder(layoutInflater)
    }

    override fun getItemCount(): Int {
        return itemDatas.size
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        holder.clear()
        holder.onBind(position)
    }

    fun updateWaktu(updateWaktuList: List<Jadwal>) {
        this.itemDatas.clear()
        this.itemDatas.addAll(updateWaktuList)
        notifyDataSetChanged()
    }

    inner class TimeViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        fun clear() {

        }

        fun onBind(position: Int) {
            val(jadwal,
                status) = itemDatas[position]
            inflateData(jadwal,
                status)
        }

        private fun inflateData(jadwal : List<JadwalSholat>,
                                status : String) {

            for(i in jadwal.indices){

                var waktu = jadwal[i]
                itemView.waktu_sholat.text = waktu.ashar
            }


        }



    }
}