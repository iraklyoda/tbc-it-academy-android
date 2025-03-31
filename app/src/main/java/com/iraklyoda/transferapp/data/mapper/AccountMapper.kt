package com.iraklyoda.transferapp.data.mapper

import com.iraklyoda.transferapp.data.remote.dto.AccountDto
import com.iraklyoda.transferapp.domain.model.GetDebitCard

fun AccountDto.toDomain(): GetDebitCard {
    return GetDebitCard(
        id, accountName, accountNumber, valueType, cardType, balance, cardLogo
    )
}