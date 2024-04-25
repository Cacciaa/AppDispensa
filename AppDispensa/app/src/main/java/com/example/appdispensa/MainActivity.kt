package com.example.appdispensa

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.appdispensa.databinding.ActivityMainBinding
import com.example.appdispensa.dbhelper.MyDbHelper
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    public var xxx : String = "ciao"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_dispensa, R.id.nav_maps,R.id.nav_macro
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        //set name of users

        // -------------------------------------------------------------------

        var navHeaderMainLayout = navView.getHeaderView(0)
        var userEmailWelcome = navHeaderMainLayout.findViewById<TextView>(R.id.txtEmail)

        val sharedPreferences: SharedPreferences = binding.root.context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)
        val user_id = sharedPreferences.getInt("user_id", -1) //where 0 is default value

        // Chiamo la query per ottenere il nome utente e la email dato l'id dell'utente loggato


        var db: MyDbHelper = MyDbHelper(this, "dbDispensa.db", 1)
        var cursor: Cursor = db.getUserInfo(user_id)


        var resultList: ArrayList<String> = extractFromCursorUserAndEmail(cursor)
        println(resultList)
        userEmailWelcome.text = resultList.first()

    }



    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun esci(view: View) {

            startActivity(Intent(this, WelcomeActivity::class.java))
    }

    private fun extractFromCursorUserAndEmail(cursor:Cursor): ArrayList<String>{

        var myList: ArrayList<String> = arrayListOf()


        if (cursor.moveToFirst()) {
            do {
                myList.add(cursor.getString(1))
            } while (cursor.moveToNext());
        }

        return myList
    }
}