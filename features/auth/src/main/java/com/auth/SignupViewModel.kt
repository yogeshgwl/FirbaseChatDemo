package com.auth

import androidx.lifecycle.MutableLiveData
import com.aucto.core.ActionLiveData
import com.aucto.core.BaseViewModel
import com.aucto.core.FormValidator
import com.aucto.model.User
import com.aucto.networking.util.StringUtil
import com.auth.login.LoginField


class SignupViewModel(val model: SignupRepository) : BaseViewModel() {

    // region - Public Properties
    val user by lazy { User() }
    var loginClick = ActionLiveData<Boolean>()
    var signupSuccess = ActionLiveData<Boolean>()
    var showError: MutableLiveData<String> = MutableLiveData()
    val hideKeyboard by lazy { MutableLiveData<Boolean>() }
    // endregion


    // region - Button Clicks
    fun onLoginClick() {
        loginClick.sendAction(true)
    }

    fun onSignUpClick() {
        hideKeyboard.postValue(true)
        if (user.firstName.isEmpty()) {
            showError.postValue(StringUtil.getString(R.string.first_name_error))
        } else if (user.lastName.isEmpty()) {
            showError.postValue(StringUtil.getString(R.string.last_name_error))
        } else if (user.email.isEmpty()) {
            showError.postValue(StringUtil.getString(R.string.email_empty_error))
        } else if (!FormValidator.isEmailValid(user.email)) {
            showError.postValue(StringUtil.getString(R.string.error_invalid_email_address))
        } else if (user.mobile.isEmpty()) {
            showError.postValue(StringUtil.getString(R.string.mobile_number_error))
        } else if (user.mobile.length != 10) {
            showError.postValue(StringUtil.getString(R.string.mobile_number_invalid))
        } else if (user.password.isEmpty()) {
            showError.postValue(StringUtil.getString(R.string.password_empty_error))
        } else if (user.password.length < 6) {
            showError.postValue(StringUtil.getString(R.string.password_invalid))
        } else if (user.confirmPassword.isEmpty()) {
            showError.postValue(StringUtil.getString(R.string.con_password_empty_error))
        } else if (user.confirmPassword.length < 6) {
            showError.postValue(StringUtil.getString(R.string.con_password_invalid))
        } else if (user.confirmPassword != user
                .password
        ) {
            showError.postValue(StringUtil.getString(R.string.password_not_same))
        } else {
            model.saveUserData(user, signupSuccess)
        }
    }

    // endregion
    // region - After Text change lister  validate form fields and save value on model
    fun afterTextChange(loginField: LoginField, value: String) {
        user.apply {
            when (loginField) {
                LoginField.USER_NAME -> email = value
                LoginField.PASSWORD -> password = value
                LoginField.FIRST_NAME -> firstName = value
                LoginField.LAST_NAME -> lastName = value
                LoginField.CONFIRM_PASSWORD -> confirmPassword = value
                LoginField.MOBILE_NUMBER -> mobile = value
            }

        }
    }
    // endregion
}
