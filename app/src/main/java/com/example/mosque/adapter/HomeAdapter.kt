package com.example.mosque.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.mosque.R
import com.example.mosque.common.Constans
import com.example.mosque.extention.getProgressDrawable
import com.example.mosque.extention.loadImage
import com.example.mosque.model.*
import kotlinx.android.synthetic.main.row.view.*

class HomeAdapter(var masjidItems: MutableList<Mosque>) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    var mContext: Context? = null
    private var mOnInfoItemClickListener: HomeAdapter.OnInfoItemClickListener? = null

    private var mOnDonasiItemClickListener: HomeAdapter.OnDonasiItemClickListener? = null

    interface OnInfoItemClickListener {
        fun onItemSelected(masjidItems: Mosque)
    }

    interface OnDonasiItemClickListener {
        fun onItemSelected(masjidItems: Mosque)
    }

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

    internal fun setOnInfoItemClickListener(listener: OnInfoItemClickListener) {
        mOnInfoItemClickListener = listener
    }
    internal fun setOnDonasiItemClickListener(listener: OnDonasiItemClickListener) {
        mOnDonasiItemClickListener = listener
    }

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        override fun onClick(v: View?) {

            if (v == itemView.info){
                mOnInfoItemClickListener?.onItemSelected((masjidItems[adapterPosition]))
            } else if (v == itemView.donasi){
                mOnDonasiItemClickListener?.onItemSelected((masjidItems[adapterPosition]))
            }


        }

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
                los,
                since,
                bankId,
                rek,
                address,
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
                bank  ,
                province,
                regency,
                district,
                village) = masjidItems[position]

            inflateData(
                mosqueId,
                mosqueType,
                mosqueCode,
                mosqueName,
                mosqueIdentity,
                surfaceArea,
                buildingArea,
                los,
                since,
                bankId,
                rek,
                address,
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
                bank  ,
                province,
                regency,
                district,
                village
            )
        }

        private fun inflateData(
            mmosqueId: Int,
            mosqueType: String,
            mosqueCode: String,
            mosqueName: String,
            mosqueIdentity: String,
            surfaceArea: String,
            buildingArea: String,
            los: String,
            since: String,
            bankId: String,
            rek: String,
            address: String,
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
            bank : Bank,
            province: Province,
            regency: Regency,
            district: District,
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

            address.let {
                itemView.descTv.text = it
            }

            itemView.donasi.setOnClickListener(this)
            itemView.info.setOnClickListener(this)

        }

    }

}