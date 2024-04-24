package com.example.dbsqlite

import android.app.DownloadManager.Query
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDbHelper(var context: Context,var DATABASE_NAME: String?,var DATABASE_VERSION: Int) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val TABLE_NAME : String
        get() {return "Users"}

    private val COLUMN_ID: String = "id"
    private val COLUMN_NOME: String = "nome"
    private val COLUMN_COGNOME: String = "cognome"
    private val COLUMN_EMAIL: String = "email"
    private val COLUMN_PASSWORD:String ="password"
    override fun onCreate(db: SQLiteDatabase?) {
        var query:String = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                COLUMN_NOME + " TEXT, " +
                COLUMN_COGNOME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT);"
        db!!.execSQL(query)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addUser(nome:String,cognome:String,email:String,password:String){
        var db : SQLiteDatabase = this.writableDatabase
        var cv : ContentValues = ContentValues()
        cv.put(COLUMN_NOME,nome)
        cv.put(COLUMN_COGNOME,cognome)
        cv.put(COLUMN_EMAIL,email)
        cv.put(COLUMN_PASSWORD,password)
        var  result : Long = db.insert(TABLE_NAME,null,cv)
        if (result == -1L){
            Toast.makeText(this.context,"Errore",Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this.context,"Inserito",Toast.LENGTH_SHORT).show()
        }
    }

    fun readALlData() : Cursor?{
        var query:String = "SELECT * FROM " + TABLE_NAME
        var db : SQLiteDatabase = this.writableDatabase
        var cursor : Cursor ?= null
        if( db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor
    }

    fun updateData(nome:String,cognome:String,email:String,password:String,id:String){
        var db : SQLiteDatabase = this.writableDatabase
        var cv : ContentValues = ContentValues()
        cv.put(COLUMN_NOME,nome)
        cv.put(COLUMN_COGNOME,cognome)
        cv.put(COLUMN_EMAIL,email)
        cv.put(COLUMN_PASSWORD,password)

        var result : Int = db.update(TABLE_NAME,cv,"id =?",arrayOf(id))

        if (result == -1){
            Toast.makeText(this.context,"Errore",Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this.context,"Aggiornato",Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteData(id:String){
        var db : SQLiteDatabase = this.writableDatabase
        var result : Int = db.delete(TABLE_NAME,"id=?",arrayOf(id))
        if (result == -1){
            Toast.makeText(this.context,"Errore",Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this.context,"Eliminato",Toast.LENGTH_SHORT).show()
        }
    }
}