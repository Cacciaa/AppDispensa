package com.example.dbsqlite

import android.annotation.SuppressLint
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var db = MyDbHelper(this@MainActivity,"mydb.db",1)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btn = findViewById<Button>(R.id.button)
        var btn2 = findViewById<Button>(R.id.button2)
        var btn3 = findViewById<Button>(R.id.button3)
        btn.setOnClickListener(object:OnClickListener{
            override fun onClick(v: View?) {
                var db = MyDbHelper(this@MainActivity,"mydb.db",1)
                db.addUser("Lorenzo","Erba","lorenzoerba250@gmail.com","prova")
            }

        })

        btn2.setOnClickListener(object:OnClickListener{
            override fun onClick(v: View?) {

                var cursor : Cursor ?= db.readALlData();
                db.updateData("Matteo","Cacciarino","matcacciarino@gmail.com","prova","2");
                if(cursor!!.count == 0){
                    Toast.makeText(this@MainActivity,"No data", Toast.LENGTH_SHORT).show()
                }
                else{
                    while(cursor.moveToNext()){
                        Toast.makeText(this@MainActivity,cursor.getInt(0).toString() + " - " +
                                cursor.getString(1), Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })

        btn3.setOnClickListener{

            db.deleteData("2")
        }
    }
}