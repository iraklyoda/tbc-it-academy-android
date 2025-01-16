package com.example.baseproject

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.databinding.FragmentRegisterBinding
import com.example.baseproject.field.FieldCardAdapter
import com.example.baseproject.field.FieldDto
import com.example.baseproject.field.FieldViewModel

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate){
    private lateinit var adapter: FieldCardAdapter
    private val viewModel: FieldViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fieldGroups = viewModel.getFieldGroups()
        setInputs(fieldGroups)

        val flattenedFields: List<FieldDto> = fieldGroups.flatten()

        binding.btnRegister.setOnClickListener {
            validateFields(flattenedFields)
        }
    }

    private fun setInputs(fieldGroups: List<List<FieldDto>>) {
        adapter = FieldCardAdapter(parentFragmentManager, fieldGroups)
        binding.rvInputCards.layoutManager = LinearLayoutManager(requireContext())
        binding.rvInputCards.adapter = adapter
    }

    private fun validateFields(list: List<FieldDto>): Boolean {
        for (field in list) {
            if (field.value.isEmpty() && field.required) {
                Toast.makeText(requireContext(),
                    getString(R.string.is_required, field.hint), Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }
}