package com.example.baseproject

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.transition.Visibility
import com.example.baseproject.databinding.FragmentSearchBinding
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inputSearch.doOnTextChanged { text, start, before, count ->
            var user: User?

            if (text.toString().isNotEmpty()) {
                user = User.findUser(input = text.toString())
            } else {
                user = null
            }

            if (user != null) {
                binding.viewUserNotFound.visibility = View.GONE

                val date: String = convertTimestampToDate(timestamp = user.birthday.toLong())

                binding.textUserId.text = user.id.toString()
                binding.textUserFirstName.text = user.firstName
                binding.textUserLastName.text = user.lastName
                binding.textUserBirthday.text = date
                binding.textUserAddress.text = user.address
                binding.textUserEmail.text = user.email

                if (!user.desc.isNullOrEmpty()) {
                    binding.textUserDesc.text = user.desc
                }

                return@doOnTextChanged
            }

            emptyUsers()
            binding.viewUserNotFound.visibility = View.VISIBLE
        }

        binding.btnAddUser.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, AddUserFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun emptyUsers() {
        with(binding) {
            textUserId.text = ""
            textUserFirstName.text = ""
            textUserLastName.text = ""
            textUserBirthday.text = ""
            textUserAddress.text = ""
            textUserEmail.text = ""
            textUserDesc.text = ""
        }
    }

    private fun convertTimestampToDate(timestamp: Long): String {
        val date = Date(timestamp)
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return formatter.format(date)
    }


}