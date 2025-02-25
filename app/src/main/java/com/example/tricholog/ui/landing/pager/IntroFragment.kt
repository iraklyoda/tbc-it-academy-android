package com.example.tricholog.ui.landing.pager

import androidx.lifecycle.lifecycleScope
import com.example.tricholog.BaseFragment
import com.example.tricholog.databinding.FragmentIntroBinding
import com.example.tricholog.utils.fadeIn
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class IntroFragment : BaseFragment<FragmentIntroBinding>(FragmentIntroBinding::inflate) {
    override fun start() {
        setAnimations()
    }

    override fun listeners() {

    }

    private fun setAnimations() {
        viewLifecycleOwner.lifecycleScope.launch {
            with(binding) {
                tvHeader.fadeIn(800)
                ivKodeeGreeting.fadeIn(200)
                tvDescription.fadeIn(400)
                delay(2000)
                tvGoal.fadeIn(700)
            }
        }
    }
}