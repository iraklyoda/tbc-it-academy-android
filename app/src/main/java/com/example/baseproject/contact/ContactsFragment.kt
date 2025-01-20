package com.example.baseproject.contact

import android.util.Log.d
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.BaseFragment
import com.example.baseproject.databinding.FragmentContactsBinding

class ContactsFragment : BaseFragment<FragmentContactsBinding>(FragmentContactsBinding::inflate){
    private val contactViewModel: ContactViewModel by viewModels()

    private val adapter by lazy {
        ContactAdapter()
    }

    override fun start() {
        contactViewModel.contacts.observe(this) {
            val contacts = contactViewModel.contacts.value
            d("Contacts", contacts?.joinToString { contactPto ->
                "Last Message ${contactPto.lastMessage}"
            } ?: "nope")
            setContacts(contacts?.toList())
        }
    }

    private fun setContacts(contacts: List<ContactPto>?) {
        adapter.submitList(contacts ?: listOf())
        binding.rvContacts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvContacts.adapter = adapter
    }
}