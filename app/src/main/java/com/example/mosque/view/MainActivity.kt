package com.example.mosque.view


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.mosque.R
import com.example.mosque.utils.addFragment
import com.example.mosque.view.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var mToolbar : Toolbar
    var bottomNav : Boolean? = false
    private var menu: Menu? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigationView()
    }

    private fun setupNavigationView() {
        if (navigation != null ){
            menu = navigation.menu
            selectFragment(menu?.getItem(2))

            navigation.setOnNavigationItemSelectedListener { item ->
                selectFragment(item)
                false
            }
        }
    }

    private fun selectFragment(item: MenuItem?) {
        item?.isChecked = true
        bottomNav = item?.isChecked

        if (item?.itemId == R.id.bantuan) {
            //openHomeFragment()
            //println("ON CLICK HOME")
        } else if (item?.itemId == R.id.berita){
            //println("ON CLICK Destinasi")
            //openDestinationFragment()
        }else if (item?.itemId == R.id.beranda) {
            println("ON CLICK Category")
            openHomeFragment()
        } else if (item?.itemId == R.id.transaksi) {
            //println("ON CLICK BOOKING")
            //openBookingFragment()
        }else if (item?.itemId == R.id.akun){
            //println("ON CLICK ACCOUNT")
            //openAccountFragment()
        } else  {
            //openHomeFragment()
        }


    }

    private fun openHomeFragment() {
        supportFragmentManager.addFragment(R.id.container, HomeFragment.newInstance(), HomeFragment.TAG)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }
}