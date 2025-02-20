package com.example.baseproject.ui.landing

import com.example.baseproject.BaseFragment
import com.example.baseproject.databinding.FragmentWelcomeBinding
import com.example.baseproject.ui.landing.pager.AuthFragment
import com.example.baseproject.ui.landing.pager.InfoFragment
import com.example.baseproject.ui.landing.pager.IntroFragment
import com.google.android.material.tabs.TabLayoutMediator

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {
    override fun start() {
        setPagerAdapter()
    }

    override fun listeners() {

    }

    private fun setPagerAdapter() {
        val welcomePagerFragments = listOf(IntroFragment(), InfoFragment(), AuthFragment())
        val adapter = WelcomeViewPagerAdapter(this, welcomePagerFragments)
        binding.vp2Landing.adapter = adapter

        TabLayoutMediator(binding.tlLanding, binding.vp2Landing) {_, _ -> }.attach()
    }
}