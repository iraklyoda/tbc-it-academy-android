package com.example.tricholog.ui.landing

import com.example.tricholog.BaseFragment
import com.example.tricholog.databinding.FragmentWelcomeBinding
import com.example.tricholog.ui.landing.pager.AuthFragment
import com.example.tricholog.ui.landing.pager.InfoFragment
import com.example.tricholog.ui.landing.pager.IntroFragment
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