package com.acto.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aucto.model.Message
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class ChatAdapter(val userId: String, options: FirebaseRecyclerOptions<Message>) :
    FirebaseRecyclerAdapter<Message, MessageViewHolder>(options) {

    companion object {
        const val ITEM_TYPE_SENDER = 0
        const val ITEM_TYPE_RECEIVER = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return when (viewType) {
            ITEM_TYPE_RECEIVER -> {
                MessageViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.rc_item_message_friend,
                        parent,
                        false
                    )
                )
            }
            else -> {
                UserMassageViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.rc_item_message_user,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int, model: Message) {
        holder.bind(model)
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).idSender == userId) ITEM_TYPE_SENDER else ITEM_TYPE_RECEIVER
    }
}