package com.example.baseproject.card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CardViewModel : ViewModel() {
    private val _cards: MutableLiveData<MutableList<Card>> = MutableLiveData(
        mutableListOf(
            Card(
                name = "Paul Sheldon",
                number = 4315123412341234,
                expireMonth = 1,
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

    val cards: LiveData<MutableList<Card>> get() = _cards

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

        val updatedList = _cards.value?.toMutableList() ?: mutableListOf()
        updatedList.add(0, card)
        _cards.value = updatedList
    }

    fun deleteCard(position: Int) {
        val updatedList = _cards.value?.toMutableList() ?: mutableListOf()
        updatedList.removeAt(position)
        _cards.value = updatedList
    }
}