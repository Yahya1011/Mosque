package com.example.mosque.view

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.mosque.R
import com.example.mosque.common.Constans
import com.example.mosque.extention.getProgressDrawable
import com.example.mosque.extention.loadImage
import com.example.mosque.utils.showToast
import com.example.mosque.viewmodel.DonationViewModel
import com.tiper.MaterialSpinner
import kotlinx.android.synthetic.main.activity_donasi.*
import kotlinx.android.synthetic.main.activity_donasi.view.*


class DonasiActivity : AppCompatActivity(), MaterialSpinner.OnItemSelectedListener{

    private var donationSelected: Int = 0
    lateinit var donation: Array<String>
    var mDonation: ArrayAdapter<String>? = null
    var valueId: Int = 0
    var imgTarget: String = ""
    var progressDrawable: CircularProgressDrawable? = null

    lateinit var donationViewModel: DonationViewModel





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donasi)

        reciveData()
        donationViewModel = ViewModelProvider(this)[DonationViewModel::class.java]
        donationViewModel.refresh(valueId)
        observeViewModel()
        donation = resources.getStringArray(R.array.donasi_category)
        initSpinnerData()

        initClickLitener()

    }

    private fun initClickLitener() {
        btn_donasi.setOnClickListener{
            submitDonationData()
        }
    }

    private fun submitDonationData() {

        if (donationSelected == 0 ){
            showToast(this,"Maaf anda belum menetukan jenis donasi anda!!")
            spinner_donation.requestFocus()
            
            println("Selected Donatin Index = $donationSelected")
        } else {
            //donationViewModel.submitDonation()
            println("Selected Donatin Index = $donationSelected")
        }

    }

    private fun initSpinnerData() {

        ArrayAdapter.createFromResource(this, R.array.donasi_category, android.R.layout.simple_spinner_item).let {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_donation.apply {
                adapter = it
                onItemSelectedListener = this@DonasiActivity
                onFocusChangeListener = View.OnFocusChangeListener{ _, hasFocus ->
                    Log.v("MaterialSpinner", "onFocusChange hasFocus=$hasFocus")
                }
            }
        }



    }

    private fun reciveData() : Int {
        val extras = intent.extras
        if (extras != null) {
            valueId = extras.getInt("key")
        }

        return valueId

    }

    private fun observeViewModel(){
        donationViewModel.mosquesData.observe(this, Observer { mosques->
            mosques?.let {
                println("DATA recive API ${it.mosqueName}")
                tv_masjid_name.text =it.mosqueName
                alamatMasjid.text = it.address
                progressDrawable= getProgressDrawable(this)
                imgTarget = Constans.imageUrlPath
                it.pic.let {
                    iv_masjid.loadImage(imgTarget + it, progressDrawable!!)
                }

                if (it.mosqueType ==  "0"){
                        tipe_masjid.text =getString(R.string.tipe_masjid)

                } else {
                        tipe_masjid.text =getString(R.string.tipe_mushola)
                }
                tahun_berdiri.text = String.format("Tahun Berdiri : Sejak Tahun %s", it.since)

            }
        })


    }

    override fun onItemSelected(parent: MaterialSpinner, view: View?, position: Int, id: Long) {
        if (parent.id == 0) {
            showToast(this,"Maaf anda belum menetukan jenis donasi anda!!")

            parent.focusSearch(View.FOCUS_UP)?.requestFocus()

        }else {
            println("onItemSelected parent=${parent.id}, position=$position")
            donationSelected = position
        }
    }

    override fun onNothingSelected(parent: MaterialSpinner) {
        showToast(this,"Maaf anda belum menetukan jenis donasi anda!!")
    }

    /* override fun onNothingSelected(parent: AdapterView<*>?) {

     }

     override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

     }*/
}