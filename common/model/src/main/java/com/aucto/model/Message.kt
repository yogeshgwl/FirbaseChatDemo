package com.aucto.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.TypeConverter
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity
@Parcelize
 class Message() : Parcelable {
    var idSender: String? = null
    var idReceiver: String? = null
    var text: String? = null
    var name: String = ""
    var timestamp: Date = Date()
}