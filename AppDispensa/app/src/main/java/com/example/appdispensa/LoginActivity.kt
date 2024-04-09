package com.example.appdispensa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.appdispensa.ui.home.HomeFragment

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun registrati(view: View) {
        startActivity(Intent(this,RegistrationActivity::class.java))
    }

    fun goHome(view: View) {
        startActivity(Intent(this,MainActivity::class.java))
    }
}