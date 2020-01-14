package com.example.mosque.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.mosque.*
import com.example.mosque.adapter.PagerAdapter
import com.example.mosque.fragment.BeritaFragment
import com.example.mosque.fragment.GaleriFragment
import com.example.mosque.fragment.InformasiFragment
import com.example.mosque.fragment.LaporanFragment
import com.google.android.material.tabs.TabLayout

class Informasi1Activity : AppCompatActivity() {

    private var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informasi1)

        viewPager = findViewById(R.id.viewPager) as ViewPager
        setupViewPager(viewPager!!)

        tabLayout = findViewById(R.id.tabs) as TabLayout
        tabLayout!!.setupWithViewPager(viewPager)
    }
    private fun setupViewPager(viewPager: ViewPager) {
        val adapter =
            PagerAdapter(supportFragmentManager)
        adapter.addFragment(InformasiFragment(), "Informasi")
        adapter.addFragment(LaporanFragment(), "Laporan")
        adapter.addFragment(BeritaFragment(), "Berita")
        adapter.addFragment(GaleriFragment(), "Galeri")
        viewPager.adapter = adapter
    }
    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }


//        setSupportActionBar(toolbar)
//
//        val adapter = PagerAdapter(supportFragmentManager)
//        adapter.addFragment(InformasiFragment(), "Informasi")
//        adapter.addFragment(LaporanFragment(), "Laporan")
//        adapter.addFragment(BeritaFragment(), "Berita")
//        adapter.addFragment(GaleriFragment(), "Galeri")
//        viewPager.adapter = adapter
//        tabs.setupWithViewPager(viewPager)

//        val fab: View = findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Toast.makeText(this, "Floating Action Button Berhasil dibuat", Toast.LENGTH_SHORT).show()
//        }

//        informasi.setOnClickListener{
//            val popup = PopupMenu (this,informasi)
//            popup.inflate(R.menu.popup)
//            popup.setOnMenuItemClickListener {item ->
//                when(item.itemId) {
//                    R.id.informasi_masjid -> {
//                        val intent = Intent(this, InformasiFragment::class.java)
//                        startActivity(intent)
//                    }
//                    R.id.sejarah -> {
//                        val intent = Intent(this, InformasiFragment::class.java)
//                        startActivity(intent)
//                    }
//                    R.id.visi_misi -> {
//                        val intent = Intent(this, InformasiFragment::class.java)
//                        startActivity(intent)
//                    }
//                    R.id.pengurus -> {
//                        val intent = Intent(this, InformasiFragment::class.java)
//                        startActivity(intent)
//                    }
//                    R.id.jamaah -> {
//                        val intent = Intent(this, InformasiFragment::class.java)
//                        startActivity(intent)
//                    }
//                }
//                true
//            }
//            popup.show()
//    }
        }