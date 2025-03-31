package com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.mapper

import com.iraklyoda.transferapp.R
import com.iraklyoda.transferapp.domain.common.enum.DebitCardType
import com.iraklyoda.transferapp.domain.model.GetDebitCard
import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.model.AccountUi

fun GetDebitCard.toUi(): AccountUi {
    val cardLogo = getCardLogoResId(cardType = cardType)

    return AccountUi(
        id = id,
        accountName = "@$accountName",
        accountNumber = accountNumber,
        valueType = valueType,
        cardType = cardType,
        balance = balance,
        cardLogo = cardLogo
    )
}

private fun getCardLogoResId(cardType: DebitCardType): Int {
    return when (cardType) {
        DebitCardType.VISA -> R.drawable.visa
        DebitCardType.MASTER_CARD -> R.drawable.master_card
    }
}