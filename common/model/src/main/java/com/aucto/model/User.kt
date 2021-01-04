package com.aucto.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * @author GWL
 */
@Parcelize
@Entity
class User : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var dbId: Int = 0
    var firstName: String = ""
    var lastName: String = ""
    var name: String = "test"
    var city: String = ""
    var state: String = ""
    var mobile: String = ""
    var email: String = ""
    var profileUrl: String = ""
    var password: String = ""
    var confirmPassword: String = ""
    var token: String =""
    @ColumnInfo(name = "userName")
    var userName: String = "$firstName $lastName"
        get() = "$firstName $lastName"
        set(value) {
            field = "$firstName $lastName"
        }

    override fun toString(): String {
        return "User(firstName='$firstName', lastName='$lastName', mobile='$mobile', email='$email', password='$password' , token $token)"
    }

}

fun String.createUniqueId() :String{
    return UUID.randomUUID().toString().substring(0, 15)
}