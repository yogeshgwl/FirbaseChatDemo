package com.aucto.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Message(
    var idSender: String? = null,
    var idReceiver: String? = null,
    var text: String? = null,
    val name: String = "",
    var timestamp: Long = 0
) : Parcelable {

}