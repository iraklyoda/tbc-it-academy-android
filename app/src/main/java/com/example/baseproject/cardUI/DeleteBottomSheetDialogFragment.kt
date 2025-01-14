package com.example.baseproject.cardUI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentDeleteBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DeleteBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentDeleteBottomSheetDialogBinding? = null
    private val binding: FragmentDeleteBottomSheetDialogBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeleteBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnYes.setOnClickListener {
            val resultBundle = Bundle().apply {
                putBoolean("deleteConfirmed", true)
            }
            setFragmentResult("deleteRequest", resultBundle)
            dismiss()
        }

        binding.btnNo.setOnClickListener {
            dismiss()
        }
    }

}