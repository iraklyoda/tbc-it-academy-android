package com.iraklyoda.transferapp.domain.model

import com.iraklyoda.transferapp.domain.common.enum.Currency
import com.iraklyoda.transferapp.domain.common.enum.DebitCardType

data class GetDebitCard(
    val id: Int,
    val accountName: String,
    val accountNumber: String,
    val valueType: Currency,
    val cardType: DebitCardType,
    val balance: Int,
    val cardLogo: String?
)