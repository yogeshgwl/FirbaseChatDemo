package com.chat

import android.view.View
import com.aucto.model.Message
import kotlinx.android.synthetic.main.rc_item_message_user.view.*

class UserMassageViewHolder(view: View) : MessageViewHolder(view) {
    override fun bind(model: Message) {
        itemView.textContentUser?.text = model.text
    }
}