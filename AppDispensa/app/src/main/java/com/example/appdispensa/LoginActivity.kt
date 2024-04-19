package com.example.appdispensa

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun registrati(view: View) {
        startActivity(Intent(this,RegistrationActivity::class.java))
    }

    fun goHome(view: View) {
        val sharedPreferences = baseContext.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("user_id", 10)
        editor.apply()
        startActivity(Intent(this,MainActivity::class.java))
    }
}