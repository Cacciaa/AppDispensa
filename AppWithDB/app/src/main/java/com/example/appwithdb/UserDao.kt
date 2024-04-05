package com.example.appwithdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface UserDao {

    @Upsert
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM User")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM User ORDER BY uid ASC")
    fun readAllData(): LiveData<List<User>>

    @Query("SELECT uid FROM User WHERE email LIKE :emailInserita AND password LIKE :pswInserita")
    fun getUserId(emailInserita:String, pswInserita:String): Int
}