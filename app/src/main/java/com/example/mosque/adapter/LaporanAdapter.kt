package com.example.mosque.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mosque.R
import com.example.mosque.model.LaporanModel
import com.example.mosque.utils.convertDateShortFromString
import kotlinx.android.synthetic.main.item_footer.view.*
import kotlinx.android.synthetic.main.item_header.view.*
import kotlinx.android.synthetic.main.item_header.view.debitlbl
import kotlinx.android.synthetic.main.item_header.view.kreditlbl
import kotlinx.android.synthetic.main.item_laporans.view.*
import java.text.NumberFormat
import java.util.*


class LaporanAdapter (var laporanItems: MutableList<LaporanModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    var mContext: Context? = null
    private val isHeader = false
    private val isFooter = false
    val VIEW_HEADER = 0
    val VIEW_FOOTER = 2
    val VIEW_ITEM = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context

            val contentItem = LayoutInflater.from(mContext).inflate(R.layout.item_laporans, parent, false)
            return ItemContent(contentItem)

    }

    override fun getItemCount(): Int {
        var itemCount = laporanItems.size
        //if header is required then increase the number of count by one
        if (isHeader) {
            itemCount += 1
        }
        if (isFooter) {
            itemCount += 1
        }
        return itemCount
    }


    override fun getItemViewType(position: Int): Int {
        //check if header required then position must be one and return the header view
        return when {
            isHeader && isHeader(position) -> VIEW_HEADER
            isFooter && isFooter(position) -> {
                VIEW_FOOTER
            }
            else -> VIEW_ITEM
        }
    }

    fun isFooter(position: Int): Boolean {
        return position == itemCount - 1
    }

    fun isHeader(position: Int): Boolean {
        return position == 0
    }

    override fun onBindViewHolder(holder:  RecyclerView.ViewHolder, position: Int) {

        if (holder is ItemContent) {
            holder.clear()
            holder.onBind(position)
        } else if (holder is HeaderContent) {
            holder.noLbl.text = "No"
            holder.tglLbl.text = "Tanggal"
            holder.ketLbl.text = "Keterangan"
            holder.debitLbl.text = "Debit"
            holder.kreditLbl.text = "Kredit"
        } else {

        }

    }


    fun updateData(laporanDataItems: List<LaporanModel>) {
        this.laporanItems.clear()
        this.laporanItems.addAll(laporanDataItems)
        notifyDataSetChanged()

    }
    class HeaderContent(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var noLbl: TextView = itemView.nolbl
        var tglLbl: TextView = itemView.tanggallbl
        var ketLbl: TextView = itemView.keteranganlbl
        var debitLbl: TextView = itemView.debitlbl
        var kreditLbl: TextView = itemView.kreditlbl

    }

    inner class ItemContent(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
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
            val pos = adapterPosition+1
            val localeID = Locale("in", "ID")
            val numFormat: NumberFormat = NumberFormat.getCurrencyInstance(localeID)


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
                        itemView.debit.text = numFormat.format(it.toInt()).replace("Rp","Rp ")
                    }

                    itemView.kredit.text = "-"
                }

            } else if (nama == "Pengeluaran") {
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
            }


        }

    }

}