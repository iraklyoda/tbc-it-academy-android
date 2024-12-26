package com.example.baseproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.databinding.FragmentMainBinding
import com.example.baseproject.models.Address
import com.example.baseproject.models.AddressData

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!
    private val addressAdapter:AddressAdapter by lazy {
        AddressAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        setUp()
        return binding.root
    }

    private fun setUp() {
        setAddressViewsAdapter()
        goToAddNewAddress()
    }

    private fun setAddressViewsAdapter() {
        val addressList: MutableList<Address> = AddressData.getAddresses()
        binding.rvAddresses.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAddresses.adapter = addressAdapter

        addressAdapter.submitList(addressList)

        addressAdapter.itemClickListener = { position: Int ->
            val fragment = CreateAddressFragment()
            val bundle = Bundle()
            bundle.putInt("position", position)
            fragment.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun goToAddNewAddress() {
        binding.btnAddAddress.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, CreateAddressFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}