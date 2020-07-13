package com.acto.chat


import android.content.Intent
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.acto.chat.databinding.ActivityUserBinding
import com.aucto.chat.MyApplication
import com.aucto.core.BaseActivity
import com.aucto.core.initViewModel
import com.aucto.model.User
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_user.*

val INTENT_KEY_USER = "user"
val USER_CHILD: String = "Users"

class UserActivity : BaseActivity<ActivityUserBinding, UserViewModel>() {

    var mFirebaseAdapter: UserAdapter? = null
    private lateinit var mFirebaseDatabaseReference: DatabaseReference
    val linearLayoutManager = LinearLayoutManager(this)
    val userToken = MyApplication.loginManager.getUser()?.token


    override fun getLayoutId(): Int {
        return R.layout.activity_user
    }

    override fun initObservers() {
        super.initObservers()
        recyclerUser.layoutManager = linearLayoutManager
        recyclerUser.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        mViewModel.navigateOnMessage.observe {
            startActivity(Intent(this, ChatActivity::class.java).apply {
                putExtra(INTENT_KEY_USER, it)
            })
        }
        init()

    }

    override fun getViewModel(): UserViewModel {
        return initViewModel { UserViewModel() }
    }

    fun init() {
        // New child entries
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().reference
        val parser = SnapshotParser<User> { dataSnapshot ->
            val friendlyMessage = dataSnapshot.getValue(User::class.java)
            friendlyMessage!!
        }

        val messagesRef = mFirebaseDatabaseReference.child(USER_CHILD)
        // val query = messagesRef.orderByValue()
        val options = FirebaseRecyclerOptions.Builder<User>()
            .setQuery(messagesRef, parser)
            .build()
        mFirebaseAdapter = UserAdapter(options, mViewModel)
        recyclerChat?.adapter = mFirebaseAdapter

    }

    override fun onPause() {
        mFirebaseAdapter?.stopListening()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mFirebaseAdapter?.startListening()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChatFragment()
    }
}
