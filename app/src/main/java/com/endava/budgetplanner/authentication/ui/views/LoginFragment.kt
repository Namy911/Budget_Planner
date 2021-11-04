package com.endava.budgetplanner.authentication.ui.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.endava.budgetplanner.R
import com.endava.budgetplanner.authentication.ui.vm.LoginViewModel
import com.endava.budgetplanner.authentication.ui.vm.states.LoginState
import com.endava.budgetplanner.common.dialogs.LoadingDialog
import com.endava.budgetplanner.common.base.BaseFragment
import com.endava.budgetplanner.common.callbacks.DefaultTextWatcher
import com.endava.budgetplanner.common.dialogs.ErrorDialog
import com.endava.budgetplanner.common.ext.provideAppComponent
import com.endava.budgetplanner.databinding.FragmentLoginBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "LoginFragment"
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var loadingDialog: LoadingDialog

    private val viewModel by viewModels<LoginViewModel> {
        factory
    }
    private val textWatcher by lazy {
        DefaultTextWatcher {
            viewModel.handleFields(getEmail(), getPassword())
        }
    }

    override fun onAttach(context: Context) {
        context.provideAppComponent().loginComponent().create().inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObserver()
        loadingDialog = LoadingDialog()
    }

    override fun onStart() {
        super.onStart()
        with(binding) {
            loginEmail.addTextChangedListener(textWatcher)
            loginPassword.addTextChangedListener(textWatcher)
            buttonSignIn.setOnClickListener {
                viewModel.checkFieldsValidation(getEmail(), getPassword())
            }
            buttonSignup.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        binding.loginEmail.removeTextChangedListener(textWatcher)
        binding.loginPassword.removeTextChangedListener(textWatcher)
    }

    override fun onDestroyView() {
        viewModel.cancelJob()
        super.onDestroyView()
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        parent: Boolean
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, parent)
    }

    private fun subscribeToObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loginState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest { state ->
                    when (state) {
                        is LoginState.ButtonState -> binding.buttonSignIn.isEnabled =
                            state.isEnabled
                        LoginState.Empty -> {
                        }
                        is LoginState.NetworkError -> {
                            dismissLoadingDialog()
                            showErrorDialog(state.textId)
                        }
                        is LoginState.Success -> {
                            dismissLoadingDialog()
                            //for DEMO
                            Log.d("MyLog", state.token)
//                            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                        }
                        LoginState.Loading -> showLoadingDialog()
                        is LoginState.ValidationError -> showSnackBar(state.textId)
                    }
                }
        }
    }

    private fun showErrorDialog(@StringRes textId: Int) {
        ErrorDialog.newInstance(
            getString(R.string.authentication_failed),
            getString(textId),
            getString(R.string.got_it)
        ).show(parentFragmentManager, ErrorDialog.TAG)
    }

    private fun getEmail() = binding.loginEmail.text.toString()

    private fun getPassword() = binding.loginPassword.text.toString()

    private fun showLoadingDialog() = loadingDialog.show(parentFragmentManager, LoadingDialog.TAG)

    private fun dismissLoadingDialog() = loadingDialog.dismiss()
}