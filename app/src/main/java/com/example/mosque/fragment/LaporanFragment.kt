package com.example.mosque.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mosque.view.activity.KeuanganActivity
import com.example.mosque.R
import kotlinx.android.synthetic.main.fragment_laporan.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class LaporanFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_laporan, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        input_keuangan.setOnClickListener {
            //FirebaseAuth.getInstance().signOut()
            val intent = Intent (getActivity(), KeuanganActivity::class.java)
            getActivity()?.startActivity(intent)

        }

    }

}

