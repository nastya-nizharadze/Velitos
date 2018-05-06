package com.example.naniti.velitos.signuplogin

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.widget.Button
import com.example.naniti.velitos.R
import com.example.naniti.velitos.baseclasses.BaseActivity
import android.view.View
import android.widget.Toast
import com.example.naniti.velitos.MainActivity
import kotlinx.android.synthetic.main.activity_signup.*


class SignUpActivity : AppCompatActivity(){
    val SIGNUP_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val buttonSignUp = findViewById<Button>(R.id.siginUpOnServer)
        buttonSignUp.setOnClickListener(clickListener)
    }

    val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.siginUpOnServer -> {
                if (!isValidData()) {
                    Toast.makeText(this, "not match", Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }
                /*
                Process request to server to validate.Make all async.
                If bad then email already registered
                else save token, and finish intent and go to login screen where immediately
                go yo main activity
                 */
                Toast.makeText(this, "to main", Toast.LENGTH_SHORT).show()
                setResult(SIGNUP_CODE)
                finish()
            }
        }
    }

    fun isValidData(): Boolean {
        val email = findViewById<TextInputLayout>(R.id.signUpEmail)
        val repPassword = findViewById <TextInputLayout> (R.id.signUpRepatPassword)
        val password = findViewById<TextInputLayout>(R.id.signUpPassword)
        if (repPassword. editText!!.text.toString() != password.editText!!.text.toString())
            return false
        return true


    }
}
