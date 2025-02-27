package com.example.tricholog.ui.dashboard

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.tricholog.BaseFragment
import com.example.tricholog.R
import com.example.tricholog.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>(FragmentDashboardBinding::inflate) {

    override fun start() {
        setBottomNavigation()
    }

    override fun listeners() {
    }

    private fun setBottomNavigation() {
        val dashboardNavHostFragment = childFragmentManager.findFragmentById(R.id.dashboard_fragment_cv) as NavHostFragment
        val navController = dashboardNavHostFragment.navController
        binding.dashboardBmNv.setupWithNavController(navController)
    }
}