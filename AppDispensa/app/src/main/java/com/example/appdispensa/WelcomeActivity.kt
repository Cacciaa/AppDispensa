@file:Suppress("DEPRECATION")

package com.example.appdispensa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.view.WindowCallbackWrapper

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_welcome)
    }

    fun register(view: View) {
        startActivity(Intent(this,RegistrationActivity::class.java))
    }

    fun login(view: View) {
        startActivity(Intent(this,LoginActivity::class.java))
    }
}