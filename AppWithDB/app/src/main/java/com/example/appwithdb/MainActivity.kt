package com.example.appwithdb

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    var db: AppDatabase? = null
    private lateinit var mUserViewModel: UserViewModel
        @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
            mUserViewModel.addUser(User(1,"Lorenzo","Erba"))
            mUserViewModel.addUser(User(2,"Matteo","Cacciarino"))
            mUserViewModel.readAllData.observe(this, Observer { user->
                Toast.makeText(this,user.get(1).firstName,Toast.LENGTH_LONG).show()

            })
            //mUserViewModel.readAllData.removeObservers(this)


    }


    fun clicca(view: View?){
        mUserViewModel.deleteUser(User(2,"Lara","Erba"))
    }


}