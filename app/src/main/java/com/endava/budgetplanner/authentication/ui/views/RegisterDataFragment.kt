package com.endava.budgetplanner.authentication.ui.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.endava.budgetplanner.authentication.ui.vm.RegisterDataViewModel
import com.endava.budgetplanner.authentication.ui.vm.states.RegisterState
import com.endava.budgetplanner.common.base.BaseFragment
import com.endava.budgetplanner.common.callbacks.DefaultTextWatcher
import com.endava.budgetplanner.common.ext.provideAppComponent
import com.endava.budgetplanner.data.models.user.User
import com.endava.budgetplanner.databinding.RegisterDataFragmentBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "RegisterDataFragment"

class RegisterDataFragment : BaseFragment<RegisterDataFragmentBinding>() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel by viewModels<RegisterDataViewModel> {
        factory
    }
    private val textWatcher by lazy {
        DefaultTextWatcher {
            viewModel.handleFields(getName(), getSurname())
        }
    }

    private val args: RegisterDataFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        context.provideAppComponent().registerComponent().create().inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToObserver()
    }

    override fun onStart() = with(binding) {
        super.onStart()
        edtName.addTextChangedListener(textWatcher)
        edtSurname.addTextChangedListener(textWatcher)
        btnNext.setOnClickListener {
            viewModel.checkFieldsValidation(getName(), getSurname())
        }
    }

    override fun onStop() = with(binding) {
        super.onStop()
        edtName.removeTextChangedListener(textWatcher)
        edtSurname.removeTextChangedListener(textWatcher)
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        parent: Boolean
    ): RegisterDataFragmentBinding {
        return RegisterDataFragmentBinding.inflate(inflater, container, parent)
    }

    private fun subscribeToObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest { state ->
                    when (state) {
                        is RegisterState.ButtonState -> binding.btnNext.isEnabled = state.isEnabled
                        RegisterState.Empty -> {
                        }
                        is RegisterState.Error -> showSnackBar(state.textId)
                        RegisterState.NavigateToNext -> findNavController().navigate(
                            RegisterDataFragmentDirections
                                .actionRegisterDataFragmentToRegisterDataRolesFragment(
                                    User(
                                        firstName = getName(),
                                        lastName = getSurname(),
                                        password = args.user.password,
                                        industry = null,
                                        email = args.user.email,
                                        initialBalance = null
                                    )
                                )
                        )
                    }
                }
        }
    }

    private fun getName() = binding.edtName.text.toString()

    private fun getSurname() = binding.edtSurname.text.toString()
}