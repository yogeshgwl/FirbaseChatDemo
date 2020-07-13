package com.aucto.auth

import com.aucto.cache.db.dao.UserDao
import com.aucto.chat.MyApplication
import com.aucto.core.LoginManager
import com.aucto.model.User


class LoginRepository(
    private val userDao: UserDao = MyApplication.database.userDao(),
    val loginManager: LoginManager = MyApplication.loginManager
) {

    // region - Public function
   suspend fun getUser(email: String): User? {
     return userDao.getUser(email)
    }

    fun saveUserDetails(user: User) {
        loginManager.setUser(user)
    }
    //endregion
}
