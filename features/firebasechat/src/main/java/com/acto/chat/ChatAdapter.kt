package com.acto.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.aucto.model.Message
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import java.lang.Exception


class ChatAdapter(val userId: String, options: FirestorePagingOptions<Message>) :
    FirestorePagingAdapter<Message, MessageViewHolder>(options) {

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
        Log.d(
            "mTAGChats",
            "onLoadingStateChanged: error ${getItem(position)?.toObject(Message::class.java)}"
        )
        return/* if ((getItem(position)?.get("idSender") == userId)) ITEM_TYPE_SENDER else*/ ITEM_TYPE_RECEIVER
    }

    override fun onLoadingStateChanged(state: LoadingState) {
        super.onLoadingStateChanged(state)
        Log.d("mTAGChat", "onLoadingStateChanged: above $state $currentList $itemCount")
        when (state) {
            LoadingState.ERROR -> {
                Log.d("mTAGChats", "onLoadingStateChanged: error $state")
            }
        }
    }

    override fun onError(e: Exception) {
        super.onError(e)
        Log.d("mTAGChat", "onLoadingStateChanged: onError $e")

    }
}