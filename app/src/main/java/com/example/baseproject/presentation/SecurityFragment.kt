package com.example.baseproject.presentation

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.baseproject.BaseFragment
import com.example.baseproject.R
import com.example.baseproject.data.local.UserPreferencesRepository
import com.example.baseproject.databinding.FragmentSecurityBinding
import com.example.baseproject.utils.showToast
import kotlinx.coroutines.launch

class SecurityFragment : BaseFragment<FragmentSecurityBinding>(FragmentSecurityBinding::inflate) {

    private val securityViewModel: SecurityViewModel by viewModels() {
        SecurityViewModel.Factory(
            userPreferencesRepository = UserPreferencesRepository.getInstance(requireContext())
        )
    }

    private var enteredPasscode = ""
    private var failedAttempts = 0

    override fun start() {
        setKeys()
        setFailedAttempts()
    }

    override fun listeners() {
        removePasscodeDigit()
        handleFingerprint()
    }

    private fun setKeys() {
        binding.apply {
            val buttons = listOf(
                btnKey0,
                btnKey1,
                btnKey2,
                btnKey3,
                btnKey4,
                btnKey5,
                btnKey6,
                btnKey7,
                btnKey8,
                btnKey9,
                btnKey0
            )

            buttons.forEach { button ->
                button.setOnClickListener {
                    val number = button.text.toString()
                    updatePasscode(number)
                    setProgress()
                    handlePasscode()
                }
            }
        }
    }

    private fun handlePasscode() {
        viewLifecycleOwner.lifecycleScope.launch {
            if (enteredPasscode.length == 4 && !securityViewModel.checkSuspend()) {
                if (securityViewModel.checkPasscode(enteredPasscode)) {
                    handleSuccessAttempt()
                } else {
                    handleFailedAttempt()
                }

            } else if (enteredPasscode.length == 4) {
                requireContext().showToast(getString(R.string.bro_is_suspended))
                resetState()
            }
        }
    }

    private fun handleSuccessAttempt() {
        Toast.makeText(requireContext(), getString(R.string.success), Toast.LENGTH_SHORT).show()
        failedAttempts = 0

        viewLifecycleOwner.lifecycleScope.launch {
            securityViewModel.clearUserBeenSuspended()
            securityViewModel.clearSuspension()
        }

        resetState()
    }

    private fun handleFailedAttempt() {
        viewLifecycleOwner.lifecycleScope.launch {
            resetState()
            failedAttempts++

            when (failedAttempts) {
                1, 2 -> requireContext().showToast(getString(R.string.wrong_bro))
                3 -> requireContext().showToast(getString(R.string.that_s_enough_bro))
                4 -> requireContext().showToast(getString(R.string.try_it_one_more_time_i_dare_you))
                5 -> {
                    requireContext().showToast(getString(R.string.you_have_been_suspended_for_30_seconds))
                    securityViewModel.suspendUser()
                }

                else -> {
                    requireContext().showToast(getString(R.string.you_have_been_suspended_for_60_seconds))
                    securityViewModel.suspendUser(true)
                }
            }
        }
    }

    private fun updatePasscode(number: String) {
        if (enteredPasscode.length < 4) {
            enteredPasscode += number
        }
    }

    private fun removePasscodeDigit() {
        binding.btnRemove.setOnClickListener {
            if (enteredPasscode.isNotEmpty()) {
                enteredPasscode = enteredPasscode.dropLast(1)
                setProgress()
            }
        }
    }

    private fun setFailedAttempts() {
        viewLifecycleOwner.lifecycleScope.launch {
            if (securityViewModel.checkUserBeenSuspended()) {
                failedAttempts = 6
            }
        }
    }

    private fun handleFingerprint() {
        binding.btnFingerprint.setOnLongClickListener {
            requireContext().showToast("Wrong Finger")
            true
        }
    }

    private fun setProgressStatus(oval: View, active: Boolean) {
        if (active)
            oval.setBackgroundResource(R.drawable.oval_active)
        else
            oval.setBackgroundResource(R.drawable.oval)
    }

    private fun setProgress() {
        binding.apply {
            val ovals = listOf(viewDot1, viewDot2, viewDot3, viewDot4)

            ovals.onEach { oval ->
                setProgressStatus(oval, false)
            }

            if (enteredPasscode.isNotEmpty()) {
                setProgressStatus(ovals[0], true)
            }

            if (enteredPasscode.length >= 2) {
                setProgressStatus(ovals[1], true)
            }

            if (enteredPasscode.length >= 3) {
                setProgressStatus(ovals[2], true)
            }

            if (enteredPasscode.length == 4) {
                setProgressStatus(ovals[3], true)
            }
        }
    }

    private fun resetState() {
        enteredPasscode = ""
        setProgress()
    }
}