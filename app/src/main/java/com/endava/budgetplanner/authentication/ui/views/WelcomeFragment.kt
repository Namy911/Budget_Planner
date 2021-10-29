package com.endava.budgetplanner.authentication.ui.views

import android.view.LayoutInflater
import android.view.ViewGroup
import com.endava.budgetplanner.common.base.BaseFragment
import com.endava.budgetplanner.databinding.FragmentWelcomeBinding

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {

    override fun onStart() {
        super.onStart()
        binding.welcomeBSignIn.setOnClickListener {
            TODO("Navigate to main screen")
        }
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        parent: Boolean
    ): FragmentWelcomeBinding {
        return FragmentWelcomeBinding.inflate(inflater, container, parent)
    }
}