package com.example.mosque.view

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mosque.CustomProgressBar
import com.example.mosque.R
import com.example.mosque.view.activity.KeuanganActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    lateinit var animationDrawable: AnimationDrawable
    val progressBar = CustomProgressBar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initialView()

        initClickListener()
    }

    private fun initClickListener() {
        btn_login.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                progressBar.show(this@LoginActivity,"Please Wait...")


               if(!validate()){
                    progressBar.dialog.dismiss()

               }else{
                    progressBar.dialog.dismiss()
                    val intent = Intent(this@LoginActivity, KeuanganActivity::class.java)
                    startActivity(intent)
               }
            }
        })

        link_signup.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
        })
    }

    private fun initialView() {


//        animationDrawable.apply {
//            layout_login.background
//        }
//        animationDrawable.setEnterFadeDuration(5000)
//        animationDrawable.setExitFadeDuration(2000)


    }

//    override fun onPause() {
//        super.onPause()
//        if (animationDrawable != null && animationDrawable.isRunning) {
//            animationDrawable.stop()
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        if (animationDrawable != null && !animationDrawable.isRunning) {
//            animationDrawable.start()
//        }
//    }

    fun validate(): Boolean {
        var valid = true
        val email: String = input_email.getText().toString()
        val password: String = input_pass.getText().toString()
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_email.error = "enter a valid email address"
            valid = false
        } else {
            input_email.error = null
        }
        if (password.isEmpty() || password.length < 4 || password.length > 10) {
            input_pass.error = "between 4 and 10 alphanumeric characters"
            valid = false
        } else {
            input_pass.error = null
        }
        return valid
    }
}