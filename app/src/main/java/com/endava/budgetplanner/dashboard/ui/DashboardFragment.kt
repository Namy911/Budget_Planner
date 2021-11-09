package com.endava.budgetplanner.dashboard.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.endava.budgetplanner.common.base.BaseFragment
import com.endava.budgetplanner.databinding.FragmentDashboardBinding

class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        parent: Boolean
    ): FragmentDashboardBinding {
        return FragmentDashboardBinding.inflate(inflater, container, false)
    }
}