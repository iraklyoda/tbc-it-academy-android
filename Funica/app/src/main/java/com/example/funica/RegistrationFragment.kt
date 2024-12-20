package com.example.funica

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.funica.databinding.FragmentMainBinding
import com.example.funica.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnReturn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.btnShowPassword.setOnClickListener {
            if (binding.inputPassword.inputType == InputType.TYPE_CLASS_TEXT) {
                // This doesn't work
                binding.inputPassword.inputType = InputType.TYPE_NUMBER_VARIATION_PASSWORD
            } else {
                binding.inputPassword.inputType = InputType.TYPE_CLASS_TEXT
            }
        }
    }


}