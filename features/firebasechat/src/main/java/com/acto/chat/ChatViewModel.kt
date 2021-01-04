package com.acto.chat

import android.view.View
import com.aucto.core.ActionLiveData
import com.aucto.core.BaseAdapter
import com.aucto.core.BaseViewModel
import com.aucto.model.Message
import com.aucto.model.User

class ChatViewModel : BaseViewModel(), BaseAdapter.OnItemClickListener<Message> {

    val navigateOnMessage = ActionLiveData<Message>()
    override fun onItemClick(item: Message, view: View) {
        navigateOnMessage.postValue(item)
    }

    override fun onViewClicked(view: View, item: Message, position: Int) {

    }
}