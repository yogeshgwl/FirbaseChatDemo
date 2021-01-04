package com.acto.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import com.aucto.core.BaseAdapter
import com.aucto.model.User
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import java.lang.Exception


class UserAdapter(
    options: FirestorePagingOptions<User>,
    val clickListener: BaseAdapter.OnItemClickListener<User>
) :
    FirestorePagingAdapter<User, UserViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UserViewHolder(
            inflater.inflate(
                R.layout.rc_item_friend,
                parent,
                false
            )
        )
    }

    protected override fun onBindViewHolder(
        viewHolder: UserViewHolder, position: Int, user: User
    ) {
        //   mProgressBar.setVisibility(ProgressBar.INVISIBLE)
        Log.d("mTAG", "onBindViewHolder:  $user")
        viewHolder.bind(user, clickListener)
    }

    override fun onLoadingStateChanged(state: LoadingState) {
        super.onLoadingStateChanged(state)
        Log.d("mTAG", "onLoadingStateChanged: above $state $currentList $itemCount")
        when (state) {
            LoadingState.ERROR -> {
                Log.d("mTAG", "onLoadingStateChanged: error $state")
            }
        }
    }

    override fun onError(e: Exception) {
        super.onError(e)
        Log.d("mTAG", "onLoadingStateChanged: onError $e")

    }
}