package com.aucto.core

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class Common {

    companion object {

        const val PERMISSION_REQ_CODE: Int = 101
        const val GALLERY = 1
        const val CAMERA = 2
        val MESSAGES_CHILD: String = "messages"



        /* fun getStringValue(@StringRes error: Int?): String {
             return error?.let { getContext().getString(error) } ?: ""
         }

         fun getColorValue(@ColorRes error: Int?): Int {
             return error?.let { getContext().resources.getColor(error) } ?: 0
         }

         fun getStringArrayValue(error: Int): List<String> {
             val array = getContext().resources.getStringArray(error)
             return array.toList()
         }
 */
        fun showKeyboard(view: View, status: Boolean) {
            val imm =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (status) imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            else imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

    }
}
