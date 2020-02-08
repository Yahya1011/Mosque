package com.example.mosque.fragment

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mosque.R
import com.example.mosque.adapter.FasilitasAdapter
import com.example.mosque.model.FasilitasString
import com.example.mosque.view.fragment.HomeFragment
import com.example.mosque.viewmodel.HomeViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_filter.*

class FasilitasFragment : BottomSheetDialogFragment() {

    companion object {

        var mFasilitas: MutableList<FasilitasString> = mutableListOf()
        fun newInstance(): FasilitasFragment {
            return FasilitasFragment()
        }
        fun newInstance(listFasilitas: List<FasilitasString>): FasilitasFragment {
            mFasilitas = listFasilitas.toMutableList()
            return FasilitasFragment()
        }
    }

    private val mainActivity: HomeFragment = HomeFragment()
    private var mAdapterFasilitas = FasilitasAdapter(ArrayList())
    private lateinit var dialog: BottomSheetDialog
    private lateinit var behavior: BottomSheetBehavior<View>
    lateinit var fragmentView: View
    lateinit var viewModel: HomeViewModels

    var valueSelected = 0


    var kategoriName: String = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = BottomSheetDialog(requireActivity(), theme)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setOnShowListener(object : DialogInterface.OnShowListener {
            override fun onShow(dialog: DialogInterface?) {
                val d = dialog as BottomSheetDialog
                val sheet = d.findViewById<View>(R.id.design_bottom_sheet)
                behavior = BottomSheetBehavior.from(sheet!!)
                behavior.isHideable = false
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        })
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.bottom_sheet_filter, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycle(mFasilitas) //GET BUTTON FILTER
    }

    private fun initRecycle(mFasilitas: List<FasilitasString>) {
        mAdapterFasilitas.updateListFasilitas(mFasilitas)
        recycle_filter.adapter = mAdapterFasilitas
        recycle_filter.setHasFixedSize(true)
        recycle_filter.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        println("CEK $mFasilitas")

        mAdapterFasilitas.setOnItemClickListener(object : FasilitasAdapter.OnItemClickListener {
            override fun onItemSelected(kategori: FasilitasString) {
                setPassData(kategori.name)
            }

        })

        //GET BUTTON FILTER
        btn_filter_fasilitas.setOnClickListener {

            //            println ("DATA BOTTOM HAHA $kategoriName")
            val stringBuilder = StringBuilder()
            for (checkedFasilitas in mFasilitas) {
                if (checkedFasilitas.isSelected )  {
                    if (stringBuilder.isNotEmpty()) stringBuilder.append(", ")

                    stringBuilder.append(checkedFasilitas.name)

                }
            }
            Toast.makeText(context, stringBuilder.toString(), Toast.LENGTH_LONG).show()
            mainActivity.onClickEventPassData("CLICK FILTER BLA ${stringBuilder}")
            dialog.dismiss()
        }
    }


    private fun setPassData(name: String): String {
        kategoriName = name
        return kategoriName
    }

}