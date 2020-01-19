package com.example.mosque.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mosque.R
import com.example.mosque.model.LaporanModel
import kotlinx.android.synthetic.main.item_laporan.view.*
import java.text.NumberFormat
import java.util.*


class LaporanAdapter (var laporanItems: MutableList<LaporanModel>) : RecyclerView.Adapter<LaporanAdapter.LaporanHolder>() {

    var mContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaporanHolder {
        mContext = parent.context
        val layoutInflater = LayoutInflater.from(mContext).inflate(R.layout.item_laporan, parent, false)
        return LaporanHolder(layoutInflater)
    }

    override fun getItemCount(): Int {
        return laporanItems.size
    }

    override fun onBindViewHolder(holder: LaporanHolder, position: Int) {
        holder.clear()
        holder.onBind(position)
    }

    fun updateData(laporanDataItems: List<LaporanModel>) {

        this.laporanItems.clear()
        this.laporanItems.addAll(laporanDataItems)
        notifyDataSetChanged()

    }

    inner class LaporanHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(p0: View?) {
        }

        fun clear() {

        }

        fun onBind(position: Int) {
            val (tanggal, keterangan, debit, kredit) = laporanItems[position]
            inflateData(tanggal, keterangan, debit, kredit
            )

        }

        private fun inflateData(tanggal: String, keterangan: String, debit: Int, kredit: Int) {
            var pos = adapterPosition+1;
            val localeID = Locale("in", "ID")
            val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
            itemView.no.text = pos.toString()

            tanggal.let {
                itemView.tanggal.text =  it
            }

            keterangan.let {
                itemView.keterangan.text = it
            }

            debit.let {
                itemView.debit.text = formatRupiah.format(it).replace("Rp","Rp ")
            }

            kredit.let {
                itemView.kredit.text = formatRupiah.format(it).replace("Rp","Rp ")
            }

        }

    }
}