package com.aucto.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aucto.navigation.features.ChatNavigation

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startLogin()
    }

    private fun startLogin() = ChatNavigation.dynamicStart?.let {
        startActivity(it)
        finish()
    }
}
