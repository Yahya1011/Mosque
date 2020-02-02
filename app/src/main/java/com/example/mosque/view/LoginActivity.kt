package com.example.mosque.view

import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mosque.utils.CustomProgressBar
import com.example.mosque.R
import com.example.mosque.helper.AppPreferencesHelper
import com.example.mosque.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.input_email


class LoginActivity : AppCompatActivity() {

    lateinit var mPrefData: AppPreferencesHelper
    lateinit var animationDrawable: AnimationDrawable
    lateinit var loginViewModel : LoginViewModel
    var email: String =" "
    var password: String =" "
    var isLoginIn : Boolean = true
    val progressBar = CustomProgressBar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mPrefData = AppPreferencesHelper(this)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        initialView()
        initClickListener()
    }

    private fun initClickListener() {
        btn_login.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                progressBar.show(this@LoginActivity,"Please Wait...")

               if(!validate()){
                   println("LOGIN GAGAL ")
                    progressBar.dialog.dismiss()


               }else{
                   println("INPUT DATA $email, $password")
                   loginViewModel.submitLogin( email, password)
                   observerViewModel()

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


    private fun observerViewModel() {
        loginViewModel.loginData.observe(this, Observer {loginResponds->
            loginResponds.let {
                println("Data Pref ${mPrefData.isLoginIn()}")
                val message: String
                val title : String
                if (it.code == 200){
                    if (mPrefData.getAccessToken() == null || mPrefData.getAccessToken() != it.data.token || mPrefData.getRoleUser() == null){
                        mPrefData.setAccessToken(it.data.token)
                        mPrefData.setRoleUser(it.data.role)
                        mPrefData.setCurrentUserEmail(it.data.email)
                        mPrefData.setCurrentUserId(it.data.id)
                        mPrefData.setFullname(it.data.name)
                        mPrefData.setUsername(it.data.username)

                    }
                    message = "Selamat datang kembali ${it.data.name}"
                    it.message.let { titleDialog->
                        title = titleDialog
                    }
                    showDialog(title,message,it.code)
                } else {
                    progressBar.dialog.dismiss()
                    title = "Ups, Maaf!!"
                    it.message.let { messageDialog->
                        message = messageDialog
                    }
                    showDialog(title, message, it.code)
                }
            }

        })


        loginViewModel.loginLoadError.observe(this, Observer {


        })
    }




    private fun showDialog(mTitle: String, msg: String, code: Int){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(msg)
            .setCancelable(false)
            .setPositiveButton("ok") {dialog, _ ->
                if (code == 200){
                    openMainActivity()
                    dialog.cancel()
                }

            }

        val alert = dialogBuilder.create()
        alert.setTitle(mTitle)
        if(alert.isShowing){
            alert.dismiss()
        } else {
            alert.show()
        }

    }

    private fun openMainActivity() {
        mPrefData.setLoginIn(this.isLoginIn)
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
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
        email = input_email.getText().toString()
        password = input_pass.getText().toString()
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_email.error = "enter a valid email address"
            valid = false
        } else {
            input_email.error = null
        }
        if (password.isEmpty() || password.length < 4 || password.length > 30) {
            input_pass.error = "between 4 and 10 alphanumeric characters"
            valid = false
        } else {
            input_pass.error = null
        }
        return valid
    }

    override fun onPause() {
        super.onPause()
        if (progressBar.dialog.isShowing) {
            progressBar.dialog.dismiss()
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        if (progressBar.dialog.isShowing) {
            progressBar.dialog.dismiss()
        }
    }


}