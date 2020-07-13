package com.acto.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aucto.chat.MyApplication
import com.aucto.core.Common.Companion.MESSAGES_CHILD
import com.aucto.model.Message
import com.aucto.model.User
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_chat.*


class ChatActivity : AppCompatActivity() {

    var mFirebaseAdapter: FirebaseRecyclerAdapter<Message, MessageViewHolder>? = null
    private lateinit var mFirebaseDatabaseReference: DatabaseReference
    val linearLayoutManager = LinearLayoutManager(this)
    val userToken = MyApplication.loginManager.getUser()?.token ?: ""
    val userName = MyApplication.loginManager.getUser()?.firstName ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        recyclerChat.layoutManager = linearLayoutManager
        recyclerChat.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        init()
        val user = intent?.getParcelableExtra<User>(INTENT_KEY_USER)
        val friendToken = user?.token ?: " "

        btnSend.setOnClickListener {
            val friendlyMessage = Message(
                userToken,
                friendToken,
                editWriteMessage.text.toString(),
                userName/* no image */
            )
            mFirebaseDatabaseReference.child(MESSAGES_CHILD)
                .push().setValue(friendlyMessage)
            editWriteMessage.setText("")
        }
    }

    fun init() {
        // New child entries
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().reference
        val parser = SnapshotParser<Message> { dataSnapshot ->
            val friendlyMessage = dataSnapshot.getValue(Message::class.java)
            friendlyMessage?.idReceiver = dataSnapshot.key
            friendlyMessage!!
        }

        val messagesRef = mFirebaseDatabaseReference.child(MESSAGES_CHILD)
        // val query = messagesRef.orderByValue()
        val options = FirebaseRecyclerOptions.Builder<Message>()
            .setQuery(messagesRef, parser)
            .build()
        mFirebaseAdapter = ChatAdapter(userToken, options)
        mFirebaseAdapter?.registerAdapterDataObserver(
            object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    super.onItemRangeInserted(positionStart, itemCount)
                    val friendlyMessageCount = mFirebaseAdapter?.getItemCount()
                    val lastVisiblePosition =
                        linearLayoutManager.findLastCompletelyVisibleItemPosition()
                    // If the recycler view is initially being loaded or the
                    // user is at the bottom of the list, scroll to the bottom
                    // of the list to show the newly added message.
                    if (friendlyMessageCount != null) {
                        if (lastVisiblePosition == -1 || positionStart >= friendlyMessageCount - 1 && lastVisiblePosition == positionStart - 1) {
                            recyclerChat.scrollToPosition(positionStart)
                        }
                    }
                }
            })

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