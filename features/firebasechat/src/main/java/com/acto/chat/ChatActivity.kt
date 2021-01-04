package com.acto.chat

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.acto.chat.databinding.ActivityChatBinding
import com.aucto.chat.MyApplication
import com.aucto.core.BaseActivity
import com.aucto.core.Common
import com.aucto.core.Common.Companion.MESSAGES_CHILD
import com.aucto.core.initViewModel
import com.aucto.model.Message
import com.aucto.model.User
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_chat.*


class ChatActivity : BaseActivity<ActivityChatBinding, ChatViewModel>() {

    var mFirebaseAdapter: ChatAdapter? = null
    private var mFirebaseDatabaseReference = Firebase.firestore
    val linearLayoutManager = LinearLayoutManager(this)
    val userToken = MyApplication.loginManager.getUser()?.token ?: ""
    val userName = MyApplication.loginManager.getUser()?.firstName ?: ""
    var friendToken: String? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_chat
    }

    override fun getViewModel(): ChatViewModel {
        return initViewModel { ChatViewModel() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recyclerChat.layoutManager = linearLayoutManager
        recyclerChat.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        val user = intent?.getParcelableExtra<User>(INTENT_KEY_USER)
        friendToken = intent?.getStringExtra(INTENT_KEY_SELECTED_USER_TOKEN)
        // friendToken = user?.token ?: " "
        Log.d("mTAGT", "receive token:  ${friendToken}")

        Log.d("mTAGChat", "friendToken: above $userToken $friendToken ")

        init()
        btnSend.setOnClickListener {
            val friendlyMessage = Message().apply {
                idSender = userToken
                idReceiver = friendToken
                text = editWriteMessage.text.toString()
                name = userName/* no image */
                timestamp = Timestamp.now().toDate()
            }
            mFirebaseDatabaseReference.collection(MESSAGES_CHILD).add(friendlyMessage)
            editWriteMessage.setText("")
        }
    }

    private fun init() {
        // New child entries
        mFirebaseDatabaseReference = Firebase.firestore

        val query = mFirebaseDatabaseReference.collection(MESSAGES_CHILD)
                .whereEqualTo("idReceiver", "$friendToken")
        //.whereEqualTo("idReceiver", friendToken)
        // val query = messagesRef.orderByValue()


// The options for the adapter combine the paging configuration with query information
// and application-specific options for lifecycle, etc.
        val options = FirestorePagingOptions.Builder<Message>()
            .setLifecycleOwner(this)
            .setQuery(query, mViewModel.config, Message::class.java)
            .build()

        mFirebaseAdapter = ChatAdapter(userToken, options)

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
}