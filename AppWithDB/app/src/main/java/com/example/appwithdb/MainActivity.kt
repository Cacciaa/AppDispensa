package com.example.appwithdb

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    var db: AppDatabase? = null
    private lateinit var mUserViewModel: UserViewModel
        @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


            mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
            /*mUserViewModel.addUser(User(1,"Lorenzo","Erba"))
            mUserViewModel.addUser(User(2,"Matteo","Cacciarino"))
            mUserViewModel.readAllData.observe(this, Observer { user->
                Toast.makeText(this,user.get(1).firstName,Toast.LENGTH_LONG).show()

            }) */
            //mUserViewModel.readAllData.removeObservers(this)


    }


    fun cliccaLogin(view: View?){

        val editMail = findViewById<EditText>(R.id.editEmail)
        val editPassword = findViewById<EditText>(R.id.editPasswordLog)

        var mail = editMail.text.toString()
        var psw = editPassword.text.toString()

        var risultato: Int? = mUserViewModel.getUserId(mail, psw)



        if(risultato != null){
            Toast.makeText(this,"Credenziali corrette, benvenuto",Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this,"Credenziali errate",Toast.LENGTH_LONG).show()
        }

    }

    fun eventRegistra(view: View?){
        val intent = Intent(applicationContext, ActivityRegister::class.java)
        startActivity(intent)
    }


}