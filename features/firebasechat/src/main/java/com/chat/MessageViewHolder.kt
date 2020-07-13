package com.chat

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.aucto.model.Message
import kotlinx.android.synthetic.main.rc_item_message_friend.view.*

open class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
   open fun bind(model: Message) {
        itemView.textContentFriend?.text = model.text
    }
}