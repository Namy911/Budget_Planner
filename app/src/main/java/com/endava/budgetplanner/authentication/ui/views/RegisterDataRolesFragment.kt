package com.endava.budgetplanner.authentication.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.endava.budgetplanner.R
import com.endava.budgetplanner.common.base.BaseFragment
import com.endava.budgetplanner.databinding.RegisterUserRolesFragmentBinding

class RegisterDataRolesFragment: BaseFragment<RegisterUserRolesFragmentBinding>(){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            findNavController().navigate(RegisterDataRolesFragmentDirections.actionRegisterDataRolesFragmentToWelcomeFragment2())
        }
        // delete this, it's stub
        val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_role, items)
        (binding.roles as? AutoCompleteTextView)?.apply {
            setAdapter(adapter)
            setOnItemClickListener { _, _, _, _ ->
                binding.menu.isHintEnabled = false
            }
            setDropDownBackgroundDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_menu_roles)
            )
        }
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        parent: Boolean
    ): RegisterUserRolesFragmentBinding {
        return RegisterUserRolesFragmentBinding.inflate(inflater, container, parent)
    }
}