package com.aucto.navigation.features

import android.content.Intent
import com.aucto.navigation.loadIntentOrNull

object UserNavigation : DynamicFeature<Intent> {

    private const val USER_HOME = "com.acto.chat.UserActivity"

    override val dynamicStart: Intent?
        get() = USER_HOME.loadIntentOrNull()
}
