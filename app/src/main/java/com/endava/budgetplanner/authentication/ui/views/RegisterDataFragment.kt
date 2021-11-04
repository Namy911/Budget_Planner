package com.endava.budgetplanner.authentication.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.endava.budgetplanner.common.base.BaseFragment
import com.endava.budgetplanner.databinding.RegisterDataFragmentBinding

class RegisterDataFragment: BaseFragment<RegisterDataFragmentBinding>(){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            findNavController().navigate(RegisterDataFragmentDirections.actionRegisterDataFragmentToRegisterDataRolesFragment())
        }
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        parent: Boolean
    ): RegisterDataFragmentBinding {
        return RegisterDataFragmentBinding.inflate(inflater, container, parent)
    }
}