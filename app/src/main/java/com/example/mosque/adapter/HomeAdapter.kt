package com.example.mosque.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mosque.R
import com.example.mosque.model.Bank
import com.example.mosque.model.Mosque
import com.example.mosque.model.Province
import kotlinx.android.synthetic.main.row.view.*

class HomeAdapter(var masjidItems: MutableList<Mosque>) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {


    var mContext: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        mContext = parent.context
        val layoutInflater = LayoutInflater.from(mContext).inflate(R.layout.row, parent, false)
        return HomeViewHolder(layoutInflater)
    }

    override fun getItemCount(): Int {
        return masjidItems.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.clear()
        holder.onBind(position)
    }

    fun updateHome(updateList: List<Mosque>) {
        this.masjidItems.clear()
        this.masjidItems.addAll(updateList)
        notifyDataSetChanged()
    }

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun clear() {
        }

        fun onBind(position: Int) {
            val (id,
                type,
                code,
                name,
                identity,
                surface_area,
                building_area,
                los,
                since,
                bank_id,
                rek,
                address,
                latitude,
                longitude,
                province_id,
                estimate,
                estimate_date,
                city_id,
                kec_id,
                kel_id,
                pic,
                description,
                bank,
                province,
                regency,
                districts,
                village) = masjidItems[position]

            inflateData(
                id,
                type,
                code,
                name,
                identity,
                surface_area,
                building_area,
                los,
                since,
                bank_id,
                rek,
                address,
                latitude,
                longitude,
                province_id,
                estimate,
                estimate_date,
                city_id,
                kec_id,
                kel_id,
                pic,
                description,
                bank,
                province,
                regency,
                districts,
                village


            )
        }

        private fun inflateData(
            id: Int,
            type: String,
            code: String,
            name: String,
            identity: String,
            surface_area: String,
            building_area: String,
            los: String,
            since: Int,
            bank_id: Int,
            rek: String,
            address: String,
            latitude: String,
            longitude: String,
            province_id: Int,
            estimate: String,
            estimate_date: String,
            city_id: Int,
            kec_id: Int,
            kel_id: String,
            pic: String,
            description: String,
            bank: Bank,
            province: Province,
            regency: String,
            districts: String,
            village: String
        ) {
            name.let {
                itemView.titleTv.text = it
            }
        }

    }

}