package com.example.mosque.view

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mosque.R
import com.example.mosque.helper.AppPreferencesHelper
import com.example.mosque.utils.showToast
import com.example.mosque.viewmodel.FinanceViewModel
import com.tiper.MaterialSpinner
import kotlinx.android.synthetic.main.activity_finance.*
import java.text.SimpleDateFormat
import java.util.*


class FinanceActivity : AppCompatActivity(), MaterialSpinner.OnItemSelectedListener {

    lateinit var kategori: Array<String>
    lateinit var subKategori: Array<String>
    private var categorySelected: String = ""
    private var subCategorySelected: String = ""
    private var kategoriSelected: Int = 0
    var mAnonymousToken: String =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiYjU1M2VkNjc2ODgxZDFhMjkwZDc3YzE3MGYzOWMyNjRjZDgzM2Y4ZTllYjViOGJkNDA0NDNlOTI1MThlZGQ2YmE3NmJhOTU4NTJlNTYwMTIiLCJpYXQiOjE1ODAzMzk5NzEsIm5iZiI6MTU4MDMzOTk3MSwiZXhwIjoxNjExOTYyMzcxLCJzdWIiOiIxMDAwOSIsInNjb3BlcyI6W119.vZr5wgn6GsUWi7olDmsCSIfGAIjcv6Hlbsj5f6h6_dlbUQFyUcHCCRtm36OoA8VZ90OSs5qUQn9HC0CW2r_ZaybNonGraNNLzqKeMruhBaZlK18bFQe-AIQheIxrxKTEEk_NhejOAn1i8PZKGo4sFrV2U2nTMYYo0fNLjyWmw5czcwOG6-OmmnBEwwXEe85TlG9mjP5iAjCOhX2Vqj_KZoKZlckuQ5WyIp1WSz7TI_JaNqA07RlSvla3neSeboJetB-6AvYTNidsgc1wy_qZuK9L7r9f5479wU1T-5GeHwYrE9logbtlBlG5cp12BKjxHu3uwI7H2kkvsVPljiEmOhOivcN1wyf8FlM8uaEWNTvSjKHedDd9X2TXvpz2eGQnB1VariB0Fo7bOfu0bkuFQ8Cl1X9iaJMUksaaa8bNu3W10VzzKKpQBvUdKYuPrfJJe0oUcnh7bSWDZ95fc8lU5kGho7PWnmm-fhC8XdUcR9Nolkxt5g5_anArLR41jawLvTqmR0ibhsrfyGheV29B9bpIN5V52qJvSuvQGNgv1f0T3l0IgwqNCUl_igGpwOjM1DVl1L3efX4H5FAlX1cRuXOq9PZJ3VF7k47db-ygWU2qF-LJJMFzTIXLF3KLJf-w9WY0J_1RliwHvGYpRbeVKPbdvpKQOH3EMIcdT1rLpzs"
    var mToken: String = ""
    var categoryId: Int = 0
    var subCategoryId: Int = 0
    var mUserId: Int = 0
    lateinit var datePickerDialog : DatePickerDialog

    lateinit var keuanganViewModel: FinanceViewModel
    lateinit var mPrefData: AppPreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finance)

        mPrefData = AppPreferencesHelper(this)
        keuanganViewModel = ViewModelProvider(this)[FinanceViewModel::class.java]

        kategori = resources.getStringArray(R.array.finance_category)
        subKategori = resources.getStringArray(R.array.finance_sub_category)
        initSpinnerData()
        initClickLitener()
        inputDate() //datepicker
    }

    private fun inputDate() {
        input_date.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                // calender class's instance and get current date , month and year from calender
                val c = Calendar.getInstance()
                val mYear = c[Calendar.YEAR] // current year

                val mMonth = c[Calendar.MONTH] // current month

                val mDay = c[Calendar.DAY_OF_MONTH] // current day

                // date picker dialog
                datePickerDialog = DatePickerDialog(
                    this@FinanceActivity,
                    OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        // set day of month , month and year value in the edit text
                        input_date.setText(
                            dayOfMonth.toString() + "/"
                                    + (monthOfYear + 1) + "/" + year
                        )
                    }, mYear, mMonth, mDay
                )
                datePickerDialog.show()
            }

        })
    }

    private fun initSpinnerData() {
        ArrayAdapter.createFromResource(
            this,
            R.array.finance_category,
            android.R.layout.simple_spinner_item
        ).let {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_category.apply {
                adapter = it
                onItemSelectedListener = this@FinanceActivity
                onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                    Log.v("MaterialSpinner", "onFocusChange hasFocus=$hasFocus")
                }
            }
        }
        ArrayAdapter.createFromResource(
            this,
            R.array.finance_sub_category,
            android.R.layout.simple_spinner_item
        ).let {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_category.apply {
                adapter = it
                onItemSelectedListener = this@FinanceActivity
                onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                    Log.v("MaterialSpinner", "onFocusChange hasFocus=$hasFocus")
                }
            }
        }
    }

    override fun onItemSelected(parent: MaterialSpinner, view: View?, position: Int, id: Long) {
        if (parent.id == 0) {
            showToast(this, "Maaf anda belum menetukan jenis donasi anda!!")
            parent.focusSearch(View.FOCUS_UP)?.requestFocus()

        } else {
            kategoriSelected = position
        }
    }

    override fun onNothingSelected(parent: MaterialSpinner) {
        showToast(this, "Maaf anda belum menetukan jenis donasi anda!!")
    }

    private fun initClickLitener() {
        btn_input.setOnClickListener {

            submitFinanceData()
        }
    }

    private fun submitFinanceData() {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val currentDateandTime: String = sdf.format(Date())

        println("Data ${mPrefData.getAccessToken()} ")

        if (categorySelected == "" || subCategorySelected == "" || keterangan.text.toString() == "" || nominal.text.toString() == "") {
            showToast(this, "Maaf terjadi kesalahan!!, Harap isi field yang ada")
            spinner_category.requestFocus()
        } else {
            //2020-01-12 18:48:30

            if (mPrefData.isLoginIn()) {
                mToken = mPrefData.getAccessToken().toString()
                mUserId = mPrefData.getCurrentUserId()
            } else {
                mToken = mAnonymousToken
                mUserId = 10009
            }

            keuanganViewModel.submitFinance(
                mToken,
                categoryId,
                subCategoryId,
                keterangan.text.toString(),
                nominal.text.toString(),
                currentDateandTime
            )
        }
    }
}
