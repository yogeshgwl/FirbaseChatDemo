package com.aucto.cache.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aucto.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(user: User)

    @Update
    suspend fun update(user: User): Int

    @Delete
    suspend fun delete(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addList(syncItems: List<User>)

    @Query("SELECT * FROM user")
    fun getAllUserList(): LiveData<List<User>>

    @Query("DELETE FROM user")
    fun clearDatabase()

    @Query("SELECT * FROM user WHERE email = (:email)")
    fun getUser(email: String): User?


}