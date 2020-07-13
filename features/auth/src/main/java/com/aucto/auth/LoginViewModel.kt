package com.aucto.auth

import androidx.lifecycle.MutableLiveData
import com.aucto.core.ActionLiveData
import com.aucto.core.BaseViewModel
import com.aucto.core.FormValidator
import com.aucto.model.LoginItem
import com.aucto.networking.util.StringUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginViewModel(val model: LoginRepository) : BaseViewModel() {

    // region - Public Properties
    val loginItem by lazy { LoginItem(email = "", password = "") }
    var navigateToDashboard = ActionLiveData<Boolean>()
    var onSignupClick = ActionLiveData<Boolean>()
    var showError: MutableLiveData<String> = MutableLiveData()
    val hideKeyboard by lazy { MutableLiveData<Boolean>() }
    // endregion


    // region - Button Clicks
    fun onLoginClick() {
        hideKeyboard.postValue(true)
        if (loginItem.email.isEmpty())
            showError.postValue(
                StringUtil.getString(
                    R.string.email_empty_error
                )
            )
        else if (!FormValidator.isEmailValid(loginItem.email))
            showError.postValue(StringUtil.getString(R.string.error_invalid_email_address))
        else if (loginItem.password.isEmpty())
            showError.postValue(StringUtil.getString(R.string.password_empty_error))
        else {
            GlobalScope.launch(Dispatchers.IO) {
                val user = model.getUser(loginItem.email)
                withContext(Dispatchers.Main) {
                    when {
                        user == null -> showError.postValue(StringUtil.getString(R.string.user_not_exist))
                        user.password == loginItem.password -> {
                            model.saveUserDetails(user)
                            navigateToDashboard.sendAction(true)
                        }
                        else -> showError.postValue(StringUtil.getString(R.string.invalid_credential))
                    }
                }
            }
        }
    }

    fun onSignUpClick() {
        onSignupClick.sendAction(true)
    }

    // endregion
    // region - After Text change lister  validate form fields and save value on model
    fun afterTextChange(loginField: LoginField, value: String) {
        loginItem.apply {
            when (loginField) {
                LoginField.USER_NAME -> email = value
                LoginField.PASSWORD -> password = value
            }

        }
    }
    // endregion
}

enum class LoginField {
    USER_NAME, PASSWORD, FIRST_NAME, LAST_NAME, CONFIRM_PASSWORD, MOBILE_NUMBER
}
