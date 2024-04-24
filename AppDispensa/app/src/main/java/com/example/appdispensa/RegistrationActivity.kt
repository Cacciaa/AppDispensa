package com.example.appdispensa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.appdispensa.dbhelper.MyDbHelper

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
    }

    fun login(view: View) {
        startActivity(Intent(this,LoginActivity::class.java))
    }

    // Button Register
    fun goLogin(view: View) {

        var nome:String = findViewById<EditText>(R.id.editTextNome).text.toString()
        var email:String = findViewById<EditText>(R.id.editTextEmail).text.toString()
        var password:String = findViewById<EditText>(R.id.editTextPassword).text.toString()

        if(nome.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {

            var db: MyDbHelper = MyDbHelper(this@RegistrationActivity, "dbDispensa.db", 1)

            db.registerUser(nome, email, password)

            // Vai su login
            startActivity(Intent(this,LoginActivity::class.java))

        }else{
            Toast.makeText(this, "Campi non corretti", Toast.LENGTH_SHORT).show()
        }


    }

}