package com.example.naniti.velitos.signuplogin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_login.*
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.example.naniti.velitos.MainActivity
import com.example.naniti.velitos.R
import com.example.naniti.velitos.baseclasses.BaseActivity
import com.example.naniti.velitos.internet.LeningradskayaClient
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.channels.NULL_VALUE


class LoginActivity : AppCompatActivity() {
    val SIGNUP_CODE = 1

    val httpClient = LeningradskayaClient("http://hserver.leningradskaya105.ru:6379")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val buttonLogin = findViewById<Button>(R.id.login)
        val buttonVkLogin = findViewById<Button>(R.id.vk_login)
        val buttonTelegramLogin = findViewById<Button>(R.id.telegran_login)
        val buttonSignUpLogin = findViewById<Button>(R.id.signup)
        val buttonGoogleLogin = findViewById<Button>(R.id.google_login)
        buttonLogin.setOnClickListener(clickListener)
        buttonVkLogin.setOnClickListener(clickListener)
        buttonGoogleLogin.setOnClickListener(clickListener)
        buttonTelegramLogin.setOnClickListener(clickListener)
        buttonSignUpLogin.setOnClickListener(clickListener)
    }


    val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.login -> {
                async(UI) {
                    val username = usernameTextInputLayout?.editText!!.text.toString()
                    val password = passwordTextInputLayout.editText!!.text.toString()
                    val userResponse = httpClient.getClientToken(username, password).await()
                    if (userResponse ==null){
                        Toast.makeText(this@LoginActivity,"invalid username or password", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        saveToPersistentStorage(userResponse.token!!, username)
                    }

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                //Toast.makeText(this, username, Toast.LENGTH_SHORT).show()
                //Toast.makeText(this, password, Toast.LENGTH_LONG).show()
                // saveToPersistentStorage("1234556789")
                // val intent = Intent(this,MainActivity::class.java)
                //startActivity(intent)
                //finish()
            }
            R.id.telegran_login -> {
                Toast.makeText(this, "Telega", Toast.LENGTH_LONG).show()
            }

            R.id.google_login -> {
                Toast.makeText(this, "Google", Toast.LENGTH_LONG).show()
            }
            R.id.vk_login -> {
                Toast.makeText(this, "VK", Toast.LENGTH_LONG).show()
            }
            R.id.signup -> {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivityForResult(intent, SIGNUP_CODE)

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == SIGNUP_CODE) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
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
