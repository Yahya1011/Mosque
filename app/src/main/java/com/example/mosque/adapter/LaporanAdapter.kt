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
            val (
                id,
                mosque_finance_id,
                category_id,
                sub_category_id,
                information,
                nominal,
                mosque_finance,
                category,
                category_sub) = laporanItems[position]

            inflateData(
                id,
                mosque_finance_id,
                category_id,
                sub_category_id,
                information,
                nominal,
                mosque_finance,
                category,
                category_sub
            )

        }

        private fun inflateData(
            id: Int,
            mosque_finance_id: Int,
            category_id: Int,
            sub_category_id: Int,
            information: String,
            nominal: Double,
            mosque_finance: String,
            category: String,
            category_sub: String
            ) {
            var pos = adapterPosition+1;
            val localeID = Locale("in", "ID")
            val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
            itemView.no.text = pos.toString()

            category_id.let {
                itemView.tanggal.text = it.toString()
            }

            information.let {
                itemView.keterangan.text = it
            }
            nominal.let {
                itemView.keterangan.text = it.toString()
            }

//            if (debit ==  0 || debit.equals("null")){
//                debit.let {
//                    itemView.debit.text =  "-"
//                }
//            } else {
//                debit.let {
//                    itemView.debit.text = formatRupiah.format(it).replace("Rp","Rp ")
//                }
//            }
//
//
//            if (kredit  == 0 || kredit.equals("null")){
//                kredit.let {
//                    itemView.kredit.text = "-"
//                }
//            } else {
//                kredit.let {
//                    itemView.kredit.text = formatRupiah.format(it).replace("Rp","Rp ")
//                }
//            }


        }

    }
}