package com.acto.chat
import com.aucto.model.Message
/*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

*/
/**
 * Created by shike99 on 2017/02/25.
 *//*

object FirebaseDBHelper {

    // firebaseインスタンス初期化
    private val fireBase: FirebaseDatabase by lazy {
        val db = FirebaseDatabase.getInstance()
        db.setPersistenceEnabled(true)
        db
    }

    // メッセージ追加
    fun addMessage(msg: Message, callError: (String) -> Unit) {
        val ref = fireBase.getReference("message")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                callError(error.toString())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                ref.push().setValue(msg)
            }

        })
    }

    // メッセージ取得
    fun fetchMessages(call: (MutableList<Message>) -> Unit, callError: (String) -> Unit) {
        val reference = fireBase.getReference("message")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                callError(error.toString())
            }

            override fun onDataChange(result: DataSnapshot) {
                val list = mutableListOf<Message>()
                result.children.forEach { element ->
                    element.getValue(Message::class.java)?.also {
                        list.add(it)
                    }
                }
                call(list)
            }
        })
    }
}*/
