package com.example.appwithdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    private fun Builddatabase(context: Context)= Room.databaseBuilder(context.applicationContext,AppDatabase::class.java,"Taskbardb").build()

}