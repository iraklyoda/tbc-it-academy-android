package com.example.baseproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.get
import com.example.baseproject.databinding.FragmentCreateAddressBinding
import com.example.baseproject.databinding.FragmentMainBinding
import com.example.baseproject.models.Address
import com.example.baseproject.models.AddressData
import com.example.baseproject.models.AddressType

class CreateAddressFragment : Fragment() {
    private var _binding: FragmentCreateAddressBinding? = null
    private val binding: FragmentCreateAddressBinding get() = _binding!!
    private var position: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateAddressBinding.inflate(inflater, container, false)
        setUp()

        val position = arguments?.getInt("position")

        if (position != null) {
            val address = AddressData.getAddresses()[position]

            binding.btnAddAddress.text = getString(R.string.update_address)
            binding.etAddress.setText(address.address)
            binding.etAddressName.setText(address.addressName)

            if(address.addressType == AddressType.WORK) {
                binding.radioHome.isChecked = true
            } else {
                binding.radioOffice.isChecked = true
            }
        }


        return binding.root
    }

    private fun setUp() {
        returnClick()
        addNewAddress()
    }

    private fun returnClick() {
        binding.btnReturn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun addNewAddress() {
        binding.btnAddAddress.setOnClickListener {
            val address: Address? = validateForm()

            address?.let {
                val position = arguments?.getInt("position")

                if (position != null) {
                    AddressData.updateAddress(
                        position = position,
                        address = address.address,
                        addressName = address.addressName,
                        addressType = address.addressType
                    )
                    requireActivity().supportFragmentManager.popBackStack()
                    return@setOnClickListener
                }

                AddressData.addAddress(address)
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }


    private fun validateForm(): Address? {
        var formValid: Boolean = true

        val addressName: String = binding.etAddressName.text.toString().trim()
        val address: String = binding.etAddress.text.toString().trim()

        var addressType: AddressType? = null

        if (binding.radioHome.isChecked)
            addressType = AddressType.HOME
        else if (binding.radioOffice.isChecked)
            addressType = AddressType.WORK
        else {
            formValid = false
            Toast.makeText(
                requireContext(),
                getString(R.string.please_choose_type), Toast.LENGTH_SHORT
            ).show()
        }

        if (addressName.isEmpty()) {
            formValid = false
            binding.etAddressName.error = getString(R.string.address_name_is_required)
        }

        if (address.isEmpty()) {
            formValid = false
            binding.etAddress.error = getString(R.string.address_is_required)
        }

        if (formValid && addressType != null) {
            return Address(
                addressName = addressName,
                address = address,
                addressType = addressType
            )
        }

        return null
    }
}