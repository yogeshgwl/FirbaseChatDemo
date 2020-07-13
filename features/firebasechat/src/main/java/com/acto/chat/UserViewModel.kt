package com.acto.chat

import android.view.View
import com.aucto.core.ActionLiveData
import com.aucto.core.BaseAdapter
import com.aucto.core.BaseViewModel
import com.aucto.model.User

class UserViewModel : BaseViewModel(), BaseAdapter.OnItemClickListener<User> {

    val navigateOnMessage = ActionLiveData<User>()
    override fun onItemClick(item: User, view: View) {
        navigateOnMessage.postValue(item)
    }

    override fun onViewClicked(view: View, item: User, position: Int) {

    }
}