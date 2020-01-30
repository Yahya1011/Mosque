package com.example.mosque.view

import android.os.Bundle
import android.util.JsonToken
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.mosque.R
import com.example.mosque.common.Constans
import com.example.mosque.extention.getProgressDrawable
import com.example.mosque.extention.loadImage
import com.example.mosque.helper.AppPreferencesHelper
import com.example.mosque.utils.showToast
import com.example.mosque.viewmodel.DonationViewModel
import com.tiper.MaterialSpinner
import kotlinx.android.synthetic.main.activity_donasi.*
import java.text.SimpleDateFormat
import java.util.*


class DonasiActivity : AppCompatActivity(), MaterialSpinner.OnItemSelectedListener {


    private var selectedBankProvide: String = ""
    private var donationSelected: Int = 0
    lateinit var donation: Array<String>
    var mDonation: ArrayAdapter<String>? = null
    var valueId: Int = 0
    var imgTarget: String = ""
    var progressDrawable: CircularProgressDrawable? = null
    var mToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNmRkNWM0N2MzYzk2MjU5MWNjZmVjYjk2N2M2ZTIyOTA5NTY5YjQwMzMwNTIwM2EwZmNlYzU1MzEwYTkwMzM1OGY5NThlNmZlOTgzNjQxODYiLCJpYXQiOjE1ODAyMTYxNjQsIm5iZiI6MTU4MDIxNjE2NCwiZXhwIjoxNjExODM4NTY0LCJzdWIiOiIxMDkiLCJzY29wZXMiOltdfQ.IZGmlzoamqaGOOueJ_X_FIXRXMSyVm5DqcjnwVDWZXRNJGgCn7RNL9NE1hmSI1-RhWy5t_2491JxesI_tksU2uACHdeuerzENiF6uZikSSpfAUhBHdqVq21moLhZdXsBFPWaZGhYSaCBwQ75VFt0SmNeS14ms-VWKPRdGJpRScxTmpDldZeMPzlEgUzU-IG7fgQSD-run3mKgLgrFGHXqDvMjhuhkT2vMzsO6QvRSTOIWlc0rC70y8hwsJJGNRqUA4lVRznVvyeCFr_tXwd5dG0rmWCK6KHwfpVC8jMp8fewBRkoFqpYiL1qJ-7eMtwLiOtTOITTD6POJAmq1XlO2Sbn59YNGdelFZmbj8gRQ1RizecquXV9NzIrdyoKJCWNWR2Dv_K___aCgDh6cmjC82c9x1WTgyMNikOk_29AMbfx3GVTAG9sln1Q4khnyOeZjVUmZjStdednFrEGVcG7HUu-QdYex97ecInBGgGbWA1eR1MSb6tPpkPhoI6566O6hCXNQ_mCJErGsqNtgc-RPwtWDuteKuULsiBTEZBLmH9ycAl7oJmXRwhwA3q3-5MVANgwpVOPP1tiEGpJXjl2H88niicycwuMJQV-kpe42sh5JxUHbzRwKe6uZy7251vQUce90fykNF3cU5wwHvTy_DOYq1kcSVBYGqKFqUuus4M"

    lateinit var donationViewModel: DonationViewModel
    lateinit var mPrefData: AppPreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donasi)
        mPrefData =  AppPreferencesHelper(this)
        reciveData()
        donationViewModel = ViewModelProvider(this)[DonationViewModel::class.java]
        donationViewModel.refresh(valueId)

        donation = resources.getStringArray(R.array.donasi_category)
        initSpinnerData()
        initClickLitener()

        initRadioButton()
        observeViewModel()
    }

    private fun initRadioButton() {
        metode_pembayaran.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            selectedBankProvide = radio.text.toString()
        }
    }

    private fun initClickLitener() {
        btn_donasi.setOnClickListener {

            submitDonationData()
        }
    }

    // Get the selected radio button text using radio button on click listener
    fun radio_button_click(view: View) {
        // Get the clicked radio button instance
        val radio: RadioButton = findViewById(metode_pembayaran.checkedRadioButtonId)
    }

    private fun submitDonationData() {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val currentDateandTime: String = sdf.format(Date())

        println("Data ${ mPrefData.getAccessToken()} ")

        if (donationSelected == 0 || selectedBankProvide == "" || input_nominal.text.toString() == "") {
            showToast(this, "Maaf terjadi kesalahan!!, harap input jenis donasi dan bank tujuan")
            spinner_donation.requestFocus()
        } else {
            //2020-01-12 18:48:30

            if (mPrefData.isLoginIn()){
                mToken = mPrefData.getAccessToken().toString()
            }
            donationViewModel.submitDonation(this, valueId, input_nominal.text.toString(), currentDateandTime, donationSelected, selectedBankProvide)
        }
    }

    private fun initSpinnerData() {

        ArrayAdapter.createFromResource(
            this,
            R.array.donasi_category,
            android.R.layout.simple_spinner_item
        ).let {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_donation.apply {
                adapter = it
                onItemSelectedListener = this@DonasiActivity
                onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                    Log.v("MaterialSpinner", "onFocusChange hasFocus=$hasFocus")
                }
            }
        }

    }

    private fun reciveData(): Int {
        val extras = intent.extras
        if (extras != null) {
            valueId = extras.getInt("key")
        }

        return valueId

    }

    private fun observeViewModel() {
        donationViewModel.mosquesData.observe(this, Observer { mosques ->
            mosques?.let {
                println("DATA recive API ${it.name}")
                tv_masjid_name.text = it.name
                alamatMasjid.text = it.address
                progressDrawable = getProgressDrawable(this)
                imgTarget = Constans.imageUrlPath
                it.pic.let {
                    iv_masjid.loadImage(imgTarget + it, progressDrawable!!)
                }

                if (it.type == "0") {
                    tipe_masjid.text = getString(R.string.tipe_masjid)

                } else {
                    tipe_masjid.text = getString(R.string.tipe_mushola)
                }
                tahun_berdiri.text = String.format("Tahun Berdiri : Sejak Tahun %s", it.since)

            }
        })

    }

    override fun onItemSelected(parent: MaterialSpinner, view: View?, position: Int, id: Long) {
        if (parent.id == 0) {
            showToast(this, "Maaf anda belum menetukan jenis donasi anda!!")
            parent.focusSearch(View.FOCUS_UP)?.requestFocus()

        } else {
            donationSelected = position
        }
    }

    override fun onNothingSelected(parent: MaterialSpinner) {
        showToast(this, "Maaf anda belum menetukan jenis donasi anda!!")
    }

}