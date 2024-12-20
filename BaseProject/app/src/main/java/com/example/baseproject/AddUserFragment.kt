package com.example.baseproject

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.baseproject.databinding.FragmentAddUserBinding
import com.example.baseproject.databinding.FragmentSearchBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AddUserFragment : Fragment() {

    private var _binding: FragmentAddUserBinding? = null
    private val binding: FragmentAddUserBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddUserBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddUser.setOnClickListener {
            var formValid: Boolean = true

            val firstName = binding.inputFirstName.text.toString()
            val lastName = binding.inputLastName.text.toString()
            var birthdayDayString = binding.inputBirthdayDay.text.toString()
            val birthdayMonthString = binding.inputBirthdayMonth.text.toString()
            val birthdayYearString = binding.inputBirthdayYear.text.toString()
            val email: String = binding.inputEmail.text.toString()
            val address = binding.inputAddress.text.toString()
            val desc = binding.inputDesc.text.toString()

            var birthdayDay = if (birthdayDayString.isEmpty()) 0 else birthdayDayString.toInt()
            val birthdayMonth = if (birthdayMonthString.isEmpty()) 0 else birthdayMonthString.toInt()
            val birthdayYear = if (birthdayYearString.isEmpty()) 0 else birthdayYearString.toInt()


            if (firstName.isEmpty()) {
                binding.inputFirstName.error = getString(R.string.first_name_is_required)
                formValid = false
            }

            if (lastName.isEmpty()) {
                binding.inputLastName.error = "Last name is required"
                formValid = false
            }

            if (birthdayDay < 1 || birthdayDay > 31) {
                binding.inputBirthdayDay.error = "Please enter valid day"
                formValid = false
            }

            if (birthdayMonth < 1 || birthdayMonth > 12) {
                binding.inputBirthdayMonth.error = "Please enter valid month"
                formValid = false
            }

            if (birthdayYear < 1) {
                binding.inputBirthdayYear.error = "You are not from B.C bro"
                formValid = false
            }

            if (address.isEmpty()) {
                binding.inputAddress.error = "Address is required"
                formValid = false
            }

            if (email.isEmpty()) {
                binding.inputEmail.error = getString(R.string.email_is_required)
                formValid = false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.inputEmail.error = getString(R.string.please_enter_proper_email)
                formValid = false
            }

            if (binding.inputDesc.visibility == View.VISIBLE && desc.isEmpty()) {
                binding.inputDesc.error = "Please enter Desc"
                formValid = false
            }

            if (formValid) {
//                val birthdayDate = "${birthdayDay}-${birthdayMonth}-${birthdayYear}"
//                if (birthdayDay < 10) {
//
//                }
//                val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
//                val date: Date? = formatter.parse(birthdayDate)
//                val timestamp: Long? = date?.time

                val user = User(
                    id = User.currentId,
                    firstName = firstName,
                    lastName = lastName,
                    birthday = timestamp.toString(),
                    address = address,
                    email = email,
                    desc = desc
                )
                User.users.add(user)
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }
}