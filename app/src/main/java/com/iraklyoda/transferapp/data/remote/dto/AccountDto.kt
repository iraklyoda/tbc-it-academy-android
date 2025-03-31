package com.iraklyoda.transferapp.data.remote.dto

import com.iraklyoda.transferapp.domain.common.enum.Currency
import com.iraklyoda.transferapp.domain.common.enum.DebitCardType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountDto(
    @SerialName("id") val id: Int,
    @SerialName("account_name") val accountName: String,
    @SerialName("account_number") val accountNumber: String,

    // Typo Mistake in Backend where sometimes value type comes as "valute_type"
    @SerialName("valute_type") val typoValueType: Currency? = null,
    @SerialName("value_type") val correctValueType: Currency? = null,

    @SerialName("card_type") val cardType: DebitCardType,
    @SerialName("balance") val balance: Int,
    @SerialName("card_logo") val cardLogo: String?
) {
    val valueType: Currency
        get() = correctValueType ?: typoValueType ?: Currency.UNKNOWN
}