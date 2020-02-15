package com.example.mosque.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.mosque.R
import com.example.mosque.common.Constans
import com.example.mosque.extention.getProgressDrawable
import com.example.mosque.extention.loadImage
import com.example.mosque.model.*
import com.example.mosque.utils.NetworkState
import com.example.mosque.utils.convertDateFromString
import com.example.mosque.view.DonasiActivity
import com.example.mosque.view.LaporanActivity
import kotlinx.android.synthetic.main.item_loading.view.*
import kotlinx.android.synthetic.main.row.view.*
import java.text.NumberFormat
import java.util.*


class HomeAdapter(val context: Context) : PagedListAdapter<Mosque, ViewHolder>(MosqueDiffCallback()) {
    
    val VIEW_TYPE_NORMAL: Int = 1
    val VIEW_TYPE_LOADING: Int = 2

    private var networkState: NetworkState? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == VIEW_TYPE_NORMAL) {
            view = layoutInflater.inflate(R.layout.row, parent, false)
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


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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

            mosque?.estimate.let {
                itemView.dana_terkumpul.text = it
                    //numFormat.format(it).replace("Rp","Rp ")
            }
            mosque?.estimateDate.let {
                itemView.tahap_pembangunan.text = convertDateFromString(it)
            }
            mosque?.pic.let {
                itemView.iconIv.loadImage(imgTarget + it, progressDrawable)
            }

            mosque?.address.let {
                itemView.descTv.text = it
            }

            itemView.donasi.setOnClickListener {
                val intent = Intent(context, DonasiActivity::class.java)
                intent.putExtra("key", mosque?.id)
                context.startActivity(intent)
            }
            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val intent = Intent(context, LaporanActivity::class.java)
                    intent.putExtra("key", mosque?.id)
                    context.startActivity(intent)

                }

            })
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
}