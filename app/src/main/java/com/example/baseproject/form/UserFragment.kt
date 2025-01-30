package com.example.baseproject.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.baseproject.User
import com.example.baseproject.databinding.FragmentUserBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class UserFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentUserBinding? = null
    private val binding: FragmentUserBinding get() = _binding!!

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
    }

    private fun setData() {
        viewLifecycleOwner.lifecycleScope.launch {
            val user: User? = userViewModel.getUser()

            binding.apply {
                tvFirstName.text = user?.firstName
                tvLastName.text = user?.lastName
                tvEmail.text = user?.emailAddress
            }
        }
    }
}