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

        query = "CREATE TABLE " + DbEnum.TABELLA_DISPENSA.valore + " (" +
                DbEnum.COLONNA_ID.valore + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                DbEnum.COLONNA_NOME.valore + " TEXT , " +
                DbEnum.COLONNA_ID_UTENTE.valore + " INTEGER, " +
                "FOREIGN KEY (" + DbEnum.COLONNA_ID_UTENTE.valore + ") REFERENCES " + DbEnum.TABELLA_UTENTI.valore + "(" +
                DbEnum.COLONNA_ID.valore + ") ON UPDATE CASCADE ON DELETE CASCADE);"

        db.execSQL(query)

        query = "CREATE TABLE " + DbEnum.TABELLA_PRODOTTI.valore + " (" +
                DbEnum.COLONNA_ID.valore + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                DbEnum.COLONNA_NOME.valore + " TEXT , " +
                DbEnum.COLONNA_QUANTITA.valore + " INTEGER DEFAULT 0, " +
                DbEnum.COLONNA_ID_DISPENSA.valore + " INTEGER, " +
                "FOREIGN KEY (" + DbEnum.COLONNA_ID_DISPENSA.valore + ") REFERENCES " + DbEnum.TABELLA_DISPENSA.valore + "(" +
                DbEnum.COLONNA_ID.valore + ") ON UPDATE CASCADE ON DELETE CASCADE);"

        db.execSQL(query)

        query = "CREATE TABLE " + DbEnum.TABELLA_MACRONUTRIENTI.valore + " (" +
                DbEnum.COLONNA_ID.valore + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                DbEnum.COLONNA_CARBOIDRATI.valore + " INTEGER DEFAULT 0 , " +
                DbEnum.COLONNA_PROTEINE.valore + " INTEGER DEFAULT 0, " +
                DbEnum.COLONNA_GRASSI.valore + " INTEGER DEFAULT 0, " +
                DbEnum.COLONNA_ID_UTENTE.valore + " INTEGER, " +
                DbEnum.COLONNA_FIBRE.valore + " INTEGER DEFAULT 0, " +
                "FOREIGN KEY (" + DbEnum.COLONNA_ID_UTENTE.valore + ") REFERENCES " + DbEnum.TABELLA_UTENTI.valore + "(" +
                DbEnum.COLONNA_ID.valore + ") ON UPDATE CASCADE ON DELETE CASCADE);"

        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + DbEnum.TABELLA_UTENTI.valore)
        onCreate(db)
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        db!!.execSQL("PRAGMA foreign_keys = ON")
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

    // Ritorna gli id delle dispense vuote
    fun getEmptyDispense(idUser:Int): Cursor{

        /*

        SELECT Dispensa.id
        FROM Dispensa
        WHERE Dispensa.id_utente = 1
        EXCEPT
        SELECT Prodotti.id_dispensa
        FROM Prodotti

        */

        var db:SQLiteDatabase = this.writableDatabase
        var query:String = "SELECT " + DbEnum.COLONNA_ID.valore +
                " FROM " + DbEnum.TABELLA_DISPENSA.valore +
                " WHERE " + DbEnum.COLONNA_ID_UTENTE.valore + "=?" +
                "EXCEPT " +
                "SELECT "  + DbEnum.COLONNA_ID_DISPENSA.valore +
                " FROM " + DbEnum.TABELLA_PRODOTTI.valore

        var cursor: Cursor = db.rawQuery(query, arrayOf(idUser.toString()))

        return cursor

    }

    fun getDispense(idUser:Int):Cursor{

        var db:SQLiteDatabase = this.writableDatabase
        var query:String = "SELECT " + DbEnum.COLONNA_ID.valore + " , " +  DbEnum.COLONNA_NOME.valore +
                " FROM " + DbEnum.TABELLA_DISPENSA.valore +
                " WHERE " + DbEnum.COLONNA_ID_UTENTE.valore + "=?"

        var cursor: Cursor = db.rawQuery(query, arrayOf(idUser.toString()))

        return cursor

    }


}