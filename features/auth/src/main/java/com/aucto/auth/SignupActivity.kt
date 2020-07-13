package com.aucto.auth

import android.app.Activity
import android.os.Bundle
import com.aucto.auth.databinding.ActivitySignupBinding
import com.aucto.core.*
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : BaseActivity<ActivitySignupBinding, SignupViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_signup
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding.setVariable(BR.viewModel, mViewModel)
        setupToolbar(toolbar, enableUpButton = true, resTitleId = R.string._sign_up)
    }

    override fun getViewModel(): SignupViewModel = initViewModel {
        SignupViewModel(
            SignupRepository(
                loginManager = LoginManager.getInstance(this)
            )
        )
    }

    override fun initObservers() {
        super.initObservers()
        mViewModel.apply {
            showError.observe { mDataBinding.root.showSnackbar(it) }
            hideKeyboard.observe { mDataBinding.root.hideKeyboard() }
            loginClick.observe { finish() }
            signupSuccess.observe { finish() }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK)
        super.onBackPressed()
    }
}