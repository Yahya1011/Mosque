package com.example.mosque.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.mosque.R
import com.example.mosque.common.Constans
import com.example.mosque.extention.getProgressDrawable
import com.example.mosque.extention.loadImage
import com.example.mosque.model.Mosque
import com.example.mosque.utils.NetworkState
import com.example.mosque.view.LaporanActivity
import kotlinx.android.synthetic.main.item_loading.view.*
import kotlinx.android.synthetic.main.row.view.*
import kotlinx.android.synthetic.main.row.view.descTv
import kotlinx.android.synthetic.main.row.view.iconIv
import kotlinx.android.synthetic.main.row.view.titleTv
import kotlinx.android.synthetic.main.row_masjidsekitar.view.*
import java.text.NumberFormat
import java.util.*

class MasjidSekitarAdapter(val context: Context) : PagedListAdapter<Mosque, RecyclerView.ViewHolder>(MosqueDiffCallback()),
    Filterable {


    val VIEW_TYPE_NORMAL: Int = 1
    val VIEW_TYPE_LOADING: Int = 2

    private var networkState: NetworkState? = null
    private lateinit var masjidListFilter: MutableList<Mosque>




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == VIEW_TYPE_NORMAL) {
            view = layoutInflater.inflate(R.layout.row_masjidsekitar, parent, false)
            return ContentHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.item_loading, parent, false)
            return LodingHolder(view)
        }

    }


    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }


    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_NORMAL
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_NORMAL) {
            (holder as ContentHolder).onBind(getItem(position), context)
        } else {
            (holder as LodingHolder).onBind(networkState)
        }

    }


    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {                             //hadExtraRow is true and hasExtraRow false
                notifyItemRemoved(super.getItemCount())    //remove the progressbar at the end
            } else {                                       //hasExtraRow is true and hadExtraRow false
                notifyItemInserted(super.getItemCount())   //add the progressbar at the end
            }
        } else if (hasExtraRow && previousState != newNetworkState) { //hasExtraRow is true and hadExtraRow true and (NetworkState.ERROR or NetworkState.ENDOFLIST)
            notifyItemChanged(itemCount - 1)       //add the network message at the end
        }

    }

    class MosqueDiffCallback : DiffUtil.ItemCallback<Mosque>() {
        override fun areItemsTheSame(oldItem: Mosque, newItem: Mosque): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Mosque, newItem: Mosque): Boolean {
            return oldItem == newItem
        }
    }


    class ContentHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun onBind(mosque: Mosque?, context: Context) {
            val progressDrawable: CircularProgressDrawable = getProgressDrawable(context)
            val imgTarget = Constans.imageUrlPath
            val localeID = Locale("in", "ID")
            val numFormat: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
            mosque?.name.let {
                itemView.titleTv.text = it
            }

            mosque?.pic.let {
                itemView.iconIv.loadImage(imgTarget + it, progressDrawable)
            }

            mosque?.address.let {
                itemView.descTv.text = it
            }

            mosque?.let {
                itemView.descTiming.text = "otw"
            }

            itemView.btn_detail.setOnClickListener {
                val intent = Intent(context, LaporanActivity::class.java)
                intent.putExtra("key", mosque?.id)
                context.startActivity(intent)
            }
        }

    }

    class LodingHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun onBind(networkState: NetworkState?) {
            if (networkState != null && networkState == NetworkState.LOADING) {
                itemView.progressBar.visibility = View.VISIBLE;
            } else {
                itemView.progressBar.visibility = View.GONE;
            }

            if (networkState != null && networkState == NetworkState.ERROR) {
                itemView.error_msg_item.visibility = View.VISIBLE;
                itemView.error_msg_item.text = networkState.msg;
            } else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
                itemView.error_msg_item.visibility = View.VISIBLE;
                itemView.error_msg_item.text = networkState.msg;
            } else {
                itemView.error_msg_item.visibility = View.GONE;
            }
        }

    }

    //GET MAsJID FOR SEARCH OPTIONS
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    masjidListFilter
                } else {
                    val filteredList: MutableList<Mosque> = ArrayList()
//                    println("DATA ${listOf(filteredList)}")
                    for (row in masjidListFilter) {
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