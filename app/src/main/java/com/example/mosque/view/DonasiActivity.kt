package com.example.mosque.view

import android.content.Intent
import android.os.Bundle
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
    var mAnonymousToken : String =  "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiYjU1M2VkNjc2ODgxZDFhMjkwZDc3YzE3MGYzOWMyNjRjZDgzM2Y4ZTllYjViOGJkNDA0NDNlOTI1MThlZGQ2YmE3NmJhOTU4NTJlNTYwMTIiLCJpYXQiOjE1ODAzMzk5NzEsIm5iZiI6MTU4MDMzOTk3MSwiZXhwIjoxNjExOTYyMzcxLCJzdWIiOiIxMDAwOSIsInNjb3BlcyI6W119.vZr5wgn6GsUWi7olDmsCSIfGAIjcv6Hlbsj5f6h6_dlbUQFyUcHCCRtm36OoA8VZ90OSs5qUQn9HC0CW2r_ZaybNonGraNNLzqKeMruhBaZlK18bFQe-AIQheIxrxKTEEk_NhejOAn1i8PZKGo4sFrV2U2nTMYYo0fNLjyWmw5czcwOG6-OmmnBEwwXEe85TlG9mjP5iAjCOhX2Vqj_KZoKZlckuQ5WyIp1WSz7TI_JaNqA07RlSvla3neSeboJetB-6AvYTNidsgc1wy_qZuK9L7r9f5479wU1T-5GeHwYrE9logbtlBlG5cp12BKjxHu3uwI7H2kkvsVPljiEmOhOivcN1wyf8FlM8uaEWNTvSjKHedDd9X2TXvpz2eGQnB1VariB0Fo7bOfu0bkuFQ8Cl1X9iaJMUksaaa8bNu3W10VzzKKpQBvUdKYuPrfJJe0oUcnh7bSWDZ95fc8lU5kGho7PWnmm-fhC8XdUcR9Nolkxt5g5_anArLR41jawLvTqmR0ibhsrfyGheV29B9bpIN5V52qJvSuvQGNgv1f0T3l0IgwqNCUl_igGpwOjM1DVl1L3efX4H5FAlX1cRuXOq9PZJ3VF7k47db-ygWU2qF-LJJMFzTIXLF3KLJf-w9WY0J_1RliwHvGYpRbeVKPbdvpKQOH3EMIcdT1rLpzs"
    var mToken : String  = ""
    var mUserId: Int  = 0

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
                mUserId = mPrefData.getCurrentUserId()
            } else {
                mToken = mAnonymousToken
                mUserId = 10009
            }
            donationViewModel.submitDonation(mToken, valueId, mUserId, input_nominal.text.toString(), currentDateandTime, donationSelected, selectedBankProvide)
            println("Data Berhasil di Tambahkan")
            val intent = Intent(this, DetailDonasiActivity::class.java)
            startActivity(intent)
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