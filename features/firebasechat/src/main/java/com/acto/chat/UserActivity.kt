package com.acto.chat


import android.content.ClipData.Item
import android.content.Intent
import android.util.Log
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.acto.chat.databinding.ActivityUserBinding
import com.aucto.chat.MyApplication
import com.aucto.core.BaseActivity
import com.aucto.core.Common.Companion.USERS
import com.aucto.core.initViewModel
import com.aucto.model.User
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_user.*


val INTENT_KEY_USER = "user"
val INTENT_KEY_SELECTED_USER_TOKEN = "user_token"

class UserActivity : BaseActivity<ActivityUserBinding, UserViewModel>() {

    var mFirebaseAdapter: UserAdapter? = null
    private var mFirebaseDatabaseReference = Firebase.firestore
    val linearLayoutManager = LinearLayoutManager(this)
    val userToken = MyApplication.loginManager.getUser()?.token


    override fun getLayoutId(): Int {
        return R.layout.activity_user
    }

    override fun initObservers() {
        super.initObservers()
        recyclerUser.layoutManager = linearLayoutManager
        /*  recyclerUser.addItemDecoration(
              DividerItemDecoration(
                  this,
                  DividerItemDecoration.VERTICAL
              )
          )*/
        mViewModel.navigateOnMessage.observe {
            startActivity(Intent(this, ChatActivity::class.java).apply {
                Log.d("mTAGT", "send token:  ${it.token}")
                putExtra(INTENT_KEY_USER, it)
                putExtra(INTENT_KEY_SELECTED_USER_TOKEN, it.token)
            })
        }
        init()

    }

    override fun getViewModel(): UserViewModel {
        return initViewModel { UserViewModel() }
    }

    fun init() {

        // The "base query" is a query with no startAt/endAt/limit clauses that the adapter can use
// to form smaller queries for each page.  It should only include where() and orderBy() clauses
        val baseQuery: Query =
            mFirebaseDatabaseReference.collection(USERS)
                .whereNotEqualTo("token",userToken)

// The options for the adapter combine the paging configuration with query information
// and application-specific options for lifecycle, etc.
        val options = FirestorePagingOptions.Builder<User>()
            .setLifecycleOwner(this)
            .setQuery(baseQuery, mViewModel.config, User::class.java)
            .build()

        mFirebaseAdapter = UserAdapter(options, mViewModel)
        recyclerUser?.adapter = mFirebaseAdapter

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
