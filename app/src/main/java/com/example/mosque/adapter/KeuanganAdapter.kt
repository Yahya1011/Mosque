package com.example.mosque.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mosque.R
import com.example.mosque.model.*
import kotlinx.android.synthetic.main.list_keuangan.view.*

class KeuanganAdapter (val financeDataList: MutableList<LaporanModel>) : RecyclerView.Adapter<KeuanganAdapter.KeuanganHolder>() {

    private var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeuanganHolder {
        context = parent.context
        val itemView = LayoutInflater.from(context).inflate(R.layout.list_keuangan, parent, false)
        return KeuanganHolder(itemView)
    }

    override fun getItemCount(): Int {
        return financeDataList.size
    }

    override fun onBindViewHolder(holder: KeuanganHolder, position: Int) {

        val item = financeDataList[position]

        holder.tanggalKeuangan.text = item.date
        holder.mosqueName.text  = item.mosqueId

        holder.ivArrow.setOnClickListener { onItemClicked(item) }
        if (item.isExpanded!!) {
            holder.childView.visibility = View.VISIBLE
            holder.ivArrow.setImageResource(R.drawable.ic_up_arrow)
        } else {
            holder.childView.visibility = View.GONE
            holder.ivArrow.setImageResource(R.drawable.ic_down_arrow)
        }

    }

    private fun onItemClicked(item: LaporanModel) {
        item.isExpanded = !item.isExpanded!!
        notifyDataSetChanged()
    }

    fun updateData(financeData: List<LaporanModel>) {
        this.financeDataList.clear()
        this.financeDataList.addAll(financeData)
        notifyDataSetChanged()
    }

    inner class KeuanganHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tanggalKeuangan: TextView = itemView.tgl
        var mosqueName : TextView = itemView.tv_nama_masjid
        var childView: LinearLayout = itemView.child_items
        var ivArrow: ImageView = itemView.iv_expand

    }

}


