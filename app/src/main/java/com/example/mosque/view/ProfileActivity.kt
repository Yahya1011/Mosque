package com.example.mosque.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.mosque.R
import com.example.mosque.adapter.ProfileMainAdapter
import com.example.mosque.extention.getProgressDrawable
import com.example.mosque.extention.loadAsset
import com.example.mosque.helper.AppPreferencesHelper
import com.example.mosque.model.MainNav
import com.example.mosque.utils.EqualSpacingItemDecoration
import com.example.mosque.viewmodel.ProfileMenuViewModel
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {

    lateinit var mPrefData: AppPreferencesHelper
    lateinit var mainViewModel: ProfileMenuViewModel
    private val navMainAdapter = ProfileMainAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        rv_listprofile.apply{
            addItemDecoration(EqualSpacingItemDecoration(12, EqualSpacingItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = navMainAdapter
        }

        mainViewModel = ViewModelProvider(this)[ProfileMenuViewModel::class.java]
        mainViewModel.refresh(this)
        mPrefData = AppPreferencesHelper(this)
        loadData()
        observeProfileNavModel()
    }

    private fun observeProfileNavModel() {
        mainViewModel.dataNavigation.observe(this, Observer { mainNavs ->
            mainNavs?.let {
                println("DATA NAV PROFILE ${it.size}")
                rv_listprofile.visibility = View.VISIBLE
                navMainAdapter.updateNav(it)
                navMainAdapter.setOnItemClickListener(object :
                    ProfileMainAdapter.OnItemClickListener {
                    override fun onItemSelected(mainNav: MainNav) {
                        initClickListener(mainNav.nav_name)
                    }

                })


            }

        })
    }

    private fun initClickListener(navName: String) {

        if (navName == "Edit Profile"){
            val intent = Intent(this, MasjidSekitarActivity::class.java)
            intent.putExtra("key", navName)
            startActivity(intent)
        } else if(navName == "Hubungi Kami"){
            val intent = Intent(this, DonasiActivity::class.java)
            intent.putExtra("key", navName)
            startActivity(intent)
        } else if(navName == "Rating"){
            val intent = Intent(this, InformasiActivity::class.java)
            intent.putExtra("key", navName)
            startActivity(intent)
        } else if(navName == "Logout"){
            mPrefData.setLogout()
            startActivity(Intent(baseContext, MainActivity::class.java))
        }


    }

    private fun loadData() {
        val progressDrawable: CircularProgressDrawable = getProgressDrawable(this)
        iv_profile.loadAsset(R.drawable.ic_user, progressDrawable)
        tv_fullname.text = mPrefData.getFullname()
        tv_user_role.text = mPrefData.getRoleUser()
        tv_username.text = String.format("Username : %s", mPrefData.getUsername())
        tv_email_address.text = String.format("Email : %s", mPrefData.getCurrentUserEmail())

    }


}