package com.endava.budgetplanner.authentication.ui.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.endava.budgetplanner.authentication.ui.vm.RegisterViewModel
import com.endava.budgetplanner.common.base.BaseFragment
import com.endava.budgetplanner.common.ext.provideAppComponent
import com.endava.budgetplanner.databinding.FragmentRegisterBinding
import javax.inject.Inject

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel by viewModels<RegisterViewModel> {
        factory
    }

    override fun onAttach(context: Context) {
        context.provideAppComponent().registerComponent().create().inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(true)
        requireActivity().actionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToRegisterDataFragment())
        }
        binding.txtSignIn.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        parent: Boolean
    ): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(inflater, container, parent)
    }

    companion object {

        @JvmStatic
        fun newInstance() = RegisterFragment()
    }
}