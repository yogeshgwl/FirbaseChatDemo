package com.aucto.auth

import android.content.Intent
import android.os.Bundle
import com.aucto.auth.databinding.ActivityLayoutLoginBinding
import com.aucto.core.*
import com.aucto.navigation.features.UserNavigation

class LoginActivity : BaseActivity<ActivityLayoutLoginBinding, LoginViewModel>() {
    companion object {
        const val REQUEST_CODE = 101
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_layout_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding.setVariable(BR.viewModel, mViewModel)
    }

    override fun getViewModel(): LoginViewModel = initViewModel {
        LoginViewModel(
            LoginRepository(
                loginManager = LoginManager.getInstance(this)
            )
        )
    }

    override fun initObservers() {
        super.initObservers()
        mViewModel.apply {
            //navigateOnNext.observe { navigateOnHome() }
            showError.observe { mDataBinding.root.showSnackbar(it) }
            hideKeyboard.observe { mDataBinding.root.hideKeyboard() }
            navigateToDashboard.observe {
                UserNavigation.dynamicStart?.let {
                    startActivity(it)
                }
            }
            onSignupClick.observe {
                if (it)
                    startActivityForResult(
                        Intent(this@LoginActivity, SignupActivity::class.java), REQUEST_CODE
                    )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            clearLoginData()
        }
    }

    private fun clearLoginData() {
        mDataBinding.textPassword.setText("")
        mDataBinding.textLogin.setText("")
    }
}