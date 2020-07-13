package com.aucto.auth

import com.aucto.cache.db.dao.UserDao
import com.aucto.chat.MyApplication
import com.aucto.core.ActionLiveData
import com.aucto.core.Common.Companion.MESSAGES_CHILD
import com.aucto.core.LoginManager
import com.aucto.model.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SignupRepository(
    private val userDao: UserDao = MyApplication.database.userDao(),
    val loginManager: LoginManager
) {
    private var mFirebaseDatabaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().reference

    // region - Public function
    fun saveUserData(user: User, signupClick: ActionLiveData<Boolean>) {
        GlobalScope.launch(Dispatchers.IO) {
            userDao.clearDatabase()
            userDao.add(user)
            mFirebaseDatabaseReference.child(MESSAGES_CHILD)
                .push().setValue(user)
            withContext(Dispatchers.Main) {
                signupClick.sendAction(true)
            }
        }
    }


    //endregion
}
