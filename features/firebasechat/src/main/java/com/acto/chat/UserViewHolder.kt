package com.acto.chat

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.aucto.core.BaseAdapter
import com.aucto.model.User
import kotlinx.android.synthetic.main.rc_item_friend.view.*

open class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    @SuppressLint("SetTextI18n")
    open fun bind(model: User, clickListener: BaseAdapter.OnItemClickListener<User>) {
        itemView.txtUserName?.text = model.firstName + " " + model.lastName
        itemView.setOnClickListener {
            Log.d("mTAGT", "click token:  ${model.token}")
            clickListener.onItemClick(model, it)
        }
    }
}