package com.example.mosque.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mosque.R
import com.example.mosque.adapter.KeuanganAdapter
import com.example.mosque.adapter.LaporanAdapter
import com.example.mosque.viewmodel.KeuanganViewModel
import kotlinx.android.synthetic.main.activity_keuangan.*

class KeuanganActivity : AppCompatActivity() {

    var valueId: Int = 0
    lateinit var keuanganViewModel: KeuanganViewModel
    private val keuanganAdapter = LaporanAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keuangan)
        reciveData()
        keuanganViewModel = ViewModelProvider(this)[KeuanganViewModel::class.java]
        keuanganViewModel.refreshKeuangan(valueId)
        initAdapter()
    }

    private fun reciveData() : Int {
        val extras = intent.extras
        when {
            extras != null -> {
                valueId = extras.getInt("key")

                println("DATA KEUANGAN $valueId")
                //The key argument here must match that used in the other activity
            }
        }
        return valueId

    }
    private fun initAdapter() {
        rv_keuangan.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = keuanganAdapter
        }
        observeViewModel()

    }

    private fun observeViewModel() {
        keuanganViewModel.keuanganData.observe(this, Observer { keuanganRespons->
            keuanganRespons?.let {
                println("DATA recive Neraca API ${it.size}")

                rv_keuangan.visibility = View.VISIBLE
                keuanganAdapter.updateData(it)

            }
        })


    }
}