package com.example.baseproject.card

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CardViewModel : ViewModel() {
    private val _cards = MutableStateFlow<MutableList<Card>>(
        mutableListOf(
            Card(
                name = "Paul Sheldon",
                number = 4315123412341234,
                expireMonth = 9,
                expireYear = 28,
                cvv = 123,
                cardType = CardType.VISA
            ),
            Card(
                name = "Annie Winkles",
                number = 4315123412341234,
                expireMonth = 1,
                expireYear = 26,
                cvv = 123,
                cardType = CardType.VISA
            ),
            Card(
                name = "Toshiro Mifune",
                number = 4315123412341234,
                expireMonth = 1,
                expireYear = 26,
                cvv = 123,
                cardType = CardType.MASTERCARD
            )
        )
    )

    val cards: StateFlow<List<Card>> get() = _cards

    fun createCard(
        name: String,
        number: Long,
        date: String,
        cvv: Int,
        cardType: CardType
    ) {
        val parts = date.split("/")
        val month = parts[0].toInt()
        val year = parts[1].toInt()

        val card = Card(
            name = name,
            number = number,
            expireMonth = month,
            expireYear = year,
            cvv = cvv,
            cardType = cardType
        )
        val updatedList = _cards.value.toMutableList()
        updatedList.add(0, card)
        _cards.value = updatedList.toMutableList()
    }

    fun deleteCard(position: Int) {
        val updatedList = _cards.value.toMutableList()
        updatedList.removeAt(position)
        _cards.value = updatedList.toMutableList()
    }
}