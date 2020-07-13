package com.acto.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aucto.core.BaseAdapter
import com.aucto.model.User
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class UserAdapter(
    options: FirebaseRecyclerOptions<User>,
    val clickListener: BaseAdapter.OnItemClickListener<User>
) :
    FirebaseRecyclerAdapter<User, UserViewHolder>(options) {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): UserViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        return UserViewHolder(
            inflater.inflate(
                R.layout.rc_item_friend,
                viewGroup,
                false
            )
        )
    }

    protected override fun onBindViewHolder(
        viewHolder: UserViewHolder, position: Int, user: User
    ) {
        //   mProgressBar.setVisibility(ProgressBar.INVISIBLE)
        viewHolder.bind(user, clickListener)
    }

}