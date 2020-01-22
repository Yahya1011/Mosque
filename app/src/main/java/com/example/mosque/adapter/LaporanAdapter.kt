package com.example.mosque.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mosque.R
import com.example.mosque.model.LaporanModel
import com.example.mosque.utils.convertDateShortFromString
import com.example.mosque.utils.convertTimeNoTimeZone
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
            val (
                id,
                mosqueFinanceId,
                categoryId,
                subCategoryId,
                information,
                nominal,
                date,
                userId,
                mosqueId,
                nama) = laporanItems[position]

            inflateData(
                id,
                mosqueFinanceId,
                categoryId,
                subCategoryId,
                information,
                nominal,
                date,
                userId,
                mosqueId,
                nama
            )

        }

        private fun inflateData(
            id: Int,
            mosqueFinanceId: String,
            categoryId: String,
            subCategoryId: String,
            information: String,
            nominal: String,
            date: String,
            userId: String,
            mosqueId: String,
            nama: String
            ) {
            var pos = adapterPosition+1
            val localeID = Locale("in", "ID")
            val numFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            itemView.no.text = pos.toString()

            date.let {
                itemView.tanggal.text = convertDateShortFromString(it)
            }

            information.let {
                itemView.keterangan.text = it
            }

            if (nama == "Pendapatan"){
                if (nominal == "0" || nominal == ""){
                    nominal.let {
                        itemView.kredit.text =  "-"
                        itemView.debit.text = "-"
                    }
                }  else {
                    nominal.let {
                        itemView.kredit.text = numFormat.format(it.toInt()).replace("Rp","Rp ")
                    }

                    itemView.debit.text = "-"
                }

            } else if (nama == "Pengeluaran") {
                if (nominal == "0" || nominal == ""){
                    nominal.let {
                        itemView.kredit.text =  "-"
                        itemView.debit.text = "-"
                    }
                }  else {
                    nominal.let {
                        itemView.debit.text = numFormat.format(it.toInt()).replace("Rp","Rp ")
                    }

                    itemView.kredit.text = "-"
                }
            }


        }

    }
}