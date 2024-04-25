package com.example.appdispensa.dbhelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDbHelper(var context: Context, var DATABASE_NAME: String?, var DATABASE_VERSION: Int) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        var query:String = "CREATE TABLE " + DbEnum.TABELLA_UTENTI.valore + " (" +
                DbEnum.COLONNA_ID.valore + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                DbEnum.COLONNA_NOME.valore + " TEXT , " +
                DbEnum.COLONNA_EMAIL.valore + " TEXT, " +
                DbEnum.COLONNA_PASSWORD.valore + " TEXT);"

        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + DbEnum.TABELLA_UTENTI.valore)
        onCreate(db)
    }


    // Query Registrazione Utente
    fun registerUser(nome:String,email:String,password:String){

        var db:SQLiteDatabase = this.writableDatabase
        var cv:ContentValues = ContentValues()

        cv.put(DbEnum.COLONNA_NOME.valore, nome)
        cv.put(DbEnum.COLONNA_EMAIL.valore, email)
        cv.put(DbEnum.COLONNA_PASSWORD.valore, password)

        var result: Long = db.insert(DbEnum.TABELLA_UTENTI.valore, null, cv)

        if(result == -1L){
            Toast.makeText(this.context, "Errore registrazione", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this.context, "Utente registrato", Toast.LENGTH_SHORT).show()
        }

    }

    // Query Login Utente
    fun loginUser(email:String,password:String):Cursor{

        var db:SQLiteDatabase = this.writableDatabase
        var query:String = "SELECT " + DbEnum.COLONNA_ID.valore +
                            " FROM " + DbEnum.TABELLA_UTENTI.valore +
                            " WHERE " + DbEnum.COLONNA_PASSWORD.valore + "=? AND " +
                            DbEnum.COLONNA_EMAIL.valore + "=?"

        var cursor: Cursor = db.rawQuery(query, arrayOf(password, email))


        return cursor

    }


    // Query che ritorna il Nome dell'utente e la sua email, chiamata dopo il login per sostituire i valori all'interno della Home Page
    fun getUserInfo(idUser:Int):Cursor{

        var db:SQLiteDatabase = this.writableDatabase
        var query:String = "SELECT " + DbEnum.COLONNA_NOME.valore + ", " + DbEnum.COLONNA_EMAIL.valore +
                " FROM " + DbEnum.TABELLA_UTENTI.valore +
                " WHERE " + DbEnum.COLONNA_ID.valore + "=?"

        var cursor: Cursor = db.rawQuery(query, arrayOf(idUser.toString()))


        return cursor

    }


}