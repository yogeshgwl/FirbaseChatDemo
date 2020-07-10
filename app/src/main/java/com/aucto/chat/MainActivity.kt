package com.aucto.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aucto.navigation.features.BlogNavigation

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startLogin()
    }

    private fun startLogin() = BlogNavigation.dynamicStart?.let {
        startActivity(it)
        finish()
    }
}
