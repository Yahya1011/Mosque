package com.example.mosque.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.mosque.R
import com.example.mosque.common.Constans
import com.example.mosque.extention.getProgressDrawable
import com.example.mosque.extention.loadImage
import com.example.mosque.model.*
import com.example.mosque.view.LaporanActivity
import kotlinx.android.synthetic.main.row_masjidsekitar.view.*

class SearchEngineAdapter(var masjid: MutableList<Mosque>) :
    RecyclerView.Adapter<SearchEngineAdapter.MasjidViewHolder>(), Filterable {

    private var context: Context? = null
    private var masjidListFilter: MutableList<Mosque>

    init {
        masjidListFilter = masjid
    }

    private var mOnItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemSelected(masjides: Mosque)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MasjidViewHolder {
        context = parent.context
        val layoutInflate = LayoutInflater.from(context).inflate(R.layout.row_searchengine, parent, false)
        return MasjidViewHolder(layoutInflate)
    }

    override fun getItemCount(): Int = masjidListFilter.size


    override fun onBindViewHolder(holder: MasjidViewHolder, position: Int) {
        holder.clear()
        holder.bind(position)
    }

    fun updateMasjid(masjidApiUpdate: List<Mosque>) {
        this.masjid.clear()
        this.masjid.addAll(masjidApiUpdate)
        notifyDataSetChanged()

    }

    internal fun setOnItemClickListener(listener: OnItemClickListener) {
        mOnItemClickListener = listener
    }

    inner class MasjidViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        override fun onClick(v: View?) {
            mOnItemClickListener?.onItemSelected(masjidListFilter[adapterPosition])
        }


        fun clear() {
            itemView.titleTv.text = ""
            itemView.descTv.text = ""
            itemView.jarakTV.text = "search"
            itemView.iconIv.setImageDrawable(null)
        }

        fun bind(position: Int) {
            val (
                id,
                type,
                code,
                name,
                identity,
                surfaceArea,
                buildingArea,
                los,
                since,
                rek,
                address,
                latitude,
                longitude,
                estimate,
                estimateDate,
                pic,
                description,
                bank,
                province,
                regency,
                district,
                village
            ) = masjidListFilter[position]
            inflateData(
                id,
                type,
                code,
                name,
                identity,
                surfaceArea,
                buildingArea,
                los,
                since,
                rek,
                address,
                latitude,
                longitude,
                estimate,
                estimateDate,
                pic,
                description,
                bank,
                province,
                regency,
                district,
                village
            )
        }

        private fun inflateData(
            id: Int,
            type: String,
            code: String,
            name: String,
            identity: String,
            surfaceArea: String,
            buildingArea: String,
            los: String,
            since: String,
            rek: String,
            address: String,
            latitude: String,
            longitude: String,
            estimate: String,
            estimateDate: String,
            pic: String,
            description: String,
            bank: Bank,
            province: Province,
            regency: Regency,
            district: District,
            village: Village
        ) {
            val progressDrawable: CircularProgressDrawable = getProgressDrawable(itemView.context)
            val imgTarget = Constans.imageUrlPath

            name.let {
                itemView.titleTv.text = it
            }
            address.let {
                itemView.descTv.text = it
//                    String.format("%s, %s, %s", it, province.provinceName, regency.regencyName, district.districtName)
            }
            pic.let {
                itemView.iconIv.loadImage(imgTarget + it, progressDrawable)
            }

            id.let {
                itemView.setOnClickListener {
                    val intent = Intent(context, LaporanActivity::class.java)
                    intent.putExtra("key", id)
                    context!!.startActivity(intent)
                }
            }
        }
    }

    //GET MAsJID FOR SEARCH OPTIONS
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    masjidListFilter = masjid
                } else {
                    val filteredList: MutableList<Mosque> = ArrayList()
//                    println("DATA ${listOf(filteredList)}")
                    for (row in masjid) {
                        if (row.name.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    masjidListFilter = filteredList
                }
                val filterResults = FilterResults()

                filterResults.values = masjidListFilter
//                println("DATA ADAPTER ${filterResults}")
                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                masjidListFilter = results.values as ArrayList<Mosque>
                notifyDataSetChanged()
            }
        }
    }

}