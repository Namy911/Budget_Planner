package com.endava.budgetplanner.authentication.ui.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.endava.budgetplanner.authentication.ui.vm.RegisterViewModel
import com.endava.budgetplanner.common.base.BaseFragment
import com.endava.budgetplanner.common.ext.provideAppComponent
import com.endava.budgetplanner.databinding.FragmentRegisterBinding

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private lateinit var viewModel: RegisterViewModel

    override fun onAttach(context: Context) {
        context.provideAppComponent().inject(this)
        viewModel = context.provideAppComponent().factories.create(RegisterViewModel::class.java)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textCase.text = viewModel.getText()
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