package com.example.mosque.view

import android.app.AlertDialog
import android.content.Intent
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
import com.example.mosque.helper.AppPreferencesHelper
import com.example.mosque.utils.showToast
import com.example.mosque.view.activity.InputLaporanActivity
import com.example.mosque.viewmodel.LaporanViewModel
import kotlinx.android.synthetic.main.activity_laporan.*


class LaporanActivity : AppCompatActivity() {
    lateinit var mPrefData: AppPreferencesHelper
    private val isLogin: Boolean = false
    var valueId: Int = 0
    lateinit var laporanViewModel: LaporanViewModel
    private val laporanAdapter = LaporanAdapter(ArrayList())

    var progressDrawable: CircularProgressDrawable? = null
    var imgTarget: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan)

        mPrefData = AppPreferencesHelper(this)
        println(mPrefData.isLoginIn())

        fab.setOnClickListener {
            if (mPrefData.isLoginIn() && mPrefData.getRoleUser() == "dkm"){
                showDialog("","Selamat datang ${mPrefData.getFullname()}",200)
            }else if(mPrefData.isLoginIn() && mPrefData.getRoleUser()!= "dkm"){
                showDialog("","Anda tidak memiliki akses yang tepat untuk membuka halaman ini, \nHalaman ini hanya di gunakan untuk pengurus masjid(DKM)",201)
            }else{
                val intent = Intent(this@LaporanActivity, LoginActivity::class.java)
                startActivity(intent)
            }
            /*intent = Intent(this, InputLaporanActivity::class.java)
            startActivity(intent)
            finish()*/
        }

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
                println("DATA recive API ${it.name}")
                titleMosque.text =it.name
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
        when {
            extras != null -> {
                valueId = extras.getInt("key")
                //The key argument here must match that used in the other activity
            }
        }

        return valueId

    }


    private fun showDialog(mTitle: String, msg: String, code: Int){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(msg)
            .setCancelable(false)
            .setPositiveButton("ok") {dialog, _ ->

                if (code == 200){
                    dialog.dismiss()
                    openKeuanganActivity()
                } else {
                    dialog.dismiss()
                }
            }

        val alert = dialogBuilder.create()
        alert.setTitle(mTitle)
        alert.show()
    }

    private fun openKeuanganActivity() {
        val intent = Intent(this@LaporanActivity, KeuanganActivity::class.java)
        intent.putExtra("key", valueId)
        startActivity(intent)
        finish()
    }


}