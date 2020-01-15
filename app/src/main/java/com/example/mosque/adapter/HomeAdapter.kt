package com.example.mosque.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.mosque.R
import com.example.mosque.common.Constans
import com.example.mosque.model.Bank
import com.example.mosque.model.Mosque
import com.example.mosque.model.Province
import com.example.mosque.extention.getProgressDrawable
import com.example.mosque.extention.loadImage
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
            val (
                mosqueId,
                mosqueType,
                mosqueCode,
                mosqueName,
                mosqueIdentity,
                surfaceArea,
                buildingArea,
                mosqueLos,
                mosqueSince,
                bankId,
                mosqueRek,
                mosqueAddress,
                latitude,
                longitude,
                provinceId,
                estimate,
                estimateDate,
                cityId,
                kecId,
                kelId,
                pic,
                description,
                bank,
                province,
                regency,
                districts,
                village) = masjidItems[position]

            inflateData(
                mosqueId,
                mosqueType,
                mosqueCode,
                mosqueName,
                mosqueIdentity,
                surfaceArea,
                buildingArea,
                mosqueLos,
                mosqueSince,
                bankId,
                mosqueRek,
                mosqueAddress,
                latitude,
                longitude,
                provinceId,
                estimate,
                estimateDate,
                cityId,
                kecId,
                kelId,
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
            mosqueId: Int,
            mosqueType: String,
            mosqueCode: String,
            mosqueName: String,
            mosqueIdentity: String,
            surfaceArea: String,
            buildingArea: String,
            mosqueLos: String,
            mosqueSince: String,
            bankId: String,
            mosqueRek: String,
            mosqueAddress: String,
            latitude: String,
            longitude: String,
            provinceId: String,
            estimate: String,
            estimateDate: String,
            cityId: String,
            kecId: String,
            kelId: String,
            pic: String,
            description: String,
            bank: Bank,
            province: Province,
            regency: String,
            districts: String,
            village: String
        ) {

            val progressDrawable: CircularProgressDrawable = getProgressDrawable(itemView.context)
            val imgTarget = Constans.imageUrlPath
            mosqueName.let {
                itemView.titleTv.text = it
            }
            pic.let {
                itemView.iconIv.loadImage(imgTarget + it, progressDrawable)
            }

            mosqueAddress.let {
                itemView.descTv.text = it
            }

        }

    }

}