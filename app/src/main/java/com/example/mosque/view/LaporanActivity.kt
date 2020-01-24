package com.example.mosque.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.mosque.R
import com.example.mosque.adapter.LaporanAdapter
import com.example.mosque.common.Constans
import com.example.mosque.extention.getProgressDrawable
import com.example.mosque.extention.loadImage
import com.example.mosque.viewmodel.LaporanViewModel
import kotlinx.android.synthetic.main.activity_donasi.*
import kotlinx.android.synthetic.main.activity_laporan.*


class LaporanActivity : AppCompatActivity() {
    var valueId: Int = 0
    lateinit var laporanViewModel: LaporanViewModel
    private val laporanAdapter = LaporanAdapter(ArrayList())

    var progressDrawable: CircularProgressDrawable? = null
    var imgTarget: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan)

         reciveData()
        laporanViewModel = ViewModelProvider(this)[LaporanViewModel::class.java]
        laporanViewModel.refresh(valueId)
        laporanViewModel.refreshLaporan(valueId)

        rv_laporan.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = laporanAdapter
        }

        observeViewModel()
    }
    private fun observeViewModel(){
        laporanViewModel.mosquesData.observe(this, Observer { mosques->
            mosques?.let {
                println("DATA recive API ${it.mosqueName}")
                titleMosque.text =it.mosqueName
                sub_title.text = it.address
                 progressDrawable= getProgressDrawable(this)
                 imgTarget = Constans.imageUrlPath
                it.pic.let {
                    image_poster.loadImage(imgTarget + it, progressDrawable!!)
                }





            }
        })

        laporanViewModel.laporan.observe(this, Observer { laporanData ->
            laporanData.let {
                println("DATA LAPORAN ${it.size}")
                rv_laporan.visibility = View.VISIBLE
                laporanAdapter.updateData(it)
            }

        })
    }

    private fun reciveData() : Int {
        val extras = intent.extras
        if (extras != null) {
            valueId = extras.getInt("key")
            //The key argument here must match that used in the other activity
        }

        return valueId

    }





}

