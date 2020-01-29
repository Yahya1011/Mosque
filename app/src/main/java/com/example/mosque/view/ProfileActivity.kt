package com.example.mosque.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mosque.R
import com.example.mosque.helper.AppPreferencesHelper
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    lateinit var mPrefData: AppPreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        loadData()
        initClickListener()
    }

    private fun initClickListener() {
        profile.setOnClickListener {
            intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        keuangan.setOnClickListener {
            intent = Intent(this, LaporanActivity::class.java)
            startActivity(intent)
        }
        logout.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadData() {
        username.text = mPrefData.getUserName()
        name.text = mPrefData.getFullName()
        email.text = mPrefData.getCurrentUserEmail()
        role.text = mPrefData.getRoleUser()
    }
}