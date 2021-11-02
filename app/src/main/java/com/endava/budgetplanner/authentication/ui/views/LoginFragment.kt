package com.endava.budgetplanner.authentication.ui.views

import android.content.Context
import android.text.BoringLayout
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.endava.budgetplanner.authentication.ui.vm.LoginViewModel
import com.endava.budgetplanner.common.base.BaseFragment
import com.endava.budgetplanner.common.callbacks.DefaultTextWatcher
import com.endava.budgetplanner.common.ext.provideAppComponent
import com.endava.budgetplanner.common.utils.ValidationResult
import com.endava.budgetplanner.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private lateinit var viewModel: LoginViewModel
    private val textWatcher by lazy {
        DefaultTextWatcher {
            handleButton()
        }
    }

    override fun onAttach(context: Context) {
        context.provideAppComponent().inject(this)
        viewModel = context.provideAppComponent().factories.create(LoginViewModel::class.java)
        super.onAttach(context)
    }

    override fun onStart() {
        super.onStart()
        with(binding) {
            email.addTextChangedListener(textWatcher)
            password.addTextChangedListener(textWatcher)
            buttonSignIn.setOnClickListener {
                if (handleResult(viewModel.checkEmailValidation(email.text.toString()))) {
                    handleResult(viewModel.checkPasswordValidation(password.text.toString()))
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        binding.email.removeTextChangedListener(textWatcher)
        binding.password.removeTextChangedListener(textWatcher)
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        parent: Boolean
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, parent)
    }

    private fun handleResult(result: ValidationResult): Boolean {
        return when (result) {
            is ValidationResult.Error -> {
                Snackbar.make(binding.root, result.textId, Snackbar.LENGTH_SHORT).show()
                false
            }
            is ValidationResult.Success -> true
        }
    }

    private fun handleButton() {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        binding.buttonSignIn.isEnabled = viewModel.handleFields(email, password)
    }
}