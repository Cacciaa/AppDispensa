package com.example.appwithdb

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    var db: AppDatabase? = null
        @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
           var db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "dbprova"
            ).allowMainThreadQueries().build()

            val userDao = db.userDao()
            //userDao.insertAll(User(3,"Lorenzo","Erba"))
            val f = findViewById<TextView>(R.id.txtId)
            f.text = userDao.getAll().get(1).uid.toString()

        }
    }



}