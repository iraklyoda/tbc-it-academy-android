package com.example.tricholog.ui.landing.pager

import androidx.lifecycle.lifecycleScope
import com.example.tricholog.BaseFragment
import com.example.tricholog.databinding.FragmentInfoBinding
import com.example.tricholog.utils.fadeIn
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InfoFragment : BaseFragment<FragmentInfoBinding>(FragmentInfoBinding::inflate) {
    override fun start() {
        setAnimations()
    }

    override fun listeners() {

    }

    private fun setAnimations() {
        viewLifecycleOwner.lifecycleScope.launch {
            with(binding) {
                tvHeader.fadeIn(1000)
                ivKodeeSharing.fadeIn(300)
                tvInfo01.fadeIn(300)
                delay(1000)
                tvInfo02.fadeIn(300)
                ivKodeeSitting.fadeIn(300)
                delay(1000)
                ivKodeeJumping.fadeIn(300)
                tvInfo03.fadeIn(300)
            }
        }
    }
}