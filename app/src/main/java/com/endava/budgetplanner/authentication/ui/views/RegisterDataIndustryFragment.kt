package com.endava.budgetplanner.authentication.ui.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.endava.budgetplanner.R
import com.endava.budgetplanner.authentication.ui.vm.RegisterIndustryViewModel
import com.endava.budgetplanner.authentication.ui.vm.states.RegisterFinalState
import com.endava.budgetplanner.common.base.BaseFragment
import com.endava.budgetplanner.common.callbacks.DefaultTextWatcher
import com.endava.budgetplanner.common.dialogs.ErrorDialog
import com.endava.budgetplanner.common.dialogs.LoadingDialog
import com.endava.budgetplanner.common.dialogs.OneButtonDialog
import com.endava.budgetplanner.common.ext.provideAppComponent
import com.endava.budgetplanner.data.models.user.User
import com.endava.budgetplanner.databinding.RegisterUserRolesFragmentBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "RegisterDataIndustryFragment"

class RegisterDataIndustryFragment : BaseFragment<RegisterUserRolesFragmentBinding>() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel by viewModels<RegisterIndustryViewModel> {
        factory
    }
    private val args: RegisterDataIndustryFragmentArgs by navArgs()
    private var industryItem: String? = null

    private lateinit var loadingDialog: LoadingDialog
    private val textWatcher by lazy {
        DefaultTextWatcher {
            viewModel.handleFields(getInitialBalance(), industryItem)
        }
    }

    override fun onAttach(context: Context) {
        context.provideAppComponent().registerComponent().create().inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setItemsToDropDownMenu()
        subscribeToObserver()
    }

    override fun onStart() {
        super.onStart()
        binding.edtInitialBalance.addTextChangedListener(textWatcher)
        binding.btnSignUp.setOnClickListener {
            viewModel.register(getUser())
        }
    }

    override fun onStop() {
        super.onStop()
        binding.edtInitialBalance.removeTextChangedListener(textWatcher)
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        parent: Boolean
    ): RegisterUserRolesFragmentBinding {
        return RegisterUserRolesFragmentBinding.inflate(inflater, container, parent)
    }

    private fun subscribeToObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest { state ->
                    when (state) {
                        RegisterFinalState.Empty -> {
                        }
                        is RegisterFinalState.Error -> {
                            dismissLoadingDialog()
                            showErrorAlertDialog(state.textId)
                        }
                        RegisterFinalState.Loading -> showLoadingDialog()
                        RegisterFinalState.NavigateToWelcome -> {
                            dismissLoadingDialog()
                            findNavController()
                                .navigate(
                                    RegisterDataIndustryFragmentDirections
                                        .actionRegisterDataRolesFragmentToWelcomeFragment2()
                                )
                        }
                        is RegisterFinalState.ButtonState -> binding.btnSignUp.isEnabled =
                            state.isEnabled
                        RegisterFinalState.ConnectionError -> showConnectionErrorDialog()
                    }
                }
        }
    }

    private fun setItemsToDropDownMenu() {
        val items = resources.getStringArray(R.array.industry)
        val adapter = ArrayAdapter(requireContext(), R.layout.item_role, items)
        (binding.industry as? AutoCompleteTextView)?.apply {
            setAdapter(adapter)
            setOnItemClickListener { parent, _, pos, _ ->
                binding.menu.isHintEnabled = false
                industryItem = parent.getItemAtPosition(pos).toString()
                viewModel.handleFields(getInitialBalance(), industryItem)
            }
            setDropDownBackgroundDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_menu_roles)
            )
        }
    }

    private fun showConnectionErrorDialog() {
        OneButtonDialog.newInstance(
            getString(R.string.lost_internet_connection),
            getString(R.string.lost_internet_connection_mes),
            getString(R.string.retry)
        ) {
            viewModel.register(getUser())
        }.show(parentFragmentManager, OneButtonDialog.TAG)
    }

    private fun showLoadingDialog() {
        loadingDialog = LoadingDialog.newInstance(
            getString(R.string.please_wait),
            getString(R.string.your_request_is_being_processed)
        )
        loadingDialog.show(parentFragmentManager, LoadingDialog.TAG)
    }

    private fun dismissLoadingDialog() {
        loadingDialog.dismiss()
    }

    private fun getUser() = User(
        args.user.firstName,
        args.user.lastName,
        args.user.password,
        industryItem,
        args.user.email,
        getInitialBalance().toDouble()
    )

    private fun showErrorAlertDialog(@StringRes message: Int) {
        ErrorDialog.newInstance(
            getString(R.string.error),
            getString(message),
            getString(R.string.got_it)
        ).show(parentFragmentManager, ErrorDialog.TAG)
    }

    private fun getInitialBalance() = binding.edtInitialBalance.text.toString().removePrefix("$")
}