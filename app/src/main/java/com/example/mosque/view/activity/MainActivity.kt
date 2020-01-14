package com.example.mosque.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.mosque.*
import com.example.mosque.fragment.*
import com.example.mosque.view.InformasiActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var berandaFragment: BerandaFragment
    lateinit var bantuanFragment: BantuanFragment
    lateinit var beritaFragment: BeritaFragment
    lateinit var transaksiFragment: TransaksiFragment
    lateinit var akunFragment: AkunFragment

    lateinit var button1: Button
    lateinit var button2: Button
    lateinit var button3: Button
    lateinit var button4: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation : BottomNavigationView = findViewById(R.id.nav)

        bantuanFragment = BantuanFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, bantuanFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId){
                R.id.beranda -> {

                    berandaFragment =
                        BerandaFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, berandaFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.bantuan -> {

                    bantuanFragment =
                        BantuanFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, bantuanFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.berita -> {

                    beritaFragment =
                        BeritaFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, beritaFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.transaksi -> {

                    transaksiFragment =
                        TransaksiFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, transaksiFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.akun -> {

                    akunFragment =
                        AkunFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, akunFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }
            true
        }

        button1 = findViewById<Button>(R.id.button1)
        button2 = findViewById<Button>(R.id.button2)
        button3 = findViewById<Button>(R.id.button3)
        button4 = findViewById<Button>(R.id.button4)

        button1.setOnClickListener{
            val intent = Intent(this, DonasiActivity::class.java)
            startActivity(intent)
        }
        button2.setOnClickListener{
            val intent = Intent(this, InformasiActivity::class.java)
            startActivity(intent)
        }
        button3.setOnClickListener{
            val intent = Intent(this, MasjidSekitarActivity::class.java)
            startActivity(intent)
        }
        button4.setOnClickListener{
            val intent = Intent(this, AcaraActivity::class.java)
            startActivity(intent)
        }
    }
}
