package com.example.tricholog.ui.dashboard.home.profile

import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.tricholog.BaseFragment
import com.example.tricholog.MainActivity
import com.example.tricholog.R
import com.example.tricholog.databinding.FragmentProfileBinding
import com.example.tricholog.ui.dashboard.DashboardFragmentDirections
import com.example.tricholog.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun start() {
        setUserData()
        handleArticleStack()
    }

    override fun listeners() {
        handleReturnBtn()
        onLogoutBtn()
    }

    private fun onLogoutBtn() {
        binding.btnLogout.setOnClickListener {
            profileViewModel.logout()
            logout()
        }
    }

    private fun logout() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileViewModel.logoutState.collectLatest { state ->
                    if (state) {
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        requireActivity().finish()
                    } else {
                        requireContext().showToast("Error")
                    }
                }
            }
        }
    }

    private fun setUserData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileViewModel.userState.collectLatest { user ->
                    binding.tvName.text = user?.username
                    binding.tvEmail.text = user?.email
                }
            }
        }
    }

    private fun handleArticleStack() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack(R.id.homeFragment, false)
                }
            })
    }

    private fun handleReturnBtn() {
        binding.btnReturn.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
        }
    }

}