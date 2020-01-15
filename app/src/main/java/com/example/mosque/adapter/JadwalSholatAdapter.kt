package com.example.mosque.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mosque.R
import com.example.mosque.model.JadwalSholat
import com.example.mosque.utils.convertDateFromString
import com.example.mosque.utils.convertDateFromStringToDay
import com.example.mosque.utils.convertTime
import kotlinx.android.synthetic.main.jadwal_item.view.*

class JadwalSholatAdapter(var jadwalItems: MutableList<JadwalSholat>) : RecyclerView.Adapter<JadwalSholatAdapter.JadwalViewHolder>() {

    var mContext: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JadwalViewHolder {
        mContext = parent.context
        val layoutInflater = LayoutInflater.from(mContext).inflate(R.layout.jadwal_item, parent, false)
        return JadwalViewHolder(layoutInflater)
    }

    override fun getItemCount(): Int {
        return jadwalItems.size
    }


    fun updateJadwal(updateList: List<JadwalSholat>) {
        this.jadwalItems.clear()
        this.jadwalItems.addAll(updateList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: JadwalViewHolder, position: Int) {
        holder.clear()
        holder.onBind(position)
    }

    inner class JadwalViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        fun clear() {

        }

        fun onBind(position: Int) {
            val(tanggal,
                fajar,
                subuh,
                zuhur,
                ashar,
                maghrib,
                isya) = jadwalItems[position]
            inflateData(tanggal,
                fajar,
                subuh,
                zuhur,
                ashar,
                maghrib,
                isya)
        }

        private fun inflateData(tanggal : String,
                                fajar :String,
                                subuh : String ,
                                zuhur : String ,
                                ashar : String ,
                                maghrib : String ,
                                isya : String) {

            tanggal.let {
                itemView.tv_hari.text = convertDateFromStringToDay(it)
                itemView.tv_tanggal.text = convertDateFromString(it)
            }



            fajar.let{
                itemView.lbl_waktu_fajar.text = "Fajar"
                itemView.lbl_jam_fajar.text = String.format("Pukul, %s", convertTime(it))
            }

            subuh.let{
                itemView.lbl_waktu_subuh.text = "Shubuh"
                itemView.lbl_jam_subuh.text = String.format("Pukul, %s", convertTime(it))
            }

            zuhur.let{
                itemView.lbl_waktu_dzuhur.text = "Dzuhur"
                itemView.lbl_jam_dzuhur.text = String.format("Pukul, %s", convertTime(it))
            }

            ashar.let{
                itemView.lbl_waktu_ashar.text = "Ashar"
                itemView.lbl_jam_ashar.text = String.format("Pukul, %s", convertTime(it))
            }

            maghrib.let{
                itemView.lbl_waktu_magrib.text = "Magrib"
                itemView.lbl_jam_magrib.text = String.format("Pukul, %s", convertTime(it))
            }

            isya.let{
                itemView.lbl_waktu_isya.text = "Isya"
                itemView.lbl_jam_isya.text = String.format("Pukul, %s", convertTime(it))
            }


        }





    }
}