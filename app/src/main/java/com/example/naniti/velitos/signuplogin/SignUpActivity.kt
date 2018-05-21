package com.example.naniti.velitos.signuplogin

import android.app.Activity
import android.content.Context
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
import com.example.naniti.velitos.internet.LeningradskayaClient
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async


class SignUpActivity : AppCompatActivity(){
    val SIGNUP_CODE = 1
    val httpClient = LeningradskayaClient("http://hserver.leningradskaya105.ru:6379")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val buttonSignUp = findViewById<Button>(R.id.siginUpOnServer)
        buttonSignUp.setOnClickListener(clickListener)
    }

    val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.siginUpOnServer -> {
                async(UI) {
                    val username: TextInputLayout = findViewById<TextInputLayout>(R.id.signUpUsername)
                    val repPassword = findViewById<TextInputLayout>(R.id.signUpRepatPassword)
                    val password = findViewById<TextInputLayout>(R.id.signUpPassword)
                    if (!isValidData(username, repPassword, password)) {
                        Toast.makeText(this@SignUpActivity, "not match", Toast.LENGTH_SHORT).show()
                        return@async
                    } else {
                        val user_register = httpClient.registerClient(username.editText!!.text.toString(),password.editText!!.text.toString()).await()
                        if (user_register!=null) {
                            val user_response = httpClient.getClientToken(username.editText!!.text.toString(), password.editText!!.text.toString()).await()
                            if (user_response == null) {
                                Toast.makeText(this@SignUpActivity, "invalid username or password", Toast.LENGTH_SHORT).show()
                            } else {
                                saveToPersistentStorage(user_response.token!!, username.toString())
                                Toast.makeText(this@SignUpActivity, "to main", Toast.LENGTH_SHORT).show()
                                setResult(SIGNUP_CODE)
                                finish()
                            }
                        }
                    }
                    /*
                    Process request to server to validate.Make all async.
                    If bad then email already registered
                    else save token, and finish intent and go to login screen where immediately
                    go yo main activity
                     */

                }
            }
        }
    }

    fun isValidData(username:TextInputLayout, repPassword:TextInputLayout, password:TextInputLayout): Boolean {
        if ((repPassword. editText!!.text.toString() != password.editText!!.text.toString())&&(username.counterMaxLength >= 3))
            return false
        return true
    }

    private fun saveToPersistentStorage(token: String, username: String) {

        Toast.makeText(baseContext, "SAVE!", Toast.LENGTH_SHORT).show()

        val pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE)
        val edt = pref.edit()
        edt.putString("JWTTOKEN", token)
        edt.putString("USERNAME",username)
        edt.apply()
    }

}
