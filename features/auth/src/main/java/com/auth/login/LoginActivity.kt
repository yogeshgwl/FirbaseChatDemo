package com.auth.login

import android.content.Intent
import android.os.Bundle
import com.aucto.core.*
import com.auth.DashboardActivity
import com.auth.R
import com.auth.SignupActivity
import com.auth.databinding.ActivityLayoutLoginBinding

class LoginActivity : BaseActivity<ActivityLayoutLoginBinding, LoginViewModel>() {
    companion object {
        const val REQUEST_CODE = 101
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_layout_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding.setVariable(com.auth.BR.viewModel, mViewModel)
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
                startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                finishAffinity()
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