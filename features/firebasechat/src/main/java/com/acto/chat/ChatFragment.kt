package com.acto.chat


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aucto.chat.MyApplication
import com.aucto.model.Message
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.activity_chat.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val KEY_FRIEND_TOKEN = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatFragment : Fragment() {
    /*
        var mFirebaseAdapter: FirebaseRecyclerAdapter<Message, MessageViewHolder>? = null
        private lateinit var mFirebaseDatabaseReference: DatabaseReference*/
    val MESSAGES_CHILD: String = "messages"
    val linearLayoutManager = LinearLayoutManager(activity)
    val userToken = MyApplication.loginManager.getUser()?.token ?: ""
    val userName = MyApplication.loginManager.getUser()?.firstName ?: ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_chat, container, false)
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
        // init()
        val friendToken = arguments?.getString(KEY_FRIEND_TOKEN) ?: ""
        btnSend.setOnClickListener {
            val friendlyMessage = Message(

            ).apply {
                userToken
                friendToken
                editWriteMessage.text.toString()
                userName/* no image */
                Timestamp.now().toDate()
            }
            /*   mFirebaseDatabaseReference.child(MESSAGES_CHILD)
                   .push().setValue(friendlyMessage)
               editWriteMessage.setText("")*/
        }
    }

    /* fun init() {
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
     }*/

    companion object {
        @JvmStatic
        fun newInstance(friendToken: String) = ChatFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_FRIEND_TOKEN, friendToken)
            }
        }
    }
}
