package com.aucto.auth

import android.util.Log
import com.aucto.cache.db.dao.UserDao
import com.aucto.chat.MyApplication
import com.aucto.core.ActionLiveData
import com.aucto.core.Common.Companion.MESSAGES_CHILD
import com.aucto.core.Common.Companion.USERS
import com.aucto.core.LoginManager
import com.aucto.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SignupRepository(
    private val userDao: UserDao = MyApplication.database.userDao(),
    val loginManager: LoginManager
) {
    private var mFirebaseDatabaseReference: FirebaseFirestore = Firebase.firestore


    // region - Public function
    fun saveUserData(user: User, signupClick: ActionLiveData<Boolean>) {
        GlobalScope.launch(Dispatchers.IO) {
            userDao.clearDatabase()
            userDao.add(user)
            mFirebaseDatabaseReference.collection(USERS)
                .add(user).addOnSuccessListener { documentReference ->
                    Log.d("TAG", "DocumentSnapshot user added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.d("TAG", "Error adding  user document", e)
                }

            withContext(Dispatchers.Main) {
                signupClick.sendAction(true)
            }
        }
    }


    //endregion
}
