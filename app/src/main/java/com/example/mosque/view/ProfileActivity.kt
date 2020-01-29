package com.example.mosque.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.mosque.R
import com.example.mosque.extention.getProgressDrawable
import com.example.mosque.extention.loadAsset
import com.example.mosque.helper.AppPreferencesHelper
import kotlinx.android.synthetic.main.activity_keuangan.*
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    lateinit var mPrefData: AppPreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        mPrefData = AppPreferencesHelper(this)
        loadData()
        initClickListener()

        loadData()
    }

    private fun loadData() {


        val progressDrawable: CircularProgressDrawable = getProgressDrawable(this)
        iv_profile.loadAsset(R.drawable.ic_user, progressDrawable)
        tv_fullname.text = mPrefData.getFullname()
        tv_user_role.text = mPrefData.getRoleUser()
        tv_username.text = String.format("Username : %s", mPrefData.getUsername())
        tv_email_address.text =  String.format("Username : %s", mPrefData.getCurrentUserEmail())

    }

    private fun initClickListener() {

    }


}