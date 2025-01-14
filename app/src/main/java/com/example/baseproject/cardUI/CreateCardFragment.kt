package com.example.baseproject.cardUI

import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.baseproject.BaseFragment
import com.example.baseproject.R
import com.example.baseproject.card.CardType
import com.example.baseproject.card.CardViewModel
import com.example.baseproject.databinding.FragmentCreateCardBinding
import com.example.baseproject.getLastAsInt


class CreateCardFragment :
    BaseFragment<FragmentCreateCardBinding>(FragmentCreateCardBinding::inflate) {
    private val viewModel: CardViewModel by activityViewModels()

    private var formIsValid: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp()
    }

    private fun setUp() {
        binding.btnReturn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.rgCardType.check(binding.rbMastercard.id)

        // Formats and updates data on card layout
        handleInputs()

        binding.btnAddCard.setOnClickListener {
            submitForm()
        }
    }

    private fun submitForm() {
        formIsValid = true

        with(binding) {
            val name: String = etName.getString()
            val number: String = etNumber.getString()
            val expire: String = etExpire.getString()
            val cvv: String = etCvv.getString()

            if (name.length < 3) {
                etName.showError(getString(R.string.please_enter_proper_name))
            }

            if (number.length < 16) {
                etNumber.showError(getString(R.string.please_enter_valid_card_number))
            }

            if (expire.length != 5) {
                etExpire.showError(getString(R.string.please_enter_proper_date))
            } else if (isDateExpired(expire)) {
                etExpire.showError(getString(R.string.date_is_already_expired))
            }

            if (cvv.length != 3) {
                etCvv.showError(getString(R.string.please_enter_proper_cvv))
            }

            if (formIsValid) {
                viewModel.createCard(
                    name = name,
                    number = number.toLong(),
                    date = expire,
                    cvv = cvv.toInt(),
                    cardType = checkCardType()
                )
                findNavController().popBackStack()
            }
        }
    }

    private fun handleInputs() {
        binding.apply {
            handleCardType()

            etName.doOnTextChanged { text, _, _, _ ->
                itemCard.textCardName.text = text
            }

            etNumber.doOnTextChanged { text, _, _, _ ->
                itemCard.textCardNumber.text = updateCardNumber(text.toString())
            }

            handleExpirationDate()
        }
    }

    private fun updateCardNumber(input: String): String {
        val cardTemplate = "****************"

        val updatedCard = cardTemplate.mapIndexed { index, char ->
            if (index < input.length) input[index] else char
        }.joinToString("")

        return updatedCard.chunked(4).joinToString(" ")
    }

    private fun updateExpirationDate(date: String): String {
        val formattedDate: String = date.replace(Regex("[^\\d]"), "")

        if (formattedDate.length == 1 && formattedDate.toInt() > 1) {
            return ""
        }

        if (formattedDate.length == 2 && formattedDate.getLastAsInt() > 2 && formattedDate.toInt() > 9) {
            return formattedDate.first().toString()
        }

        return if (formattedDate.length > 2) {
            "${formattedDate.take(2)}/${formattedDate.drop(2)}"
        } else {
            formattedDate
        }
    }

    private fun checkCardType(): CardType {
        return if (binding.rbMastercard.isChecked) CardType.MASTERCARD else CardType.VISA
    }

    private fun handleCardType() {
        binding.rgCardType.setOnCheckedChangeListener { _, _ ->
            val cardType: CardType = checkCardType()

            with(binding.itemCard) {
                ivCard.setBackgroundResource(cardType.backGround)
                ivCard.setImageResource(cardType.image)
                ivCardType.setImageResource(cardType.logo)
            }
        }
    }

    private fun handleExpirationDate() {
        with(binding) {
            etExpire.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    val formattedDate: String = updateExpirationDate(s.toString())
                    etExpire.removeTextChangedListener(this)
                    itemCard.textExpire.text = formattedDate
                    etExpire.setText(formattedDate)
                    etExpire.setSelection(formattedDate.length)
                    etExpire.addTextChangedListener(this)
                }
            })
        }
    }

    private fun isDateExpired(date: String): Boolean {
        val parts = date.split("/")

        val month = parts[0].toInt()
        val year = parts[1].toInt()

        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH + 1)
        val currentYear = calendar.get(Calendar.YEAR) % 100

        return year < currentYear || (year == currentYear && month < currentMonth)
    }

    private fun EditText.showError(error: String) {
        this.error = error
        formIsValid = false
    }

    private fun EditText.getString(): String {
        return this.text.toString().trim()
    }


}