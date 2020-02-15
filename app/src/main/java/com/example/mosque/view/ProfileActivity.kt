package com.example.mosque.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.mosque.R
import com.example.mosque.adapter.NavMainAdapter
import com.example.mosque.extention.getProgressDrawable
import com.example.mosque.extention.loadAsset
import com.example.mosque.helper.AppPreferencesHelper
import com.example.mosque.viewmodel.MainNavViewModel
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {

    lateinit var mPrefData: AppPreferencesHelper
    lateinit var mainViewModel: MainNavViewModel
    private val navMainAdapter = NavMainAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        mainViewModel = ViewModelProvider(this)[MainNavViewModel::class.java]
        mainViewModel.refresh(this)

        mPrefData = AppPreferencesHelper(this)
        loadData()
    }

    private fun loadData() {
        val progressDrawable: CircularProgressDrawable = getProgressDrawable(this)
        iv_profile.loadAsset(R.drawable.ic_user, progressDrawable)
        tv_fullname.text = mPrefData.getFullname()
        tv_user_role.text = mPrefData.getRoleUser()
        tv_username.text = String.format("Username : %s", mPrefData.getUsername())
        tv_email_address.text = String.format("Username : %s", mPrefData.getCurrentUserEmail())

        btn_logout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                mPrefData.setLogout()
                startActivity(Intent(baseContext, MainActivity::class.java))
                finish()
            }
        })
    }


}