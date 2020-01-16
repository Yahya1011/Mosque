package com.example.mosque.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.mosque.R
import com.example.mosque.extention.getProgressDrawable
import com.example.mosque.extention.loadAsset
import com.example.mosque.extention.loadImageProvider
import com.example.mosque.model.MainNav
import kotlinx.android.synthetic.main.item_main_nav.view.*

class NavMainAdapter(val navData : MutableList<MainNav>) : RecyclerView.Adapter<NavMainAdapter.NavMainHolder>() {

    var mContext: Context? = null

    private var mOnItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemSelected(mainNav: MainNav)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavMainHolder {
        mContext = parent.context
        val layoutInflater = LayoutInflater.from(mContext).inflate(R.layout.item_main_nav, parent, false)
        return NavMainHolder(layoutInflater)
    }

    override fun getItemCount(): Int {
        return navData.size
    }

    override fun onBindViewHolder(holder: NavMainHolder, position: Int) {
        holder.clear()
        holder.onBind(position)
    }

    fun updateNav(updateNavList: List<MainNav>) {
        this.navData.clear()
        this.navData.addAll(updateNavList)
        notifyDataSetChanged()
    }

    internal fun setOnItemClickListener(listener: OnItemClickListener) {
        mOnItemClickListener = listener
    }

    inner class NavMainHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        override fun onClick(p0: View?) {
            mOnItemClickListener?.onItemSelected(navData[adapterPosition])
        }
        fun clear() {

        }

        fun onBind(position: Int) {
            val(nav_icon,
                nav_name) = navData[position]
            inflateData(nav_icon, nav_name)
        }

        private fun inflateData(nav_icon : Int?,
                                nav_name : String?) {




            val progressDrawable: CircularProgressDrawable = getProgressDrawable(itemView.context)
            nav_name.let {
                itemView.navText.text = it
            }

            nav_icon.let {
                if (it != null) {
                    itemView.navIcon.loadAsset( it, progressDrawable)
                }
            }


            itemView.setOnClickListener(this)

        }




    }
}