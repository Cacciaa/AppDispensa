package com.example.appdispensa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.appdispensa.dbhelper.MyDbHelper
import com.hololo.tutorial.library.TutorialActivity

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
    }

    fun login(view: View) {
        startActivity(Intent(this,LoginActivity::class.java))
        clearData()
    }

    // Button Register
    fun goLogin(view: View) {

        var nome:String = findViewById<EditText>(R.id.editTextNome).text.toString()
        var email:String = findViewById<EditText>(R.id.editTextEmail).text.toString()
        var password:String = findViewById<EditText>(R.id.editTextPassword).text.toString()

        if(nome.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {

            var db: MyDbHelper = MyDbHelper(this@RegistrationActivity, "dbDispensa.db", 1)

            if(db.registerUser(nome, email, password)){
                // go to login
                //startActivity(Intent(this,LoginActivity::class.java))
                startActivity(Intent(this,IntroActivity::class.java))

            }


        }else{
            Toast.makeText(this, "Campi non corretti", Toast.LENGTH_SHORT).show()
        }

        clearData()

    }

    private fun clearData() {

        var nome = findViewById<EditText>(R.id.editTextNome)
        var email = findViewById<EditText>(R.id.editTextEmail)
        var password = findViewById<EditText>(R.id.editTextPassword)

        nome.setText("")
        email.setText("")
        password.setText("")

    }

}