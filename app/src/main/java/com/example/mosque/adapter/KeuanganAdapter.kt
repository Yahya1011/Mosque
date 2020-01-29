package com.example.mosque.adapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mosque.R
import com.example.mosque.model.FinanceModel
import kotlinx.android.synthetic.main.list_keuangan.view.*

class KeuanganAdapter (val parentDataItems: MutableList<FinanceModel>) : RecyclerView.Adapter<KeuanganAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_keuangan, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return parentDataItems.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val parentDataItem = parentDataItems[position]
        holder.itemView.dropdown.text = parentDataItem.mosqueFinance.toString()

        holder.bind(parentDataItem)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private var context: Context? = null
        override fun onClick(view: View?) {
            if (view?.id == R.id.dropdown) {
                if (itemView.child_items?.visibility == View.VISIBLE) {
                    itemView.child_items?.visibility = View.GONE
                } else {
                    itemView.child_items?.visibility = View.VISIBLE
                }
            } else {
                val textViewClicked = view as TextView
                Toast.makeText(context, "" + textViewClicked.text.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }

        fun bind(parentDataItem: FinanceModel) {
            context = itemView.context
            itemView.child_items?.visibility = View.GONE
            val intMaxNoOfChild = parentDataItem.mosqueFinance.size

            for (indexView in 0 until intMaxNoOfChild) {
                val textView = TextView(context)
                textView.id = indexView
                textView.setPadding(0, 20, 0, 20)
                textView.gravity = Gravity.CENTER
                textView.background = ContextCompat.getDrawable(context!!, R.drawable.background_sub_module_text)
                val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                textView.setOnClickListener(this)
                itemView.child_items?.addView(textView, layoutParams)
            }
            itemView.dropdown?.setOnClickListener(this)


            val noOfChildTextViews = itemView.child_items?.childCount

            val noOfChild = parentDataItem.mosqueFinance.size

            if (noOfChild < noOfChildTextViews!!) {
                for (index in noOfChild until noOfChildTextViews) {
                    val currentTextView = itemView.child_items!!.getChildAt(index) as TextView
                    currentTextView.visibility = View.GONE
                }
            }
            for (textViewIndex in 0 until noOfChild) {
                val currentTextView = itemView.child_items!!.getChildAt(textViewIndex) as TextView
                currentTextView.text = parentDataItem.mosqueFinance[textViewIndex].userId
            }

        }
    }

}


