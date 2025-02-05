package com.example.baseproject

import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.baseproject.databinding.FragmentSecurityBinding
import com.example.baseproject.utils.showToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SecurityFragment : BaseFragment<FragmentSecurityBinding>(FragmentSecurityBinding::inflate) {
    private val passcode = "0934"
    private val SUSPENSION_TIME_MILLIS: Long = 30 * 1000

    private var enteredPasscode = ""
    private var failedAttempts = 0
    private var isSuspended = false


    override fun start() {
        setKeys()
    }

    override fun listeners() {
        removePasscodeDigit()
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
                    checkPassword()
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

    private fun checkPassword() {
        if (enteredPasscode.length == 4 && !isSuspended) {

            if (enteredPasscode == passcode) {
                Toast.makeText(requireContext(), "Success!", Toast.LENGTH_SHORT).show()
                failedAttempts = 0
            } else {
                enteredPasscode = ""
                setProgress()
                failedAttempts++

                if (failedAttempts <= 2) {
                    requireContext().showToast("Wrong Bro")
                } else if (failedAttempts == 3) {
                    requireContext().showToast("That's enough bro")
                } else if (failedAttempts == 4) {
                    requireContext().showToast("Try it one more time. I dare you!")
                } else {
                    requireContext().showToast("You have been suspended for 30 seconds 😒")
                    suspendUser()
                }
            }
        } else if (enteredPasscode.length == 4) {
            requireContext().showToast("Bro is suspended 🤣")
            enteredPasscode = ""
            setProgress()
        }
    }


    private fun setOvalStatus(oval: View, active: Boolean) {
        if (active)
            oval.setBackgroundResource(R.drawable.oval_active)
        else
            oval.setBackgroundResource(R.drawable.oval)
    }

    private fun suspendUser() {
        isSuspended = true

        viewLifecycleOwner.lifecycleScope.launch {
            delay(SUSPENSION_TIME_MILLIS)
            isSuspended = false
        }
    }

    private fun setProgress() {
        binding.apply {
            val ovals = listOf(viewDot1, viewDot2, viewDot3, viewDot4)

            ovals.onEach { oval ->
                setOvalStatus(oval, false)
            }

            if (enteredPasscode.isNotEmpty()) {
                setOvalStatus(ovals[0], true)
            }

            if (enteredPasscode.length >= 2) {
                setOvalStatus(ovals[1], true)
            }

            if (enteredPasscode.length >= 3) {
                setOvalStatus(ovals[2], true)
            }

            if (enteredPasscode.length == 4) {
                setOvalStatus(ovals[3], true)
            }
        }
    }


}