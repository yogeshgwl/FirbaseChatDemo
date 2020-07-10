package com.auth.login

import com.aucto.cache.db.dao.UserDao
import com.aucto.chat.MyApplication
import com.aucto.core.LoginManager
import com.aucto.model.User


class LoginRepository(
    private val userDao: UserDao = MyApplication.database.userDao(),
    val loginManager: LoginManager
) {

    // region - Public function
   suspend fun getUser(email: String): User? {
     return userDao.getUser(email)
    }
    //endregion
}
