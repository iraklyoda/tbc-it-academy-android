package com.example.baseproject.ui.locations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentMarkerInfoBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MarkerInfoBottomSheetDialogFragment : BottomSheetDialogFragment() {


    private var _binding: FragmentMarkerInfoBottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarkerInfoBottomSheetDialogBinding.inflate(inflater, container, false)

        val markerTitle = arguments?.getString(ARG_TITLE) ?: "Marker Title"
        val markerAddress = arguments?.getString(ARG_ADDRESS) ?: "No Address Available"

        binding.tvHeader.text = markerTitle
        binding.tvData.text = markerAddress

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear binding reference to avoid memory leaks
    }

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_ADDRESS = "body"

        fun newInstance(title: String, address: String): MarkerInfoBottomSheetDialogFragment {
            val fragment = MarkerInfoBottomSheetDialogFragment()
            val args = Bundle().apply {
                putString(ARG_TITLE, title)
                putString(ARG_ADDRESS, address)
            }
            fragment.arguments = args
            return fragment
        }
    }
}