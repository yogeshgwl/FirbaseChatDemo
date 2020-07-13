package com.chat


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aucto.model.Message
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatFragment : Fragment() {

    var mFirebaseAdapter: FirebaseRecyclerAdapter<Message, MessageViewHolder>? = null
    private lateinit var mFirebaseDatabaseReference: DatabaseReference
    val MESSAGES_CHILD: String = "messages"
    val linearLayoutManager = LinearLayoutManager(activity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.blog.R.layout.activity_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerChat.layoutManager = linearLayoutManager
        recyclerChat.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        init()

        btnSend.setOnClickListener(View.OnClickListener {
            val friendlyMessage = Message(
                "anonymous",
                "Monika",
                editWriteMessage.text.toString(),
                "Monika123" /* no image */
            )
            mFirebaseDatabaseReference.child(MESSAGES_CHILD)
                .push().setValue(friendlyMessage)
            editWriteMessage.setText("")
        })
        mFirebaseDatabaseReference.child(MESSAGES_CHILD)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    Log.d("onChildAdded", "onChildAdded called")
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    Log.d("onChildAdded", "onChildChanged called")

                }

                override fun onCancelled(error: DatabaseError) {
                    //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
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
        mFirebaseAdapter = object : FirebaseRecyclerAdapter<Message, MessageViewHolder>(options) {
            override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MessageViewHolder {
                val inflater = LayoutInflater.from(viewGroup.context)
                return MessageViewHolder(
                    inflater.inflate(
                        com.blog.R.layout.rc_item_message_friend,
                        viewGroup,
                        false
                    )
                )
            }

            protected override fun onBindViewHolder(
                viewHolder: MessageViewHolder, position: Int, friendlyMessage: Message
            ) {
                //   mProgressBar.setVisibility(ProgressBar.INVISIBLE)
                viewHolder.bind(friendlyMessage)
            }

            override fun getItemViewType(position: Int): Int {
                return super.getItemViewType(position)
            }
        }
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

    companion object {
        @JvmStatic
        fun newInstance() = ChatFragment()
    }
}
