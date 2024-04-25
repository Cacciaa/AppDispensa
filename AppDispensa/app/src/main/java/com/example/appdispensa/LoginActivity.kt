package com.example.appdispensa

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appdispensa.dbhelper.MyDbHelper


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun registrati(view: View) {
        startActivity(Intent(this,RegistrationActivity::class.java))
    }

    fun goHome(view: View) {

        var email:String = findViewById<EditText>(R.id.editTextEmailL).text.toString()
        var password:String = findViewById<EditText>(R.id.editTextPasswordL).text.toString()

        if(email.isNotEmpty() && password.isNotEmpty()) {

            var db: MyDbHelper = MyDbHelper(this@LoginActivity, "dbDispensa.db", 1)
            var cursor: Cursor = db.loginUser(email, password)

            if(cursor.count == 1) {

                while(cursor.moveToNext()){
                    val sharedPreferences = baseContext.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()

                    editor.putInt("user_id", cursor.getInt(0))
                    editor.apply()
                    startActivity(Intent(this, MainActivity::class.java))
                }

            }else{
                Toast.makeText(this, "Utente non registrato", Toast.LENGTH_SHORT).show()
            }

        }else{
            Toast.makeText(this, "Campi non corretti", Toast.LENGTH_SHORT).show()
        }

    }
}