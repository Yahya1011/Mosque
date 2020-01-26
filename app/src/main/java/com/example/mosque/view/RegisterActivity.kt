package com.example.mosque.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mosque.CustomProgressBar
import com.example.mosque.R
import com.example.mosque.helper.AppPreferencesHelper
import com.example.mosque.view.activity.KeuanganActivity
import com.example.mosque.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(){

    lateinit var mPrefData: AppPreferencesHelper
    private var email: String =" "
    private var username: String =""
    private var fullName: String = ""
    private var password : String =""
    val progressBar = CustomProgressBar()
    lateinit var registerViewModel : RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mPrefData = AppPreferencesHelper(this)
        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]


        initClickListener()


    }


    private fun observerViewModel() {
       registerViewModel.registerData.observe(this, Observer { regRespons->
           regRespons.let {

               mPrefData.setAccessToken(it.data.token)
               showDialog("Success", it.message)
           }


       })


        registerViewModel.errorData.observe(this, Observer {registerErrData->
            registerErrData?.let {
                println("TOKEN ERROR USERNAME${it.data.username},\n ERROR EMAIL${it.data.email} ")
                if (it.data.username == ""){
                    showDialogError("Error", "The username has already been taken")

                }

                if(it.data.email == " "){
                    showDialogError("Error", "The email has already been taken")
                }

            }
        }

        )
    }

    private fun showDialog(mTitle : String, msg: String){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(msg)
            .setCancelable(false)
            .setPositiveButton("ok") {dialog, id ->
                openLoginActivity()
                dialog.dismiss()
            }

        val alert = dialogBuilder.create()
        if (alert.isShowing){
            alert.dismiss()
        } else {
            alert.setTitle(mTitle)
            alert.show()
        }
    }

    private fun showDialogError(mTitleErr : String, msgErr: String){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(msgErr)
            .setCancelable(false)
            .setPositiveButton("ok") {dialog, id ->
                input_email.error = "Email ${input_email.text.toString()}  Sudah di gunakan"
                input_username.error = " Username ${input_username.text.toString()}  Sudah di gunakan"
                dialog.dismiss()
            }

        val alert = dialogBuilder.create()
        if (alert.isShowing){
            alert.dismiss()
        } else {
            alert.setTitle(mTitleErr)
            alert.show()
        }

    }

    private fun openLoginActivity() {
       val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun initClickListener() {
        btn_register.setOnClickListener(object : OnClickListener{
            override fun onClick(v: View?) {
                progressBar.show(this@RegisterActivity,"Please Wait...")
                if(!validateData()){
                    progressBar.dialog.dismiss()
                }else{
                    progressBar.dialog.dismiss()

                    registerViewModel.submitRegister(email , username,  fullName , password)
                    observerViewModel()
                }
            }
        })
        link_login.setOnClickListener(object :OnClickListener{
            override fun onClick(v: View?) {
                intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

        })
    }


    private fun validateData(): Boolean {
        var valid = true
        fullName = input_nama.getText().toString()
        username = input_username.getText().toString()
        email = input_email.getText().toString()
        password = input_password.getText().toString()
        val RetypePassword: String = input_konfirmasi_password.getText().toString()
        if (fullName.isEmpty() || fullName.length < 4 || fullName.length > 50 ) {
            input_nama.error = "enter a valid name"
            valid = false
        } else {
            input_nama.error = null
        }
        if (username.isEmpty() || username.length < 4 || username.length > 20) {
            input_username.error = "enter a valid username"
            valid = false
        } else {
            input_username.error = null
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_email.error = "enter a valid email address"
            valid = false
        } else {
            input_email.error = null
        }
        if (password.isEmpty() || password.length < 4 || password.length > 10) {
            input_password.error = "between 4 and 10 alphanumeric characters"
            valid = false
        } else {
            input_password.error = null
        }
        if (RetypePassword.isEmpty() || !RetypePassword.equals(password)) {
            input_konfirmasi_password.error = "Password yang anda masukkan tidak sesuai dengan password sebelumnya!!"
            valid = false
        } else {
            input_konfirmasi_password.error = null
        }


        return valid
    }


}
