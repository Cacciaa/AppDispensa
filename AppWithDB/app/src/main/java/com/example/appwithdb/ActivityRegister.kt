package com.example.appwithdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class ActivityRegister : AppCompatActivity() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun registratiEvent(view: View){

        val editNome = findViewById<EditText>(R.id.editNome)
        val editCognome = findViewById<EditText>(R.id.editCognome)
        val editMail = findViewById<EditText>(R.id.editEmailReg)
        val editPassword = findViewById<EditText>(R.id.editPasswordReg)

        var nome = editNome.text.toString()
        var cognome = editCognome.text.toString()
        var mail = editMail.text.toString()
        var psw = editPassword.text.toString()

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mUserViewModel.addUser(User(0,nome, cognome, mail, psw))

    }
}