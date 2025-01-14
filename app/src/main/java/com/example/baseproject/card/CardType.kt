package com.example.baseproject.card

import com.example.baseproject.R

enum class CardType(val image: Int, val backGround: Int, val logo: Int) {
    VISA(image = R.drawable.visa,backGround = R.drawable.item_card_visa_bg, logo = R.drawable.ic_visa),
    MASTERCARD(image = R.drawable.mastercard, backGround = R.drawable.item_card_mastercard_bg, logo = R.drawable.ic_mastercard)
}